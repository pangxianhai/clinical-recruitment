package com.andy.recruitment.region.util;

import com.andy.recruitment.region.model.Region;
import com.andy.recruitment.region.model.RegionDO;
import com.xgimi.commons.util.CollectionUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;

/**
 * 地区工具类
 *
 * @author 庞先海 2018-12-27
 */
public class RegionUtil {

    public static Region transformRegion(RegionDO regionDO) {
        if (null == regionDO) {
            return null;
        }
        Region region = new Region();
        BeanUtils.copyProperties(regionDO, region);
        region.setRegionId(regionDO.getId());
        return region;
    }

    public static List<Region> transformRegion(List<RegionDO> regionDOList) {
        if (CollectionUtil.isEmpty(regionDOList)) {
            return null;
        }
        List<Region> regionList = new ArrayList<>(regionDOList.size());
        regionDOList.forEach(regionDO -> {
            regionList.add(transformRegion(regionDO));
        });
        return regionList;
    }

    public static RegionDO transformRegionDO(Region region) {
        RegionDO regionDO = new RegionDO();
        BeanUtils.copyProperties(region, regionDO);
        regionDO.setId(region.getRegionId());
        return regionDO;
    }
}
