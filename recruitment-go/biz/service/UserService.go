package service

import (
	"encoding/json"
	"errors"
	"recruitment/biz/dao"
	"recruitment/common"
	"recruitment/constant"
	"recruitment/util"
)

type UserService struct {
	userDAO dao.UserDAO
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
}

type UserInfoRes struct {
	UserId   uint            `json:"userId"`
	Nickname string          `json:"nickname"`
	Phone    string          `json:"phone"`
	RealName string          `json:"realName"`
	Gender   common.BaseType `json:"gender"`
	Status   common.BaseType `json:"status"`
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
