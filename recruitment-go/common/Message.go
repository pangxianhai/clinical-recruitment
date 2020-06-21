package common

import (
	"github.com/jinzhu/configor"
	"strconv"
)

var msg map[string]string

func init() {
	m := make(map[string]string)
	configor.Load(&m, "resources/error-zh-CN.yml")
	msg = m
}

func GetMessage(code uint) string {
	key := strconv.Itoa(int(code))
	return msg[key]
}
