package dao

import (
	"errors"
	"recruitment/common"
	"recruitment/constant"
	"recruitment/util"
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
	conn util.DbConnection
}

const (
	userColumns = "id,open_id,union_id,nickname,real_name,gender,phone,password,status," +
		"created_by,created_time,updated_by,updated_time"
)

func (this *UserDAO) GetUserInfoByPhone(phone string) *UserInfoDO {
	var userInfoDO UserInfoDO
	this.conn.GetDb().Select(userColumns).Where("phone = ?", phone).First(&userInfoDO)
	return &userInfoDO
}

func (this *UserDAO) InsertUser(do *UserInfoDO, operator string) (uint, error) {
	do.CreatedTime = time.Now()
	do.CreatedBy = operator
	do.Status = constant.STATUS_NORMAL
	//由于 string 不能赋值为nil 但 open_id 和 union_id 不能重复
	//所以 在 open_id 和 union_id 为空的情况用手机号替代
	if do.OpenId == "" {
		do.OpenId = do.Phone
	}
	if do.UnionId == "" {
		do.UnionId = do.Phone
	}
	db := this.conn.GetDb()
	db = db.Create(do)
	if db.Error != nil {
		return common.USER_ADD_FAILED, db.Error
	}
	return common.SUCCESS, nil
}

func (this *UserDAO) UpdateUser(do *UserInfoDO, operator string) (uint, error) {
	if do.ID == 0 {
		return common.USER_ID_EMPTY, errors.New("user id empty")
	}
	do.UpdatedBy = operator
	do.UpdatedTime = time.Now()
	db := this.conn.GetDb()
	db = db.Model(&do).Omit("id", "created_by", "created_time").Updates(do)
	if db.Error != nil {
		return common.USER_UPDATE_FAILED, db.Error
	}
	return common.SUCCESS, nil
}
