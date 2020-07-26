package config

type ConsulConfig struct {
	Address string
}

var consulConfig ConsulConfig

func LoadConfig(profiles string) {
	if profiles == "prod" {
		consulConfig = ConsulConfig{
			Address: "127.0.0.1:8500",
		}

	} else {
		consulConfig = ConsulConfig{
			Address: "127.0.0.1:8500",
		}
	}
}

func GetConsulConfig() ConsulConfig {
	return consulConfig
}
