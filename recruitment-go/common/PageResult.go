package common

type PageResult struct {
	Paginator Paginator   `json:"paginator"`
	Data      interface{} `json:"data"`
}
