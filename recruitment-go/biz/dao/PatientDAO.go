package dao

import (
	"fmt"
	"recruitment/common"
	"recruitment/util"
	"strings"
	"sync"
)

type PatientDO struct {
	common.BaseDO
	UserId     uint `json:"userId"`
	ProvinceId uint `json:"provinceId"`
	CityId     uint `json:"cityId"`
	DistrictId uint `json:"districtId"`
	Age        uint `json:"age"`
}

func (PatientDO) TableName() string {
	return "patient_info"
}

type PatientBO struct {
	UserInfoDO
	UserId     uint `json:"userId"`
	ProvinceId uint `json:"provinceId"`
	CityId     uint `json:"cityId"`
	DistrictId uint `json:"districtId"`
	Age        uint `json:"age"`
}

type PatientQuery struct {
	common.BaseQuery
	UserId       uint   `json:"userId"`
	ProvinceId   uint   `json:"provinceId"`
	CityId       uint   `json:"cityId"`
	DistrictId   uint   `json:"districtId"`
	PhoneLike    string `form:"phoneLike" `
	RealNameLike string `form:"realNameLike" `
	Status       byte   `form:"status" `
}

type PatientDAO struct {
	conn util.DbConnection
}

var patientDAO *PatientDAO
var patientDAOLock sync.Mutex

func GetPatientDAO() *PatientDAO {
	if patientDAO != nil {
		return patientDAO
	}
	patientDAOLock.Lock()
	defer patientDAOLock.Unlock()
	if patientDAO != nil {
		return patientDAO
	}
	patientDAO = &PatientDAO{}
	return patientDAO
}

func (this *PatientDAO) ListPatientPage(query *PatientQuery) ([]PatientBO, common.Paginator) {
	db := this.conn.GetDb()
	var patientInfo = make([]PatientBO, query.PageSize)
	sql := "select p.id,p.user_id,p.province_id,p.city_id,p.district_id,p.age,p.status,p.created_by," +
		"p.created_time,p.updated_by,p.updated_time,u.open_id," +
		"u.union_id,u.nickname,u.real_name,u.gender,u.phone,u.password from patient_info p " +
		"left join user_info u on (p.user_id=u.id) "

	whereCondition, args := this.buildWhere(query)
	if len(args) > 0 {
		sql += " where " + whereCondition
	}
	sql += " limit ?,? "
	args = append(args, query.GetFrom())
	args = append(args, query.GetLimit())
	db.Raw(sql, args...).Scan(&patientInfo)
	totalRecord := this.getTotalCount(query)
	paginator := common.NewPaginator(query.Page, query.PageSize, totalRecord)
	return patientInfo, *paginator
}

func (this *PatientDAO) getTotalCount(query *PatientQuery) uint {
	db := this.conn.GetDb()
	sql := "select count(1) from patient_info p " +
		"left join user_info u on (p.user_id=u.id) "

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

func (this *PatientDAO) GetPatientOne(query *PatientQuery) *PatientBO {
	db := this.conn.GetDb()
	var patientInfo PatientBO
	sql := "select p.id,p.user_id,p.province_id,p.city_id,p.district_id,p.age,p.status,p.created_by," +
		"p.created_time,p.updated_by,p.updated_time,u.open_id," +
		"u.union_id,u.nickname,u.real_name,u.gender,u.phone,u.password from patient_info p " +
		"left join user_info u on (p.user_id=u.id) "
	whereCondition, args := this.buildWhere(query)
	if len(args) > 0 {
		sql += " where " + whereCondition
	}
	sql += " limit 1 "
	db.Raw(sql, args...).Scan(&patientInfo)
	return &patientInfo
}

func (this *PatientDAO) buildWhere(query *PatientQuery) (string, []interface{}) {
	args := make([]interface{}, 0)
	conditions := make([]string, 0)
	if query.ID > 0 {
		args = append(args, query.ID)
		conditions = append(conditions, " p.id = ?")
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
		conditions = append(conditions, " p.status = ? ")
	}
	if query.UserId > 0 {
		args = append(args, query.UserId)
		conditions = append(conditions, " p.user_id = ?")
	}
	if query.ProvinceId > 0 {
		args = append(args, query.ProvinceId)
		conditions = append(conditions, " p.province_id = ?")
	}
	if query.CityId > 0 {
		args = append(args, query.CityId)
		conditions = append(conditions, " p.city_id = ?")
	}
	if query.DistrictId > 0 {
		args = append(args, query.DistrictId)
		conditions = append(conditions, " p.district_id = ?")
	}

	return strings.Join(conditions, " and "), args
}
