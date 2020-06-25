package controller

import (
	"github.com/gin-gonic/gin"
	"recruitment/biz/service"
)

func GetLoginInfo(context *gin.Context) (loginInfo *service.LoginInfo, exists bool) {
	value, exists := context.Get("loginInfo")
	loginInfo = value.(*service.LoginInfo)
	return
}

func GetOperator(context *gin.Context) (name string) {
	loginInfo, exists := GetLoginInfo(context)
	if !exists {
		name = ""
	} else {
		name = loginInfo.RealName
	}
	return
}
