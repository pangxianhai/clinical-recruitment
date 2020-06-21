package constant

const (
	ADMIN_SYS      = 1
	ADMIN_BUSINESS = 2
)

var AdminTypeDict = map[byte]string{
	ADMIN_SYS:      "系统管理员",
	ADMIN_BUSINESS: "业务管理员",
}
