package common

import "time"

type BaseType struct {
	Code uint   `json:"code"`
	Desc string `json:"desc"`
}

type BaseQuery struct {
	ID       uint `json:"id"`
	Page     uint `json:"page" form:"currentPage"`
	PageSize byte `json:"pageSize" form:"pageSize"`
}

func (baseQuery *BaseQuery) GetFrom() uint {
	if baseQuery.Page <= 0 || baseQuery.PageSize <= 0 {
		return 0
	} else {
		return (baseQuery.Page - 1) * uint(baseQuery.PageSize)
	}
}

func (baseQuery *BaseQuery) GetLimit() byte {
	if baseQuery.PageSize <= 0 {
		return 10
	} else {
		return baseQuery.PageSize
	}
}

type BaseDO struct {
	ID          uint
	Status      byte
	CreatedBy   string
	CreatedTime time.Time
	UpdatedBy   string
	UpdatedTime time.Time
}
