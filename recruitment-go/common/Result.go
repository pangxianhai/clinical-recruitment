package common

type result struct {
	Code    uint        `json:"code"`
	Message string      `json:"message"`
	Data    interface{} `json:"data"`
}

const (
	SUCCESS      uint = 200
	UNAUTHORIZED uint = 401
	FORBIDDEN    uint = 403
	SYS_ERROR    uint = 500
	PARAM_ERROR  uint = 600
	NOT_LOGIN    uint = 601

	//管理员添加失败
	ADMIN_ADD_FAILED uint = 101000
	//管理员 ID 为空
	ADMIN_ID_EMPTY uint = 101001
	//管理员更新失败
	ADMIN_UPDATE_FAILED uint = 101002
	//管理员被冻结
	ADMIN_FREEZE uint = 101002

	// 添加用户失败
	USER_ADD_FAILED uint = 101300
	//用户ID为空
	USER_ID_EMPTY uint = 101301
	// 更新用户失败
	USER_UPDATE_FAILED uint = 101302
	//未登录
	USER_NOT_LOGIN uint = 101303
	//用户注册失败
	USER_REGISTER_FAILED uint = 101304
	//用户不存在
	USER_NOT_EXIST uint = 101305
	//密码错误
	USER_PASSWORD_ERROR uint = 101306
)

func Result(data interface{}) *result {
	r := result{Code: SUCCESS, Message: "success", Data: data}
	return &r
}

func ResultError(code uint) *result {
	message := GetMessage(code)
	r := result{Code: code, Message: message}
	return &r
}
