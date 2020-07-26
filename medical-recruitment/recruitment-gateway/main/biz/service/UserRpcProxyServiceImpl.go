package service

import (
	"errors"
	"recruitment-gateway/main/appRpc"
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
	userService = new(UserRpcProxyServiceImpl)
	return userService
}

type UserRpcProxyServiceImpl struct {
}

func (userService *UserRpcProxyServiceImpl) GetUserInfoByPhone(phone string, userInfoRes *api.UserInfoRes) error {
	client := appRpc.GetRpcClient("recruitment-user-server")
	if client == nil {
		return errors.New("recruitment-user-server 服务不可用")
	}
	return client.Call("UserServiceImpl.GetUserInfoByPhone", phone, userInfoRes)
}
