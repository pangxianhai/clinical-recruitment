package controller

import (
	"fmt"
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
	service.UserInfoRes
	AdminType common.BaseType `json:"type"`
}

type AdminLoginReq struct {
	UserName string `json:"userName" binding:"required"`
	Password string `json:"password" binding:"required"`
}

type AdminController struct {
	adminService service.AdminService
	userService  service.UserService
}

func (this *AdminController) AdminLogin(context *gin.Context) {
	var loginReq AdminLoginReq
	err := context.ShouldBind(&loginReq)
	if err != nil {
		context.JSON(http.StatusOK, common.ResultErrorMsg(common.PARAM_ERROR, err.Error()))
		return
	}
	token, userName, code := this.adminService.Login(loginReq.UserName, loginReq.Password)
	if code != common.SUCCESS {
		context.JSON(http.StatusOK, common.ResultError(code))
		return
	}

	loginRes := LoginRes{UserName: userName, Token: token}
	context.JSON(http.StatusOK, common.Result(loginRes))
}

/**
分页查询
*/
func (this *AdminController) ListAdminInfoPage(context *gin.Context) {
	var query dao.AdminInfoQuery
	_ = context.ShouldBind(&query)
	adminBOArray, paginator := this.adminService.ListAdminInfoPage(&query)
	adminResArray := transformAdminResArray(adminBOArray)
	pageResult := common.PageResult{Paginator: paginator, Data: adminResArray}
	result := common.Result(pageResult)
	context.JSON(http.StatusOK, result)
}

func (this *AdminController) GetAdminInfoById(context *gin.Context) {
	idStr := context.Param("adminId")
	id, err := strconv.ParseUint(idStr, 10, 32)
	if err != nil {
		context.JSON(http.StatusOK, common.ResultErrorMsg(common.PARAM_ERROR, err.Error()))
		return
	}
	adminBO := this.adminService.GetAdminInfoById(uint(id))
	adminRes := transformAdminRes(adminBO)
	context.JSON(http.StatusOK, common.Result(adminRes))
}

func (this *AdminController) UpdateAdminInfo(context *gin.Context) {
	var updateReq service.AdminUpdateReq
	err := context.ShouldBind(&updateReq)
	if err != nil {
		fmt.Println("绑定参数错误", err)
		context.JSON(http.StatusOK, common.ResultErrorMsg(common.PARAM_ERROR, err.Error()))
		return
	}
	idStr := context.Param("adminId")
	id, err := strconv.ParseUint(idStr, 10, 32)
	if err != nil {
		context.JSON(http.StatusOK, common.ResultErrorMsg(common.PARAM_ERROR, err.Error()))
		return
	}
	code, err := this.adminService.UpdateAdminInfo(uint(id), &updateReq, GetOperator(context))
	if common.SUCCESS != code {
		if err != nil {
			context.JSON(http.StatusOK, common.ResultErrorMsg(code, err.Error()))
		} else {
			context.JSON(http.StatusOK, common.ResultError(code))
		}
	} else {
		context.JSON(http.StatusOK, common.Result(nil))
	}
}

/**
冻结用户
*/
func (this *AdminController) FreezeAdmin(context *gin.Context) {
	idStr := context.Param("adminId")
	id, err := strconv.ParseUint(idStr, 10, 32)
	if err != nil {
		context.JSON(http.StatusOK, common.ResultError(common.PARAM_ERROR))
		return
	}
	code := this.adminService.UpdateAdminStatus(uint(id), constant.STATUS_FREEZE, GetOperator(context))
	if common.SUCCESS == code {
		context.JSON(http.StatusOK, common.Result(nil))
	} else {
		context.JSON(http.StatusOK, common.ResultError(code))
	}
}

/*
解冻用户
*/
func (this *AdminController) UnfreezeAdmin(context *gin.Context) {
	idStr := context.Param("adminId")
	id, err := strconv.ParseUint(idStr, 10, 32)
	if err != nil {
		context.JSON(http.StatusOK, common.ResultError(common.PARAM_ERROR))
		return
	}
	code := this.adminService.UpdateAdminStatus(uint(id), constant.STATUS_NORMAL, GetOperator(context))
	if common.SUCCESS == code {
		context.JSON(http.StatusOK, common.Result(nil))
	} else {
		context.JSON(http.StatusOK, common.ResultError(code))
	}
}

func (this *AdminController) AddAdmin(context *gin.Context) {
	var addReq service.AdminAddReq
	err := context.ShouldBind(&addReq)
	if err != nil {
		fmt.Println("绑定参数错误", err)
		context.JSON(http.StatusOK, common.ResultErrorMsg(common.PARAM_ERROR, err.Error()))
		return
	}
	code, err := this.adminService.AddAdmin(&addReq, GetOperator(context))
	if err != nil {
		context.JSON(http.StatusOK, common.ResultError(code))
	} else {
		context.JSON(http.StatusOK, common.Result(common.SUCCESS))
	}
	return
}

func transformAdminResArray(adminBOArray []dao.AdminInfoBO) []AdminRes {
	if len(adminBOArray) == 0 {
		return []AdminRes{}
	}
	adminResArray := make([]AdminRes, len(adminBOArray))
	for i, adminBO := range adminBOArray {
		res := transformAdminRes(&adminBO)
		adminResArray[i] = *res
	}
	return adminResArray
}

func transformAdminRes(adminBO *dao.AdminInfoBO) *AdminRes {
	adminRes := AdminRes{}
	adminRes.Id = adminBO.ID
	adminRes.UserId = adminBO.UserId
	adminRes.RealName = adminBO.RealName
	adminRes.Phone = adminBO.Phone
	adminRes.Nickname = adminBO.Nickname
	adminRes.Gender = common.BaseType{Code: adminBO.Gender, Desc: constant.GenderDict[adminBO.Gender]}
	adminRes.Status = common.BaseType{Code: adminBO.Status, Desc: constant.StatusDict[adminBO.Status]}
	adminRes.AdminType = common.BaseType{Code: adminBO.AdminType, Desc: constant.AdminTypeDict[adminBO.AdminType]}
	return &adminRes
}
