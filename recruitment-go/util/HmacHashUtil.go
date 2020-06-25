package util

import (
	"crypto/hmac"
	"crypto/sha256"
	"fmt"
)

func HmacSha256Hash(data string, key string) string {
	mac := hmac.New(sha256.New, []byte(key))
	mac.Write([]byte(data))
	dataByte := mac.Sum(nil)
	return fmt.Sprintf("%X", dataByte)
}
