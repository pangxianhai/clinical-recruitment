package appRpc

import (
	consulApi "github.com/hashicorp/consul/api"
	"log"
	"math/rand"
	"net/rpc"
	"recruitment-gateway/main/config"
	"strconv"
	"time"
)

type ServiceInfo struct {
	Host     string
	Port     int
	ServerID string
	Client   *rpc.Client
}

var appRpcServiceMap = make(map[string][]ServiceInfo, 1)

var consulClient *consulApi.Client

func InitRpcService() {
	FindRpcServer()
	go func() {
		for {
			// 10 秒发现一次 是否有新服务
			time.Sleep(10 * time.Second)
			FindRpcServer()
		}
	}()
	CheckRpcServer()
}

func FindRpcServer() {
	appConsulConfig := config.GetConsulConfig()
	consulConfig := consulApi.DefaultConfig()
	consulConfig.Address = appConsulConfig.Address
	var err error
	consulClient, err = consulApi.NewClient(consulConfig)
	if err != nil {
		log.Println("Rpc 服务启动 连接注册中心失败 : ", err)
		return
	}
	serviceList, err := consulClient.Agent().Services()
	for _, value := range serviceList {
		serviceName := value.Service
		serviceID := value.ID
		if IsHasServer(appRpcServiceMap[serviceName], serviceID) {
			continue
		}
		address := value.Address + ":" + strconv.Itoa(value.Port)
		client, err := rpc.Dial("tcp", address)
		if err != nil {
			log.Println("Error 服务提供者 连接失败:"+address, err)
		} else {
			appRpcServiceMap[serviceName] = append(appRpcServiceMap[serviceName], ServiceInfo{
				Host:     value.Address,
				Port:     value.Port,
				ServerID: value.ID,
				Client:   client,
			})
		}
	}
}

func IsHasServer(serverList []ServiceInfo, serverID string) bool {
	if len(serverList) <= 0 {
		return false
	}
	for _, serverInfo := range serverList {
		if serverID == serverInfo.ServerID {
			return true
		}
	}
	return false
}

func CheckRpcServer() {
	go func() {
		for {
			deleteIdMap := make(map[string]bool, 0)
			for _, serverList := range appRpcServiceMap {
				for i := 0; i < len(serverList); i++ {
					serverInfo := serverList[i]
					health, _, _ := consulClient.Agent().AgentHealthServiceByID(serverInfo.ServerID)
					if health != consulApi.HealthPassing {
						deleteIdMap[serverInfo.ServerID] = true
					}

				}
			}
			if len(deleteIdMap) > 0 {
				for serverName, serverList := range appRpcServiceMap {
					serverInfoList := make([]ServiceInfo, 0)
					for _, sInfo := range serverList {
						_, e := deleteIdMap[sInfo.ServerID]
						if !e {
							serverInfoList = append(serverInfoList, sInfo)
						}
					}
					appRpcServiceMap[serverName] = serverInfoList
				}

			}
			//一秒检查一次
			time.Sleep(1 * time.Second)
		}
	}()
}

func GetRpcClient(serverName string) *rpc.Client {
	clientList := appRpcServiceMap[serverName]
	N := len(clientList)
	if N == 0 {
		log.Println(serverName + ", 没有服务提供者")
		return nil
	} else if N == 1 {
		return clientList[0].Client
	} else {
		i := rand.Intn(N)
		return clientList[i].Client
	}
}
