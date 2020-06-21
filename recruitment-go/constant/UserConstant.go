package constant

const (
	USER_MALE    = 1
	USER_FEMALE  = 2
	USER_UNKNOWN = 3

	STATUS_NORMAL = 1
	STATUS_FREEZE = 2
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
