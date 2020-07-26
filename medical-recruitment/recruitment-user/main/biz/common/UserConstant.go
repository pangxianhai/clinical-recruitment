package common

import (
	"encoding/json"
	"io/ioutil"
	"recruitment-user/main/logger"
)

type userConstant struct {
	UserMale     byte
	UserFemale   byte
	UserUnknown  byte
	StatusNormal byte
	StatusFreeze byte
	RoleManager  byte
	RoleCustomer byte
}

var UserConstant = userConstant{
	UserMale:    1,
	UserFemale:  2,
	UserUnknown: 3,

	StatusNormal: 1,
	StatusFreeze: 2,

	RoleManager:  1,
	RoleCustomer: 2,
}

type UserCode struct {
	Gender map[uint]string `json:"gender"`
	Status map[uint]string `json:"status"`
	Role   map[uint]string `json:"role"`
}

var userCnCode UserCode

func LoadUserCode() {
	var (
		content []byte
		err     error
	)
	if content, err = ioutil.ReadFile("./conf/i18n/user_code_cn.json"); err != nil {
		logger.Error("用户状态码加载失败", err)
		return
	}
	if err = json.Unmarshal(content, &userCnCode); err != nil {
		logger.Error("用户状态码绑定失败", err)
		return
	}
}

func GetUserCode() UserCode {
	return userCnCode
}
