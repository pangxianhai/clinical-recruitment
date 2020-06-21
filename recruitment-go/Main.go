package main

import (
	"github.com/gin-gonic/gin"
	"recruitment/web"
)

func main() {
	engine := gin.Default()
	router := web.Router{}
	router.RouterPath(engine)
	_ = engine.Run(":7099")
}
