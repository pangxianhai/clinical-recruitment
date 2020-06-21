package common

import "time"

type BaseDO struct {
	ID          uint `gorm:"primary_key"`
	Status      byte
	CreatedBy   string
	CreatedTime time.Time
	UpdatedBy   string
	UpdatedTime time.Time
}
