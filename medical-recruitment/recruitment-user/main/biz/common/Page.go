package common

type PageResult struct {
	Paginator Paginator   `json:"paginator"`
	Data      interface{} `json:"data"`
}

type Paginator struct {
	CurrentPage uint `json:"currentPage"`
	PageSize    byte `json:"pageSize"`
	TotalRecord uint `json:"totalRecord"`
	TotalPage   uint `json:"totalPage"`
}

func NewPaginator(page uint, pageSize byte, totalRecord uint) *Paginator {
	paginator := Paginator{CurrentPage: page, PageSize: pageSize, TotalRecord: totalRecord}
	if paginator.PageSize > 0 {
		paginator.TotalPage = paginator.TotalRecord / uint(paginator.PageSize)
	}
	return &paginator
}

func (paginator *Paginator) NextPage() {
	if paginator.CurrentPage <= 0 {
		paginator.CurrentPage = 1
	} else {
		paginator.CurrentPage++
	}
}

func (paginator *Paginator) HasNextPage() bool {
	return paginator.CurrentPage < paginator.TotalPage
}

func (paginator *Paginator) GetFrom() uint {
	if paginator.CurrentPage <= 1 {
		return 0
	} else {
		return (paginator.CurrentPage - 1) * uint(paginator.PageSize)
	}
}
