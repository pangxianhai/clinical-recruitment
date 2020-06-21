package util

import (
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"github.com/jinzhu/gorm"
)

type DbConnection struct {
}

func (conn *DbConnection) GetDb() *gorm.DB {
	db, err := gorm.Open("mysql", "medical_recruitment-user:mmmm8888@tcp(127.0.0.1:3306)/medical_recruitment?charset=utf8&parseTime=True&loc=Local")
	if err != nil {
		fmt.Println("数据库连接失败", err)
	}
	db.DB().SetMaxIdleConns(10)
	db.DB().SetMaxOpenConns(100)
	return db
}
