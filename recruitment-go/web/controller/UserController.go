package controller

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
	"recruitment/biz/service"
	"recruitment/common"
)

type UserController struct {
	userService service.UserService
}

type LoginRes struct {
	UserName string `json:"userName"`
	Token    string `json:"token"`
}

func (this *UserController) GetUserInfoByPhone(context *gin.Context) {
	phone := context.Param("phone")
	userInfoRes := this.userService.GetUserInfoByPhone(phone)
	context.JSON(http.StatusOK, common.Result(userInfoRes))
}

func (this *UserController) GetCurrentUserInfo(context *gin.Context) {
	loginInfo, exists := context.Get("loginInfo")
	if !exists {
		context.JSON(http.StatusOK, common.ResultError(common.UNAUTHORIZED))
	} else {
		result := common.Result(loginInfo)
		context.JSON(http.StatusOK, result)
	}
}

func (this *UserController) UpdatePassword(context *gin.Context) {
	loginInfo, exists := GetLoginInfo(context)
	if !exists {
		context.JSON(http.StatusOK, common.ResultError(common.UNAUTHORIZED))
		return
	}
	var updateReq service.UpdatePasswordReq
	err := context.ShouldBind(&updateReq)
	if err != nil {
		fmt.Println("绑定参数错误", err)
		context.JSON(http.StatusOK, common.ResultErrorMsg(common.PARAM_ERROR, err.Error()))
		return
	}
	updateReq.Phone = loginInfo.Phone
	code, err := this.userService.UpdateUserPassword(&updateReq, loginInfo.RealName)
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
