package main

import (
	"github.com/gin-gonic/gin"
	"recruitment/web"
)

func main() {
	engine := gin.New()
	engine.Use(gin.Recovery())
	engine.Use(web.UserMiddleWare())
	router := web.Router{}
	router.RouterPath(engine)
	_ = engine.Run(":7099")
}
