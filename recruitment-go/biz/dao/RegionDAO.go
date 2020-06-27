package dao

import (
	"recruitment/common"
	"recruitment/util"
	"sync"
)

type RegionDO struct {
	common.BaseDO
	RegionName string
	ParentId   uint
	RegionType byte
}

func (RegionDO) TableName() string {
	return "region_dict"
}

const (
	regionColumns = "id,region_name,parent_id,region_type,status," +
		"created_by,created_time,updated_by,updated_time"
)

type RegionDAO struct {
	conn               util.DbConnection
	regionDict         map[uint]RegionDO
	regionChildrenDict map[uint][]RegionDO
}

var regionDAO *RegionDAO
var regionDAOLock sync.Mutex

func GetRegionDAO() *RegionDAO {
	if regionDAO != nil {
		return regionDAO
	}
	regionDAOLock.Lock()
	defer regionDAOLock.Unlock()
	if regionDAO != nil {
		return regionDAO
	}
	regionDAO = &RegionDAO{
		regionDict:         map[uint]RegionDO{},
		regionChildrenDict: map[uint][]RegionDO{},
	}
	return regionDAO
}

func (this *RegionDAO) GetRegionById(regionId uint) *RegionDO {
	if regionId <= 0 {
		return nil
	}
	regionDO, exist := this.regionDict[regionId]
	if exist {
		return &regionDO
	}
	regionDO = RegionDO{}
	this.conn.GetDb().Select(regionColumns).Where("id = ?", regionId).First(&regionDO)
	if regionDO.ID > 0 && len(regionDO.RegionName) > 0 {
		this.regionDict[regionId] = regionDO
		return &regionDO
	} else {
		return nil
	}
}

func (this *RegionDAO) GetRegionByParentId(parentId uint) []RegionDO {
	if parentId <= 0 {
		return make([]RegionDO, 0)
	}
	var regionArr = this.regionChildrenDict[parentId]
	if len(regionArr) > 0 {
		return regionArr
	}
	regionArr = make([]RegionDO, 10)
	this.conn.GetDb().Select(regionColumns).Where("parent_id = ?", parentId).Find(&regionArr)
	if len(regionArr) > 0 {
		this.regionChildrenDict[parentId] = regionArr
	}
	return regionArr
}

func (this *RegionDAO) GetAddressName(provinceId uint, cityId uint, districtId uint) string {
	province := this.GetRegionById(provinceId)
	city := this.GetRegionById(cityId)
	district := this.GetRegionById(districtId)
	addressName := ""
	if province != nil {
		addressName += province.RegionName
	}
	if city != nil {
		if province == nil || (province.RegionName != city.RegionName) {
			addressName += city.RegionName
		}
	}
	if district != nil {
		addressName += district.RegionName
	}
	return addressName
}
