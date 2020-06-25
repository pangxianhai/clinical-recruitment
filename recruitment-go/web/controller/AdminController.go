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
	AdminType common.BaseType `json:"adminType"`
}

type AdminLoginReq struct {
	UserName string `json:"userName"`
	Password string `json:"password"`
}

type AdminController struct {
	adminService service.AdminService
	userService  service.UserService
}

func (this *AdminController) AdminLogin(context *gin.Context) {
	var loginReq AdminLoginReq
	context.ShouldBind(&loginReq)
	token, userName, code := this.adminService.Login(loginReq.UserName, loginReq.Password)
	if code != common.SUCCESS {
		context.JSON(http.StatusOK, common.ResultError(code))
		return
	}

	loginRes := LoginRes{UserName: userName, Token: token}
	context.JSON(http.StatusOK, common.Result(loginRes))
}

func (this *AdminController) GetAdminLoginInfo(context *gin.Context) {
	loginInfo, exists := context.Get("loginInfo")
	if !exists {
		context.JSON(http.StatusOK, common.ResultError(common.UNAUTHORIZED))
	} else {
		result := common.Result(loginInfo)
		context.JSON(http.StatusOK, result)
	}
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
	this.adminService.UpdateAdminStatus(uint(id), constant.STATUS_FREEZE, GetOperator(context))
	context.JSON(http.StatusOK, common.Result(common.SUCCESS))
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
	this.adminService.UpdateAdminStatus(uint(id), constant.STATUS_NORMAL, GetOperator(context))
	context.JSON(http.StatusOK, common.Result(common.SUCCESS))
}

func (this *AdminController) AddAdmin(context *gin.Context) {
	var addReq service.AdminAddReq
	err := context.ShouldBind(&addReq)
	fmt.Println("添加管理员参数", addReq)
	if err != nil {
		fmt.Println("绑定参数错误", err)
		context.JSON(http.StatusOK, common.ResultError(common.PARAM_ERROR))
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
