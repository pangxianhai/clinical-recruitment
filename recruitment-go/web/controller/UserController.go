package controller

import (
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
