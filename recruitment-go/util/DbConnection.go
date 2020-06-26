package util

import (
	"fmt"
	"log"

	_ "github.com/go-sql-driver/mysql"
	"github.com/jinzhu/gorm"
)

type DbConnection struct {
}

type MyLogger struct {
}

func (logger *MyLogger) Print(values ...interface{}) {
	var (
		level  = values[0]
		source = values[1]
	)

	if level == "sql" {
		sql := values[3].(string)
		log.Println(source, level, sql)
	} else {
		log.Println(values)
	}
}

func (conn *DbConnection) GetDb() *gorm.DB {
	db, err := gorm.Open("mysql", "medical_recruitment-user:mmmm8888@tcp(127.0.0.1:3306)/medical_recruitment?charset=utf8&parseTime=True&loc=Local")
	if err != nil {
		fmt.Println("数据库连接失败", err)
	}
	db.DB().SetMaxIdleConns(10)
	db.DB().SetMaxOpenConns(100)
	logger := &MyLogger{}

	db.LogMode(true)

	db.SetLogger(logger)
	return db
}
