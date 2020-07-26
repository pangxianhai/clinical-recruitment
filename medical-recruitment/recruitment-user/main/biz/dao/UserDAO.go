package dao

import (
	"errors"
	"recruitment-user/main/biz/common"
	"sync"
	"time"
)

type UserInfoDO struct {
	common.BaseDO
	OpenId   string
	UnionId  string
	Nickname string
	Phone    string
	RealName string
	Gender   byte
	Password string
}

func (UserInfoDO) TableName() string {
	return "user_info"
}

type UserInfoQuery struct {
	common.BaseQuery
	ID           uint   `form:"userId" `
	OpenId       string `form:"openId" `
	UnionId      string `form:"unionId" `
	Phone        string `form:"phone" `
	PhoneLike    string `form:"phoneLike" `
	RealNameLike string `form:"realNameLike" `
	Gender       byte   `form:"gender" `
	Status       byte   `form:"status" `
}

type UserDAO struct {
}

var userDAO *UserDAO

var userDAOLock sync.Mutex

func GetUserDAO() *UserDAO {
	if userDAO != nil {
		return userDAO
	}
	userDAOLock.Lock()
	defer userDAOLock.Unlock()
	if userDAO != nil {
		return userDAO
	}
	userDAO = &UserDAO{}
	return userDAO
}

const (
	userColumns = "id,open_id,union_id,nickname,real_name,gender,phone,password,status," +
		"created_by,created_time,updated_by,updated_time"
)

func (userDAO *UserDAO) GetUserInfoByPhone(phone string) *UserInfoDO {
	var userInfoDO UserInfoDO
	GetDbConn().Select(userColumns).Where("phone = ?", phone).First(&userInfoDO)
	return &userInfoDO
}

func (userDAO *UserDAO) InsertUser(do *UserInfoDO, operator string) (uint, error) {
	do.CreatedTime = time.Now()
	do.CreatedBy = operator

	do.Status = common.UserConstant.UserMale
	do.Status = common.UserConstant.StatusNormal
	//由于 string 不能赋值为nil 但 open_id 和 union_id 不能重复
	//所以 在 open_id 和 union_id 为空的情况用手机号替代
	if do.OpenId == "" {
		do.OpenId = do.Phone
	}
	if do.UnionId == "" {
		do.UnionId = do.Phone
	}
	db := GetDbConn()
	db = db.Create(do)
	if db.Error != nil {
		return 1, db.Error
	}
	return common.ErrorCode.Success, nil
}

func (userDAO *UserDAO) UpdateUser(do *UserInfoDO, operator string) (uint, error) {
	if do.ID == 0 {
		return 2, errors.New("user id empty")
	}
	do.UpdatedBy = operator
	do.UpdatedTime = time.Now()
	db := GetDbConn()
	db = db.Model(&do).Omit("id", "created_by", "created_time").Updates(do)
	if db.Error != nil {
		return 3, db.Error
	}
	return common.ErrorCode.Success, nil
}

func (userDAO *UserDAO) GetUserInfoById(userId uint) *UserInfoDO {
	var userInfoDO UserInfoDO
	GetDbConn().Select(userColumns).Where("id = ?", userId).First(&userInfoDO)
	return &userInfoDO
}
