package dao

import (
	"errors"
	"fmt"
	"recruitment/common"
	"recruitment/constant"
	"recruitment/util"
	"strings"
	"time"
)

type AdminInfoDO struct {
	common.BaseDO
	UserId    uint
	AdminType byte `gorm:"column:type"`
}

type AdminInfoBO struct {
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

const (
	adminColumns = "id,user_id,type,status," +
		"created_by,created_time,updated_by,updated_time"
)

func (this *AdminDAO) InsertAdmin(do *AdminInfoDO, operator string) (uint, error) {
	do.CreatedTime = time.Now()
	do.CreatedBy = operator
	do.Status = constant.STATUS_NORMAL
	db := this.conn.GetDb()
	db = db.Create(do)
	if db.Error != nil {
		return common.ADMIN_ADD_FAILED, db.Error
	}
	return common.SUCCESS, nil
}

func (this *AdminDAO) UpdateAdmin(do *AdminInfoDO, operator string) (uint, error) {
	if do.ID == 0 {
		return common.ADMIN_ID_EMPTY, errors.New("admin id empty")
	}
	do.UpdatedBy = operator
	do.UpdatedTime = time.Now()
	db := this.conn.GetDb()
	db = db.Model(&do).Omit("id", "created_by", "created_time").Updates(do)
	if db.Error != nil {
		return common.ADMIN_UPDATE_FAILED, db.Error
	}
	return common.SUCCESS, nil
}

func (this *AdminDAO) GetAdminByUserId(userId uint) *AdminInfoDO {
	var adminInfoDO AdminInfoDO
	this.conn.GetDb().Select(adminColumns).Where("user_id = ?", userId).First(&adminInfoDO)
	return &adminInfoDO

}

func (this *AdminDAO) ListAdminInfoPage(query *AdminInfoQuery) ([]AdminInfoBO, common.Paginator) {
	db := this.conn.GetDb()
	var adminInfos = make([]AdminInfoBO, query.PageSize)
	sql := "select a.id,a.user_id,a.type,a.status, a.created_by," +
		"a.created_time,a.updated_by,a.updated_time," +
		"u.open_id,u.union_id,u.nickname,u.real_name,u.gender,u.phone,u.password " +
		"from administrator_info a left join user_info u on a.user_id = u.id "

	whereCondition, args := this.buildWhere(query)
	if len(args) > 0 {
		sql += " where " + whereCondition
	}
	sql += " limit ?,? "
	args = append(args, query.GetFrom())
	args = append(args, query.GetLimit())
	db.Raw(sql, args...).Scan(&adminInfos)

	totalRecord := this.getTotalCount(query)
	paginator := common.NewPaginator(query.Page, query.PageSize, totalRecord)
	return adminInfos, *paginator
}

func (this *AdminDAO) getTotalCount(query *AdminInfoQuery) uint {
	db := this.conn.GetDb()
	sql := "select count(1) as totalRecord " +
		"from administrator_info a left join user_info u on a.user_id = u.id "

	whereCondition, args := this.buildWhere(query)
	if len(args) > 0 {
		sql += " where " + whereCondition
	}

	var totalRecord uint
	row := db.Raw(sql, args).Row()
	err := row.Scan(&totalRecord)
	if err != nil {
		fmt.Println("error", err)
	}
	return totalRecord
}

func (this *AdminDAO) GetAdminInfoOne(query *AdminInfoQuery) *AdminInfoBO {
	db := this.conn.GetDb()
	var adminInfoBo AdminInfoBO

	sql := "select a.id,a.user_id,a.type,a.status, a.created_by," +
		"a.created_time,a.updated_by,a.updated_time," +
		"u.open_id,u.union_id,u.nickname,u.real_name,u.gender,u.phone,u.password " +
		"from administrator_info a left join user_info u on a.user_id = u.id "

	whereCondition, args := this.buildWhere(query)
	if len(args) > 0 {
		sql += " where " + whereCondition
	}
	db.Raw(sql, args).Scan(&adminInfoBo)
	return &adminInfoBo
}

func (this *AdminDAO) buildWhere(query *AdminInfoQuery) (string, []interface{}) {
	args := make([]interface{}, 0)
	conditions := make([]string, 0)
	if query.ID > 0 {
		args = append(args, query.ID)
		conditions = append(conditions, " a.id = ?")
	}
	if len(query.PhoneLike) > 0 {
		args = append(args, "%"+query.PhoneLike+"%")
		conditions = append(conditions, " u.phone LIKE ? ")
	}
	if len(query.RealNameLike) > 0 {
		args = append(args, "%"+query.RealNameLike+"%")
		conditions = append(conditions, " u.real_name LIKE ? ")
	}
	if query.Status > 0 {
		args = append(args, query.Status)
		conditions = append(conditions, " a.status = ? ")
	}
	if query.AdminType > 0 {
		args = append(args, query.AdminType)
		conditions = append(conditions, " a.type = ? ")
	}
	return strings.Join(conditions, " and "), args
}
