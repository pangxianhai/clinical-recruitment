package config

type ServiceConfig struct {
	Name string
	Port string
}

type ConsulConfig struct {
	Address string
}

var serConfig ServiceConfig
var consulConfig ConsulConfig

func LoadConfig(profiles string) {
	if profiles == "prod" {
		serConfig = ServiceConfig{
			Name: "recruitment-user-server",
			Port: "2000",
		}
		consulConfig = ConsulConfig{
			Address: "127.0.0.1:8500",
		}
	} else {
		serConfig = ServiceConfig{
			Name: "recruitment-user-server",
			Port: "2000",
		}
		consulConfig = ConsulConfig{
			Address: "127.0.0.1:8500",
		}
	}

}

func GetServiceConfig() ServiceConfig {
	return serConfig
}

func GetConsulConfig() ConsulConfig {
	return consulConfig
}
