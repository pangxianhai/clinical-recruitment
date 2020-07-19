package api

import (
	"recruitment-user/main/biz/common"
)

type UserInfoRes struct {
	UserId   uint            `json:"userId"`
	Nickname string          `json:"nickname"`
	Phone    string          `json:"phone"`
	RealName string          `json:"realName"`
	Gender   common.BaseType `json:"gender"`
	Status   common.BaseType `json:"status"`
}

type UserService interface {
	GetUserInfoByPhone(phone string, userInfoRes *UserInfoRes)
}
