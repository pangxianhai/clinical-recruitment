package web

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"recruitment/biz/service"
	"recruitment/common"
	"recruitment/constant"
	"recruitment/web/controller"
)

type Router struct {
}

func (Router) RouterPath(engine *gin.Engine) {
	adminGroup := engine.Group("/administrator")
	{
		adminController := &controller.AdminController{}
		adminGroup.POST("/login", adminController.AdminLogin)
		adminGroup.GET("/loginInfo", adminController.GetAdminLoginInfo)
		adminGroup.GET("/", ManagerAuthMiddleWare(), adminController.ListAdminInfoPage)
		adminGroup.POST("/", ManagerAuthMiddleWare(), adminController.AddAdmin)
		adminGroup.PUT("/:adminId/freeze", ManagerAuthMiddleWare(), adminController.FreezeAdmin)
		adminGroup.PUT("/:adminId/unfreeze", ManagerAuthMiddleWare(), adminController.UnfreezeAdmin)
	}

	userGroup := engine.Group("/user")
	{
		userController := &controller.UserController{}
		userGroup.GET("/phone/:phone", userController.GetUserInfoByPhone)
	}
}

func UserMiddleWare() gin.HandlerFunc {
	return func(context *gin.Context) {
		token := context.GetHeader("token")
		userName := context.GetHeader("userName")
		if len(token) == 0 || len(userName) == 0 {
			return
		}
		userService := service.UserService{}
		loginInfo, err := userService.GetCurrentUser(token, userName)
		if err != nil {
			return
		}
		context.Set("loginInfo", loginInfo)
	}

}

func ManagerAuthMiddleWare() gin.HandlerFunc {
	return func(context *gin.Context) {
		loginInfo, exists := controller.GetLoginInfo(context)
		if !exists {
			context.JSON(http.StatusOK, common.ResultError(common.UNAUTHORIZED))
			context.Abort()
			return
		}
		//解密正确
		if loginInfo.RoleType != constant.ROLE_MANAGER {
			context.JSON(http.StatusOK, common.ResultError(common.UNAUTHORIZED))
			context.Abort()
		} else {
			context.Next()
		}
		return
	}
}

func CustomerAuthMiddleWare() gin.HandlerFunc {
	return func(context *gin.Context) {
		loginInfo, exists := controller.GetLoginInfo(context)
		if !exists {
			context.JSON(http.StatusOK, common.ResultError(common.UNAUTHORIZED))
			context.Abort()
			return
		}
		//解密正确
		if loginInfo.RoleType != constant.ROLE_CUSTOMER {
			context.JSON(http.StatusOK, common.ResultError(common.UNAUTHORIZED))
			context.Abort()
		} else {
			context.Next()
		}
		return
	}
}
