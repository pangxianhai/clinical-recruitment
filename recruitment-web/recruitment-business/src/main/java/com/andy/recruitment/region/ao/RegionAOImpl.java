package com.andy.recruitment.region.ao;

import com.andy.recruitment.region.model.Region;
import com.andy.recruitment.region.service.RegionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 地区字典业务实现
 *
 * @author 庞先海 2018-12-27
 */
@Component
public class RegionAOImpl implements RegionAO {

    private final RegionService regionService;

    @Autowired
    public RegionAOImpl(RegionService regionService) {
        this.regionService = regionService;
    }

    @Override
    public Region getRegionById(Long regionId) {
        return this.regionService.getRegionById(regionId);
    }

    @Override
    public List<Region> getRegionByParentId(Long parentId) {
        return this.regionService.getRegionByParentId(parentId);

    }

    @Override
    public Region getRegionByParent(Long parentId, String regionName) {
        return this.regionService.getRegionByParent(parentId, regionName);
    }
}
