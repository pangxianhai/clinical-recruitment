package com.andy.recruitment.region.service;

import com.andy.recruitment.exception.RecruitmentErrorCode;
import com.andy.recruitment.exception.RecruitmentException;
import com.andy.recruitment.region.mapper.RegionMapper;
import com.andy.recruitment.region.model.Region;
import com.andy.recruitment.region.model.RegionDO;
import com.andy.recruitment.region.util.RegionUtil;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.commons.util.StringUtil;
import com.xgimi.commons.util.asserts.AssertUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 地区服务实现
 *
 * @author 庞先海 2018-12-26
 */
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionMapper regionMapper;

    @Autowired
    public RegionServiceImpl(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Override
    public Region getRegionById(Long regionId) {
        if (null == regionId) {
            return null;
        }
        RegionDO regionDO = new RegionDO();
        regionDO.setId(regionId);
        List<RegionDO> regionDOList = this.regionMapper.select(regionDO);
        if (CollectionUtil.isEmpty(regionDOList)) {
            return null;
        }
        AssertUtil.assertBoolean(regionDOList.size() == 1, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.TOO_MANY_RESULT);
        });
        return RegionUtil.transformRegion(regionDOList.get(0));
    }

    @Override
    public List<Region> getRegionByParentId(Long parentId) {
        if (parentId == null) {
            //默认获取中国下所有的省
            parentId = CHINA_REGION_ID;
        }
        RegionDO regionDO = new RegionDO();
        regionDO.setParentId(parentId);
        List<RegionDO> regionDOList = this.regionMapper.select(regionDO);
        if (CollectionUtil.isEmpty(regionDOList)) {
            return null;
        }
        return RegionUtil.transformRegion(regionDOList);
    }

    @Override
    public Region getRegionByParent(Long parentId, String regionName) {
        if (null == parentId || StringUtil.isEmpty(regionName)) {
            return null;
        }
        RegionDO regionDO = new RegionDO();
        regionDO.setParentId(parentId);
        regionDO.setRegionName(regionName);
        List<RegionDO> regionDOList = this.regionMapper.select(regionDO);
        if (CollectionUtil.isEmpty(regionDOList)) {
            return null;
        }
        AssertUtil.assertBoolean(regionDOList.size() == 1, () -> {
            throw new RecruitmentException(RecruitmentErrorCode.TOO_MANY_RESULT);
        });
        return RegionUtil.transformRegion(regionDOList.get(0));
    }
}
