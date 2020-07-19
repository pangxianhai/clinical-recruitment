package main

import (
	"flag"
	"log"
	"recruitment-user/main/appRpc"
	"recruitment-user/main/biz/service"
	"recruitment-user/main/config"
	"sync"
)

func main() {
	var profiles string
	flag.StringVar(&profiles, "profiles", "dev", "默认环境为dev")
	flag.Parse()
	config.LoadConfig(profiles)

	var wg sync.WaitGroup
	wg.Add(2)
	go func() {
		rpcErr := appRpc.StartRpcServer()
		if rpcErr != nil {
			log.Fatal("启动 Rpc 服务失败，程序退出", rpcErr)
		} else {
			log.Println("启动 Rpc 服务完成")
		}
		wg.Done()
	}()

	go func() {
		regErr := appRpc.RegisterRpcServer()
		if regErr != nil {
			log.Fatal("注册 Rpc 服务失败，程序退出 ", regErr)
		} else {
			log.Println("注册 Rpc 服务完成")
		}
		wg.Done()
	}()

	service.RegisterUserService()

	wg.Wait()
}
