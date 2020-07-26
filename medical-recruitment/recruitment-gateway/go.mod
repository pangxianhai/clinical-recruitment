module recruitment-gateway

go 1.14

require (
	github.com/hashicorp/consul/api v1.5.0
	recruitment-user v0.0.0
)

replace (
	recruitment-user v0.0.0 => ../recruitment-user
)