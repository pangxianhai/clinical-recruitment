package controller

import (
	"recruitment/biz/service"
	"recruitment/common"
)

type UserController struct {
	userService service.UserService
}

type LoginRes struct {
	UserName string `json:"userName"`
	Token    string `json:"token"`
}

type UserInfoRes struct {
	UserId   uint            `json:"userId"`
	Nickname string          `json:"nickname"`
	Phone    string          `json:"phone"`
	RealName string          `json:"realName"`
	Gender   common.BaseType `json:"gender"`
	Status   common.BaseType `json:"status"`
}

