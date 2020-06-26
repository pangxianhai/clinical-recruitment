package common

type result struct {
	Code    uint        `json:"code"`
	Message string      `json:"message"`
	Data    interface{} `json:"data"`
}

const (
	//成功
	SUCCESS uint = 200
	//无权限
	UNAUTHORIZED uint = 401
	//访问被禁止
	FORBIDDEN uint = 403
	//系统错误
	SYS_ERROR uint = 500
	//参数错误
	PARAM_ERROR uint = 600
	//未登录
	NOT_LOGIN uint = 601

	//管理员添加失败
	ADMIN_ADD_FAILED uint = 101000
	//管理员 ID 为空
	ADMIN_ID_EMPTY uint = 101001
	//管理员更新失败
	ADMIN_UPDATE_FAILED uint = 101002
	//管理员被冻结
	ADMIN_FREEZE uint = 101003
	//管理员不存在
	ADMIN_NOT_EXIST uint = 101004

	// 添加用户失败
	USER_ADD_FAILED uint = 101300
	//用户ID为空
	USER_ID_EMPTY uint = 101301
	// 更新用户失败
	USER_UPDATE_FAILED uint = 101302
	//用户注册失败
	USER_REGISTER_FAILED uint = 101304
	//用户不存在
	USER_NOT_EXIST uint = 101305
	//密码错误
	USER_PASSWORD_ERROR uint = 101306
	//原密码错误
	USER_SOURCE_PASSWORD_ERROR uint = 101307
)

func Result(data interface{}) *result {
	if data == nil {
		data = true
	}
	r := result{Code: SUCCESS, Message: "success", Data: data}
	return &r
}

func ResultError(code uint) *result {
	message := GetMessage(code)
	r := result{Code: code, Message: message, Data: false}
	return &r
}

func ResultErrorMsg(code uint, message string) *result {
	r := result{Code: code, Message: message, Data: false}
	return &r
}
