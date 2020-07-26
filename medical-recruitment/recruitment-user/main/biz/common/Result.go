package common

import (
	"encoding/json"
	"io/ioutil"
	"recruitment-user/main/logger"
)

type result struct {
	Code    uint        `json:"code"`
	Message string      `json:"message"`
	Data    interface{} `json:"data"`
}

type errorCode struct {
	Success  uint
	SysError uint

	AdminAddFailed    uint
	AdminIdEmpty      uint
	AdminUpdateFailed uint
	AdminFreeze       uint
	AdminNotExist     uint

	UserAddFailed           uint
	UserIdEmpty             uint
	UserUpdateFailed        uint
	UserRegisterFailed      uint
	UserNotExist            uint
	UserPasswordError       uint
	UserSourcePasswordError uint
}

var ErrorCode = errorCode{
	Success:  200, // 成功
	SysError: 500, // 系统错误

	AdminAddFailed:    101000, // 添加管理失败
	AdminIdEmpty:      101001, // 管理员 ID 为空
	AdminUpdateFailed: 101002, // 更新管理员信息失败
	AdminFreeze:       101003, // 管理员被冻结
	AdminNotExist:     101004, // 管理员不存在

	UserAddFailed:           101101, // 用户添加失败
	UserIdEmpty:             101102, // 用户 ID 为空
	UserUpdateFailed:        101103, // 更新用户信息失败
	UserRegisterFailed:      101104, // 用户注册失败
	UserNotExist:            101105, // 用户不存在
	UserPasswordError:       101106, // 用户密码错误
	UserSourcePasswordError: 101107, // 用户原密码错误
}

var errorCnCodeDesc map[uint]string

func LoadErrorCode() {
	var (
		content []byte
		err     error
	)
	if content, err = ioutil.ReadFile("./conf/i18n/error_code_cn.json"); err != nil {
		logger.Error("错误码加载失败", err)
		return
	}
	if err = json.Unmarshal(content, &errorCnCodeDesc); err != nil {
		logger.Error("错误码绑定", err)
		return
	}
}

func GetErrorDesc(code uint) string {
	desc, ok := errorCnCodeDesc[code]
	if ok {
		return desc
	} else {
		return "Unknown error"
	}
}

func NewResult(data interface{}) *result {
	r := result{Code: ErrorCode.Success, Message: errorCnCodeDesc[ErrorCode.Success], Data: data}
	return &r
}

func NewResultError(code BaseType) *result {
	r := result{Code: code.Code, Message: code.Desc, Data: nil}
	return &r
}

func ResultErrorMsg(code uint, message string) *result {
	r := result{Code: code, Message: message, Data: nil}
	return &r
}
