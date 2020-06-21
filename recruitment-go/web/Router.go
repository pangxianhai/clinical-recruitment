package web

import (
	"github.com/gin-gonic/gin"
	"recruitment/web/controller"
)

type Router struct {
}

func (Router) RouterPath(engine *gin.Engine) {
	adminGroup := engine.Group("/administrator")
	{
		adminController := &controller.AdminController{}
		adminGroup.GET("/login", adminController.AdminLogin)
		adminGroup.GET("/", adminController.ListAdminInfoPage)
		adminGroup.PUT("/:adminId/freeze", adminController.FreezeAdmin)
		adminGroup.PUT("/:adminId/unfreeze", adminController.UnfreezeAdmin)

	}

	userGroup := engine.Group("/user")
	{
		//userController := &controller.UserController{}
		userGroup.PUT("/:userId/freeze")
		userGroup.PUT("/:userId/unfreeze")

	}
}
