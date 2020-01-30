package com.andy.recruitment.biz.region.service;

import com.andy.recruitment.dao.region.dao.RegionDAO;
import com.andy.recruitment.dao.region.entity.RegionDO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地区服务实现
 *
 * @author 庞先海 2020-01-30
 */
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionDAO regionDAO;

    @Autowired
    public RegionServiceImpl(RegionDAO regionDAO) {
        this.regionDAO = regionDAO;
    }

    @Override
    public RegionDO getRegionById(Long regionId) {
        return this.regionDAO.getRegionById(regionId);
    }

    @Override
    public String getRegionNameById(Long regionId) {
        RegionDO regionDo = this.getRegionById(regionId);
        if (regionDo != null) {
            return regionDo.getRegionName();
        }
        return null;
    }

    @Override
    public List<RegionDO> getRegionByParentId(Long parentId) {
        return this.regionDAO.getRegionByParentId(parentId);
    }

    @Override
    public RegionDO getRegionByParent(Long parentId, String regionName) {
        return this.regionDAO.getRegionByParent(parentId, regionName);
    }
}
