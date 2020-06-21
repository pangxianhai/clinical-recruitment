package dao

import (
	"errors"
	"github.com/jinzhu/gorm"
	"recruitment/common"
	"recruitment/util"
	"time"
)

type AdminInfoDO struct {
	UserInfoDO
	UserId    uint
	AdminType byte `gorm:"column:type"`
}

func (AdminInfoDO) TableName() string {
	return "administrator_info"
}

type AdminInfoQuery struct {
	common.BaseQuery
	AdminType    byte   `form:"type" `
	PhoneLike    string `form:"phoneLike" `
	RealNameLike string `form:"realNameLike" `
	Status       byte   `form:"status" `
}

type AdminDAO struct {
	conn util.DbConnection
}

func (this *AdminDAO) InsertAdmin(do *AdminInfoDO, operator string) (uint, error) {
	do.CreatedTime = time.Now()
	do.CreatedBy = operator
	db := this.conn.GetDb()
	db = db.Create(do)
	if db.Error != nil {
		return common.ADMIN_ADD_FAILED, db.Error
	}
	return 0, nil
}

func (this *AdminDAO) UpdateAdmin(do *AdminInfoDO, operator string) (uint, error) {
	if do.ID == 0 {
		return common.ADMIN_ID_EMPTY, errors.New("admin id empty")
	}
	do.UpdatedBy = operator
	do.UpdatedTime = time.Now()
	db := this.conn.GetDb()
	db = db.Model(do).Omit("created_by", "created_time").Updates(do)
	if db.Error != nil {
		return common.ADMIN_UPDATE_FAILED, db.Error
	}
	return 0, nil
}

func (this *AdminDAO) ListAdminInfoPage(query *AdminInfoQuery) ([]AdminInfoDO, common.Paginator) {
	db := this.conn.GetDb()
	var adminInfos = make([]AdminInfoDO, query.PageSize)
	var totalRecord uint
	db = db.Table("administrator_info a").Select("a.id,a.user_id,a.type,a.status," +
		"a.created_by,a.created_time,a.updated_by,a.updated_time," +
		"u.open_id,u.union_id,u.nickname,u.real_name,u.gender,u.phone,u.password ").
		Joins("left join user_info u on a.user_id = u.id")

	db = this.buildQuery(db, query)
	db = db.Offset(query.GetFrom()).Limit(query.GetLimit())
	db.Scan(&adminInfos).Count(&totalRecord)
	paginator := common.NewPaginator(query.Page, query.PageSize, totalRecord)
	return adminInfos, *paginator
}

func (this *AdminDAO) buildQuery(db *gorm.DB, query *AdminInfoQuery) *gorm.DB {
	if len(query.PhoneLike) > 0 {
		db = db.Where("u.phone LIKE ?", "%"+query.PhoneLike+"%")
	}
	if len(query.RealNameLike) > 0 {
		db = db.Where("u.real_name LIKE ?", "%"+query.RealNameLike+"%")
	}
	if query.Status > 0 {
		db = db.Where("a.status = ?", query.Status)
	}
	if query.AdminType > 0 {
		db = db.Where("a.type = ?", query.AdminType)
	}
	return db
}
