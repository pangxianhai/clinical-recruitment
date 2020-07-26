package main

import (
	"flag"
	"os"
	"os/signal"
	"recruitment-user/main/appRpc"
	"recruitment-user/main/biz/common"
	"recruitment-user/main/biz/service"
	"recruitment-user/main/config"
	"recruitment-user/main/logger"
	"syscall"
)

func main() {
	//1.加载配置
	loadConfig()
	//2.启动 Rpc 服务
	go startRpcServer()
	//3.注册 Rpc 服务
	go registerRpcServer()
	//4.加载国际化信息
	loadI18n()
	//5.监听系统退出信号
	shutdownHook()
}

func loadConfig() {
	var profiles string
	flag.StringVar(&profiles, "profiles", "dev", "默认环境为dev")
	flag.Parse()
	configErr := config.LoadConfig(profiles)
	if configErr != nil {
		logger.Info("加载配置错误")
	}
}

func startRpcServer() {
	rpcErr := appRpc.StartRpcServer()
	if rpcErr != nil {
		logger.Fatal("启动 Rpc 服务失败，程序退出", rpcErr)
	} else {
		logger.Info("启动 Rpc 服务完成")
	}
}

func registerRpcServer() {
	regErr := appRpc.RegisterRpcServer()
	if regErr != nil {
		logger.Fatal("注册 Rpc 服务失败，程序退出 ", regErr)
	} else {
		logger.Info("注册 Rpc 服务完成")
	}
	//注册用户服务
	service.RegisterUserService()
}

func loadI18n() {
	common.LoadErrorCode()
	common.LoadUserCode()
}

func shutdownHook() {
	osChan := make(chan os.Signal)
	signal.Notify(osChan, syscall.SIGHUP, syscall.SIGINT, syscall.SIGTERM, syscall.SIGQUIT, syscall.SIGUSR1, syscall.SIGUSR2)
	_ = <-osChan
	logger.Info("系统开始退出")
	appRpc.DeregisterRpcServer()
	logger.Info("结束退出")
	os.Exit(0)
}
