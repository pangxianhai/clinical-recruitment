package appRpc

import (
	consulApi "github.com/hashicorp/consul/api"
	"net"
	"net/rpc"
	"recruitment-user/main/biz/util"
	"recruitment-user/main/config"
	"recruitment-user/main/logger"
	"strconv"
)

var client *consulApi.Client

//开启 RPC 服务
func StartRpcServer() error {
	serviceConf := config.GetServiceConf()

	addy, err := net.ResolveTCPAddr("tcp", "0.0.0.0:"+serviceConf.Port)
	if err != nil {
		return err
	}

	inbound, err := net.ListenTCP("tcp", addy)
	if err != nil {
		return err
	}
	logger.Info("启动 Rpc 服务完成,监听地址: " + serviceConf.Port)
	rpc.Accept(inbound)
	return nil
}

//注册 RPC 服务到注册中心
func RegisterRpcServer() error {
	serviceConf := config.GetServiceConf()
	myConsul := config.GetConsulConf()

	consulConf := consulApi.DefaultConfig()
	consulConf.Address = myConsul.Address
	logger.Info("注册服务，服务中心地址:" + consulConf.Address)
	var err error
	client, err = consulApi.NewClient(consulConf)
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
	registration.ID = ip + ":" + serviceConf.Port
	registration.Name = serviceConf.Name
	port, err := strconv.Atoi(serviceConf.Port)
	registration.Port = port
	registration.Tags = []string{serviceConf.Name}
	registration.Meta = meta
	registration.Address = ip
	logger.Info("注册服务，服务地址: ", registration.Address, "端口号: ", registration.Port)

	// 增加consul健康检查回调函数
	check := new(consulApi.AgentServiceCheck)
	check.TCP = ip + ":" + serviceConf.Port
	check.Timeout = "5s"
	check.Interval = "5s"
	check.DeregisterCriticalServiceAfter = "20s"
	registration.Check = check

	// 注册服务到consul
	err = client.Agent().ServiceRegister(registration)
	return err
}

// 取消consul注册的服务
func DeregisterRpcServer() {
	serviceConf := config.GetServiceConf()
	ip, ipErr := util.GetLocalIp()
	if ipErr != nil {
		logger.Error("获取 IP 失败")
	}
	serverId := ip + ":" + serviceConf.Port
	err := client.Agent().ServiceDeregister(serverId)
	if err != nil {
		logger.Error("取消 RPC 服务注册失败", err)
	}
}
