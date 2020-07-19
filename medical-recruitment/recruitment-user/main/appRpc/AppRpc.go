package appRpc

import (
	consulApi "github.com/hashicorp/consul/api"
	"log"
	"net"
	"net/rpc"
	"recruitment-user/main/biz/util"
	"recruitment-user/main/config"
	"strconv"
)

//开启 RPC 服务
func StartRpcServer() error {
	serviceConfig := config.GetServiceConfig()

	addy, err := net.ResolveTCPAddr("tcp", "0.0.0.0:"+serviceConfig.Port)
	if err != nil {
		return err
	}

	inbound, err := net.ListenTCP("tcp", addy)
	if err != nil {
		return err
	}
	log.Println("启动 Rpc 服务完成,监听地址: " + serviceConfig.Port)
	rpc.Accept(inbound)
	return nil
}

//注册 RPC 服务到注册中心
func RegisterRpcServer() error {
	serviceConfig := config.GetServiceConfig()
	myConsul := config.GetConsulConfig()

	consulConfig := consulApi.DefaultConfig()
	consulConfig.Address = myConsul.Address
	log.Println("注册服务，服务中心地址:" + consulConfig.Address)
	client, err := consulApi.NewClient(consulConfig)
	if err != nil {
		return err
	}

	ip, ipErr := util.GetLocalIp()
	if ipErr != nil {
		return ipErr
	}

	meta := map[string]string{"Listener": "GetLine"}

	// 创建注册到consul的服务到
	registration := new(consulApi.AgentServiceRegistration)
	registration.ID = ip + ":" + serviceConfig.Port
	registration.Name = serviceConfig.Name
	port, err := strconv.Atoi(serviceConfig.Port)
	registration.Port = port
	registration.Tags = []string{serviceConfig.Name}
	registration.Meta = meta
	registration.Address = ip
	log.Println("注册服务，服务地址: ", registration.Address, "端口号: ", registration.Port)

	// 增加consul健康检查回调函数
	check := new(consulApi.AgentServiceCheck)
	check.TCP = ip + ":" + serviceConfig.Port
	check.Timeout = "5s"
	check.Interval = "5s"
	check.DeregisterCriticalServiceAfter = "20s"
	registration.Check = check

	// 注册服务到consul
	err = client.Agent().ServiceRegister(registration)
	return err
}
