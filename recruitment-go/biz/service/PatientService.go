package service

import (
	"recruitment/biz/dao"
	"recruitment/common"
	"recruitment/constant"
	"sync"
)

type PatientService struct {
	patientDAO *dao.PatientDAO
	regionDAO  *dao.RegionDAO
}

type PatientRes struct {
	Id          uint            `json:"id"`
	UserInfoRes UserInfoRes     `json:"userInfoRes"`
	UserId      uint            `json:"userId"`
	ProvinceId  uint            `json:"provinceId"`
	CityId      uint            `json:"cityId"`
	DistrictId  uint            `json:"districtId"`
	Age         uint            `json:"age"`
	Address     string          `json:"address"`
	Status      common.BaseType `json:"status"`
}

var patientService *PatientService
var patientServiceLock sync.Mutex

func GetPatientService() *PatientService {
	if patientService != nil {
		return patientService
	}
	patientServiceLock.Lock()
	defer patientServiceLock.Unlock()
	if patientService != nil {
		return patientService
	}
	patientService = &PatientService{}
	patientService.patientDAO = dao.GetPatientDAO()
	patientService.regionDAO = dao.GetRegionDAO()
	return patientService
}

func (this *PatientService) ListPatientPage(query *dao.PatientQuery) ([]PatientRes, common.Paginator) {
	patientBOs, paginator := this.patientDAO.ListPatientPage(query)
	patientResArr := this.transformPatientResArr(patientBOs)
	return patientResArr, paginator
}

func (this *PatientService) transformPatientResArr(patientBOs []dao.PatientBO) []PatientRes {
	if len(patientBOs) == 0 {
		return []PatientRes{}
	}
	patientResArr := make([]PatientRes, 0)
	for _, patientBO := range patientBOs {
		res := this.transformPatientRes(&patientBO)
		patientResArr = append(patientResArr, *res)
	}
	return patientResArr
}

func (this *PatientService) transformPatientRes(patientBO *dao.PatientBO) *PatientRes {
	patientRes := &PatientRes{
		Id: patientBO.ID,
		UserInfoRes: UserInfoRes{
			UserId:   patientBO.UserId,
			Nickname: patientBO.Nickname,
			Phone:    patientBO.Phone,
			RealName: patientBO.RealName,
			Gender:   common.BaseType{Code: patientBO.Gender, Desc: constant.GenderDict[patientBO.Gender]},
		},
		UserId:     patientBO.UserId,
		ProvinceId: patientBO.ProvinceId,
		CityId:     patientBO.CityId,
		DistrictId: patientBO.DistrictId,
		Age:        patientBO.Age,
		Address:    this.regionDAO.GetAddressName(patientBO.ProvinceId, patientBO.CityId, patientBO.DistrictId),
		Status:     common.BaseType{Code: patientBO.Status, Desc: constant.StatusDict[patientBO.Status]},
	}
	return patientRes
}
