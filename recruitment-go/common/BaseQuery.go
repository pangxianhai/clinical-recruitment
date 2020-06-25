package common

type BaseQuery struct {
	ID       uint `json:"id"`
	Page     uint `json:"page" form:"currentPage"`
	PageSize byte `json:"pageSize" form:"pageSize"`
}

func (this *BaseQuery) GetFrom() uint {
	if this.Page <= 0 || this.PageSize <= 0 {
		return 0
	} else {
		return (this.Page - 1) * uint(this.PageSize)
	}
}

func (this *BaseQuery) GetLimit() byte {
	if this.PageSize <= 0 {
		return 10
	} else {
		return this.PageSize
	}
}
