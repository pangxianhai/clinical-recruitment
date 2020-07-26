package dao

import (
	_ "github.com/go-sql-driver/mysql"
	"github.com/jinzhu/gorm"
	"recruitment-user/main/config"
	"recruitment-user/main/logger"
)

func GetDbConn() *gorm.DB {
	jdbcConf := config.GetJdbcConf()
	connStr := jdbcConf.UserName + ":" + jdbcConf.Password + "@" + jdbcConf.Url
	//db, err := gorm.Open("mysql", "medical_recruitment-user:mmmm8888@tcp(127.0.0.1:3306)/medical_recruitment?charset=utf8&parseTime=True&loc=Local")
	db, err := gorm.Open("mysql", connStr)
	if err != nil {
		logger.Fatal("数据库连接失败", err)
	}
	db.DB().SetMaxIdleConns(10)
	db.DB().SetMaxOpenConns(100)

	db.LogMode(true)

	db.SetLogger(logger.Logger)
	return db
}
