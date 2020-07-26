package config

import (
	"encoding/json"
	"io/ioutil"
	"recruitment-user/main/logger"
)

type ServiceConf struct {
	Name string `json:"name"`
	Port string `json:"port"`
}

type ConsulConf struct {
	Address string `json:"address"`
}

type LogConf struct {
}

var serConf ServiceConf
var consulConf ConsulConf

type BaseConfig struct {
	SerConf    ServiceConf `json:"service"`
	ConsulConf ConsulConf  `json:"consul"`
}

func LoadConfig(profiles string) (err error) {
	var (
		content []byte
		conf    BaseConfig
	)
	if content, err = ioutil.ReadFile("./conf/application.json"); err != nil {
		logger.Error("加载配置错误", err)
		return
	}
	if err = json.Unmarshal(content, &conf); err != nil {
		logger.Error(err)
		return
	}

	if content, err = ioutil.ReadFile("./conf/application-" + profiles + ".json"); err != nil {
		logger.Error("加载配置"+profiles+"错误", err)
		return
	}
	if err = json.Unmarshal(content, &conf); err != nil {
		logger.Error(err)
		return
	}
	serConf = conf.SerConf
	consulConf = conf.ConsulConf
	logger.Info("配置加载结束。")
	return nil

}

func GetServiceConf() ServiceConf {
	return serConf
}

func GetConsulConf() ConsulConf {
	return consulConf
}
