package controller

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"recruitment/biz/dao"
	"recruitment/biz/service"
	"recruitment/common"
	"recruitment/constant"
	"strconv"
)

type AdminRes struct {
	Id uint `json:"id"`
	UserInfoRes
	AdminType common.BaseType `json:"adminType"`
}

type AdminController struct {
	adminService service.AdminService
}

func (this *AdminController) AdminLogin(c *gin.Context) {
	//userInfo := this.userDAO.GetUserInfoByPhone("17780583960")
	//result := common.Result(userInfo)
	c.JSON(200, gin.H{})
}

/**
分页查询
*/
func (this *AdminController) ListAdminInfoPage(c *gin.Context) {
	var query dao.AdminInfoQuery
	c.ShouldBind(&query)
	adminDOArray, paginator := this.adminService.ListAdminInfoPage(&query)
	adminResArray := transformAdminResArray(adminDOArray)
	pageResult := common.PageResult{Paginator: paginator, Data: adminResArray}
	result := common.Result(pageResult)
	c.JSON(http.StatusOK, result)
}

/**
冻结用户
*/
func (this *AdminController) FreezeAdmin(c *gin.Context) {
	idStr := c.Param("adminId")
	id, err := strconv.ParseUint(idStr, 10, 32)
	if err != nil {
		c.JSON(http.StatusOK, common.ResultError(common.PARAM_ERROR))
		return
	}
	this.adminService.UpdateAdminStatus(uint(id), constant.STATUS_FREEZE, "系统")
	c.JSON(http.StatusOK, common.Result(common.SUCCESS))
}

/*
解冻用户
*/
func (this *AdminController) UnfreezeAdmin(c *gin.Context) {
	idStr := c.Param("adminId")
	id, err := strconv.ParseUint(idStr, 10, 32)
	if err != nil {
		c.JSON(http.StatusOK, common.ResultError(common.PARAM_ERROR))
		return
	}
	this.adminService.UpdateAdminStatus(uint(id), constant.STATUS_NORMAL, "系统")
	c.JSON(http.StatusOK, common.Result(common.SUCCESS))
}

func transformAdminResArray(adminDOArray []dao.AdminInfoDO) []AdminRes {
	if len(adminDOArray) == 0 {
		return []AdminRes{}
	}
	adminResArray := make([]AdminRes, len(adminDOArray))
	for i, adminDO := range adminDOArray {
		res := transformAdminRes(&adminDO)
		adminResArray[i] = *res
	}
	return adminResArray
}

func transformAdminRes(adminDO *dao.AdminInfoDO) *AdminRes {
	adminRes := AdminRes{}
	adminRes.Id = adminDO.ID
	adminRes.UserId = adminDO.UserId
	adminRes.RealName = adminDO.RealName
	adminRes.Phone = adminDO.Phone
	adminRes.Nickname = adminDO.Nickname
	adminRes.Gender = common.BaseType{Code: adminDO.Gender, Desc: constant.GenderDict[adminDO.Gender]}
	adminRes.Status = common.BaseType{Code: adminDO.Status, Desc: constant.StatusDict[adminDO.Status]}
	adminRes.AdminType = common.BaseType{Code: adminDO.AdminType, Desc: constant.AdminTypeDict[adminDO.AdminType]}
	return &adminRes
}
