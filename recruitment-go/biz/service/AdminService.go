package service

import (
	"encoding/json"
	"fmt"
	"recruitment/biz/dao"
	"recruitment/common"
	"recruitment/constant"
	"recruitment/util"
)

type AdminAddReq struct {
	Phone     string `json:"phone" binding:"required"`
	RealName  string `json:"realName" binding:"required"`
	Gender    byte   `json:"gender" binding:"required"`
	Password  string `json:"password" binding:"required"`
	AdminType byte   `json:"type" binding:"required"`
}

type AdminUpdateReq struct {
	RealName  string `json:"realName" `
	Gender    byte   `json:"gender" `
	AdminType byte   `json:"type" `
}

type AdminService struct {
	adminDAO dao.AdminDAO
	userDAO  dao.UserDAO
}

func (this *AdminService) ListAdminInfoPage(query *dao.AdminInfoQuery) ([]dao.AdminInfoBO, common.Paginator) {
	return this.adminDAO.ListAdminInfoPage(query)
}

func (this *AdminService) GetAdminInfoById(id uint) *dao.AdminInfoBO {
	query := dao.AdminInfoQuery{
		BaseQuery: common.BaseQuery{
			ID: id,
		},
	}
	return this.adminDAO.GetAdminInfoOne(&query)
}

func (this *AdminService) UpdateAdminStatus(id uint, status byte, operator string) uint {
	do := dao.AdminInfoDO{}
	do.ID = id
	do.Status = status
	code, err := this.adminDAO.UpdateAdmin(&do, operator)
	if err != nil {
		return code
	}
	return common.SUCCESS
}

func (this *AdminService) UpdateAdminInfo(adminId uint, update *AdminUpdateReq, operator string) (uint, error) {
	adminInfoDO := this.GetAdminInfoById(adminId)
	if adminInfoDO == nil {
		return common.ADMIN_NOT_EXIST, nil
	}
	userUpdateDO := dao.UserInfoDO{
		BaseDO: common.BaseDO{
			ID: adminInfoDO.UserId,
		},
		RealName: update.RealName,
		Gender:   update.Gender,
	}
	code, err := this.userDAO.UpdateUser(&userUpdateDO, operator)
	if common.SUCCESS != code {
		return code, err
	}
	adminUpdateDO := dao.AdminInfoDO{
		BaseDO: common.BaseDO{
			ID: adminId,
		},
		AdminType: update.AdminType,
	}
	fmt.Println("更新的管理员信息", adminUpdateDO)
	return this.adminDAO.UpdateAdmin(&adminUpdateDO, operator)
}

func (this *AdminService) Login(phone string, password string) (string, string, uint) {
	userInfoDO := this.userDAO.GetUserInfoByPhone(phone)
	if userInfoDO == nil {
		return "", "", common.USER_NOT_EXIST
	}
	adminDO := this.adminDAO.GetAdminByUserId(userInfoDO.ID)
	if adminDO == nil {
		return "", "", common.USER_NOT_EXIST
	}
	if adminDO.Status != constant.STATUS_NORMAL {
		return "", "", common.ADMIN_FREEZE
	}
	encodePass := util.HmacSha256Hash(password, phone)
	if encodePass != userInfoDO.Password {
		return "", "", common.USER_PASSWORD_ERROR
	}

	loginInfo := LoginInfo{
		UserId:   adminDO.UserId,
		UserName: userInfoDO.Phone,
		RealName: userInfoDO.RealName,
		Phone:    userInfoDO.Phone,
		RoleType: constant.ROLE_MANAGER,
		Attach:   string(adminDO.AdminType),
	}
	data, err := json.Marshal(loginInfo)
	if err != nil {
		fmt.Println("管理员登录错误", err)
		return "", "", common.SYS_ERROR
	}
	token, err := util.Rc4ByteEncode(data, loginInfo.UserName)
	if err != nil {
		fmt.Println("管理员登录错误", err)
		return "", "", common.SYS_ERROR
	}
	return token, userInfoDO.Phone, common.SUCCESS
}

func (this *AdminService) AddAdmin(adminAddReq *AdminAddReq, operator string) (uint, error) {
	userInfoDO := dao.UserInfoDO{
		Phone:    adminAddReq.Phone,
		RealName: adminAddReq.RealName,
		Gender:   adminAddReq.Gender,
		Password: util.HmacSha256Hash(adminAddReq.Password, adminAddReq.Phone),
	}
	errCode, err := this.userDAO.InsertUser(&userInfoDO, operator)
	if err != nil {
		return errCode, err
	}
	adminDO := dao.AdminInfoDO{
		UserId:    userInfoDO.ID,
		AdminType: adminAddReq.AdminType,
	}
	errCode, err = this.adminDAO.InsertAdmin(&adminDO, operator)
	if err != nil {
		return errCode, err
	}
	return common.SUCCESS, nil
}
