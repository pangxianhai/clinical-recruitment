package constant

const (
	USER_MALE    byte = 1
	USER_FEMALE  byte = 2
	USER_UNKNOWN byte = 3

	ROLE_MANAGER  byte = 1
	ROLE_CUSTOMER byte = 2

	STATUS_NORMAL byte = 1
	STATUS_FREEZE byte = 2
)

var GenderDict = map[byte]string{
	USER_MALE:    "男",
	USER_FEMALE:  "女",
	USER_UNKNOWN: "未知",
}

var StatusDict = map[byte]string{
	STATUS_NORMAL: "正常",
	STATUS_FREEZE: "冻结",
}
