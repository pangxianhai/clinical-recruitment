package service

import (
	"encoding/json"
	"errors"
	"fmt"
	"recruitment/biz/dao"
	"recruitment/common"
	"recruitment/constant"
	"recruitment/util"
	"sync"
)

type UserService struct {
	userDAO *dao.UserDAO
}

var userService *UserService
var userServiceLock sync.Mutex

func GetUserService() *UserService {
	if userService != nil {
		return userService
	}
	userServiceLock.Lock()
	defer userServiceLock.Unlock()
	if userService != nil {
		return userService
	}
	userService = &UserService{}
	userService.userDAO = dao.GetUserDAO()
	return userService
}

/*
登录信息 LoginRes
*/
type LoginInfo struct {
	UserId   uint   `json:"userId"`
	UserName string `json:"userName"`
	RealName string `json:"realName"`
	Phone    string `json:"phone"`
	//角色类型： 管理员 or 用户
	RoleType byte `json:"roleType"`
	//附加信息
	Attach string `json:"attach"`
	Status byte   `json:"status"`
}

type UserInfoRes struct {
	UserId   uint            `json:"userId"`
	Nickname string          `json:"nickname"`
	Phone    string          `json:"phone"`
	RealName string          `json:"realName"`
	Gender   common.BaseType `json:"gender"`
	Status   common.BaseType `json:"status"`
}

type UpdatePasswordReq struct {
	Phone          string `json:"phone"`
	SourcePassword string `json:"password" binding:"required"`
	NewPassword    string `json:"newPassword" binding:"required"`
}

func (this *UserService) GetCurrentUser(token string, userName string) (*LoginInfo, error) {
	decodeToken, err := util.Rc4DecodeByte(token, userName)
	if err != nil {
		return nil, err
	}
	var logInfo LoginInfo
	err = json.Unmarshal(decodeToken, &logInfo)
	if err != nil {
		return nil, err
	}
	if logInfo.UserName != userName {
		return nil, errors.New("unauthorized")
	}
	return &logInfo, nil
}

func (this *UserService) GetUserInfoByPhone(phone string) *UserInfoRes {
	userInfoDO := this.userDAO.GetUserInfoByPhone(phone)

	userInfoRes := UserInfoRes{
		UserId:   userInfoDO.ID,
		Nickname: userInfoDO.Nickname,
		Phone:    userInfoDO.Phone,
		RealName: userInfoDO.RealName,
		Gender:   common.BaseType{Code: userInfoDO.Gender, Desc: constant.GenderDict[userInfoDO.Gender]},
		Status:   common.BaseType{Code: userInfoDO.Status, Desc: constant.StatusDict[userInfoDO.Gender]},
	}

	return &userInfoRes
}

func (this *UserService) UpdateUserPassword(req *UpdatePasswordReq, operator string) (uint, error) {
	userInfoDO := this.userDAO.GetUserInfoByPhone(req.Phone)
	if userInfoDO.ID == 0 {
		return common.USER_NOT_EXIST, nil
	}
	fmt.Println("req c参数", req)
	fmt.Println("userDO ", userInfoDO)
	encodePass := util.HmacSha256Hash(req.SourcePassword, req.Phone)
	if encodePass != userInfoDO.Password {
		return common.USER_SOURCE_PASSWORD_ERROR, nil
	}
	encodeNewPass := util.HmacSha256Hash(req.NewPassword, req.Phone)
	updateDO := dao.UserInfoDO{
		BaseDO: common.BaseDO{
			ID: userInfoDO.ID,
		},
		Password: encodeNewPass,
	}
	return this.userDAO.UpdateUser(&updateDO, operator)
}
