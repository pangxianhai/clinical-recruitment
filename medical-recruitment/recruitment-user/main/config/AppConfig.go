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

type JdbcConf struct {
	UserName string `json:"userName"`
	Password string `json:"password"`
	Url      string `json:"url"`
}

var baseConf BaseConfig

type BaseConfig struct {
	SerConf    ServiceConf `json:"service"`
	ConsulConf ConsulConf  `json:"consul"`
	JdbcConf   JdbcConf    `json:"jdbcConf"`
}

func LoadConfig(profiles string) (err error) {
	var (
		content []byte
	)
	if content, err = ioutil.ReadFile("./conf/application.json"); err != nil {
		logger.Error("加载配置错误", err)
		return
	}
	if err = json.Unmarshal(content, &baseConf); err != nil {
		logger.Error(err)
		return
	}

	if content, err = ioutil.ReadFile("./conf/application-" + profiles + ".json"); err != nil {
		logger.Error("加载配置"+profiles+"错误", err)
		return
	}
	if err = json.Unmarshal(content, &baseConf); err != nil {
		logger.Error(err)
		return
	}
	logger.Info("配置加载结束。")
	return nil

}

func GetServiceConf() ServiceConf {
	return baseConf.SerConf
}

func GetConsulConf() ConsulConf {
	return baseConf.ConsulConf
}
func GetJdbcConf() JdbcConf {
	return baseConf.JdbcConf
}
