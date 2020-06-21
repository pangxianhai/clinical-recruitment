package dao

import (
	"errors"
	"recruitment/common"
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
	columns = "id,open_id,union_id,nickname,real_name,gender,phone,password,status," +
		"created_by,created_time,updated_by,updated_time"
)

func (this *UserDAO) GetUserInfoByPhone(phone string) *UserInfoDO {
	var userInfoDO UserInfoDO
	this.conn.GetDb().Select(columns).Where("phone = ?", phone).First(&userInfoDO)
	return &userInfoDO
}

func (this *UserDAO) InsertUser(do *UserInfoDO, operator string) (uint, error) {
	do.CreatedTime = time.Now()
	do.CreatedBy = operator
	db := this.conn.GetDb()
	db = db.Create(do)
	if db.Error != nil {
		return common.USER_ADD_FAILED, db.Error
	}
	return 0, nil
}

func (this *UserDAO) UpdateUser(do *UserInfoDO, operator string) (uint, error) {
	if do.ID == 0 {
		return common.USER_ID_EMPTY, errors.New("user id empty")
	}
	do.UpdatedBy = operator
	do.UpdatedTime = time.Now()
	db := this.conn.GetDb()
	db = db.Model(do).Omit("created_by", "created_time").Updates(do)
	if db.Error != nil {
		return common.USER_UPDATE_FAILED, db.Error
	}
	return 0, nil
}
