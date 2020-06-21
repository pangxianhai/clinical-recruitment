package service

import (
	"recruitment/biz/dao"
	"recruitment/common"
)

type AdminService struct {
	adminDAO dao.AdminDAO
}

func (this *AdminService) ListAdminInfoPage(query *dao.AdminInfoQuery) ([]dao.AdminInfoDO, common.Paginator) {
	return this.adminDAO.ListAdminInfoPage(query)
}

func (this *AdminService) UpdateAdminStatus(id uint, status byte, operator string) (bool, uint) {
	do := dao.AdminInfoDO{}
	do.ID = id
	do.Status = status

	code, err := this.adminDAO.UpdateAdmin(&do, operator)
	if err != nil {
		return false, code
	}
	return true, 0
}
