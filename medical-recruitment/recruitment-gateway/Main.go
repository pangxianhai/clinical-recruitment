package main

import (
	"bufio"
	"fmt"
	"os"
	"recruitment-gateway/main/appRpc"
	"recruitment-gateway/main/biz/service"
	"recruitment-user/main/biz/api"
)

//type Reply struct {
//	Data string
//}
//
//// 从consul中发现服务
//func ConsulFindServer() (host string, port int) {
//
//	// 创建连接consul服务配置
//	config := consulApi.DefaultConfig()
//	config.Address = "127.0.0.1:8500"
//	client, err := consulApi.NewClient(config)
//	if err != nil {
//		log.Fatal("consul client error : ", err)
//	}
//	a_servics, err := client.Agent().Services()
//	for _, value := range a_servics {
//		fmt.Println("服务器地址：", value.Address)
//		fmt.Println("服务器端口号：", value.Port)
//
//		fmt.Println("服务ID:", value.ID)
//		fmt.Println("服务:", value.Service)
//		fmt.Println("服务Meta:", value.Meta)
//
//		host = value.Address
//		log.Println(strconv.Itoa(value.Port))
//		port = value.Port
//	}
//
//	//
//	//if err == nil {
//	//	host = service.Address
//	//	fmt.Println(service.Port)
//	//	log.Println(strconv.Itoa(service.Port))
//	//	port = service.Port
//	//}
//	go func() {
//		a, b, _ := client.Agent().AgentHealthServiceByID("111")
//		fmt.Println("健康检查a", a)
//		fmt.Println("健康检查b", b)
//	}()
//	return
//}

//func main() {
//	host, port := ConsulFindServer()
//	address := host + ":" + strconv.Itoa(port)
//	log.Println("服务器："+address, port)
//	client, err := rpc.Dial("tcp", address)
//	if err != nil {
//		log.Fatal(err)
//	}
//	in := bufio.NewReader(os.Stdin)
//	for {
//		fmt.Println("开始调用")
//		line, b, err := in.ReadLine()
//		fmt.Println(line, b, err)
//		if err != nil {
//			log.Println(err)
//		}
//
//		var reply Reply
//		err = client.Call("Listener.GetLine", line, &reply)
//		if err != nil {
//			log.Println(err)
//		}
//		log.Printf("Reply: %v, Data: %v", reply, reply.Data)
//		fmt.Printf("Reply: %v, Data: %v", reply, reply.Data)
//	}
//}

func main() {
	appRpc.InitRpcService()
	userService := service.GetUserService()
	in := bufio.NewReader(os.Stdin)

	for {
		fmt.Println("开始调用")
		_, _, err := in.ReadLine()
		//fmt.Println(line, b, err)
		var userInfoRes = api.UserInfoRes{}
		err = userService.GetUserInfoByPhone("123899", &userInfoRes)
		if err != nil {
			fmt.Println("远程调用失败", err)
		} else {
			fmt.Println("用户信息:", userInfoRes)
		}
	}
}
