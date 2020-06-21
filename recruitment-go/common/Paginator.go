package common

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

func (this *Paginator) NextPage() {
	if this.CurrentPage <= 0 {
		this.CurrentPage = 1
	} else {
		this.CurrentPage++
	}
}

func (this *Paginator) HasNextPage() bool {
	return this.CurrentPage < this.TotalPage
}

func (this *Paginator) GetFrom() uint {
	if this.CurrentPage <= 1 {
		return 0
	} else {
		return (this.CurrentPage - 1) * uint(this.PageSize)
	}
}
