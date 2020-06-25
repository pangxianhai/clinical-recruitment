package util

import (
	"crypto/rc4"
	"encoding/hex"
)

func Rc4ByteEncode(data []byte, key string) (string, error) {
	cipher, err := rc4.NewCipher([]byte(key))
	if err != nil {
		return "", err
	}
	dst := make([]byte, len(data))
	cipher.XORKeyStream(dst, data)
	return hex.EncodeToString(dst), nil
}

func Rc4Encode(data string, key string) (string, error) {
	cipher, err := rc4.NewCipher([]byte(key))
	if err != nil {
		return "", err
	}
	src := []byte(data)
	dst := make([]byte, len(src))
	cipher.XORKeyStream(dst, src)
	return hex.EncodeToString(dst), nil
}

func Rc4Decode(data string, key string) (string, error) {
	dst, err := Rc4DecodeByte(data, key)
	if err != nil {
		return "", err
	}
	return string(dst), nil
}

func Rc4DecodeByte(data string, key string) ([]byte, error) {
	cipher, err := rc4.NewCipher([]byte(key))
	if err != nil {
		return nil, err
	}
	src, err := hex.DecodeString(data)
	if err != nil {
		return nil, err
	}
	dst := make([]byte, len(src))
	cipher.XORKeyStream(dst, src)
	return dst, nil
}
