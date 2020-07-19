package service

import (
	"log"
	"net/rpc"
	"recruitment-user/main/biz/api"
	"sync"
)

var userService api.UserService
var userServiceLock sync.Mutex

func GetUserService() api.UserService {
	if userService != nil {
		return userService
	}
	userServiceLock.Lock()
	defer userServiceLock.Unlock()
	if userService != nil {
		return userService
	}
	userService = &UserServiceImpl{}
	return userService
}

func RegisterUserService() {
	userService := GetUserService()
	err := rpc.Register(userService)
	if err != nil {
		log.Fatal("UserService register failed", err)
	}
}

type UserServiceImpl struct {
}

func (userService *UserServiceImpl) GetUserInfoByPhone(phone string, userInfoRes *api.UserInfoRes) {
	userInfoRes = &api.UserInfoRes{
		Phone: phone,
	}
}
