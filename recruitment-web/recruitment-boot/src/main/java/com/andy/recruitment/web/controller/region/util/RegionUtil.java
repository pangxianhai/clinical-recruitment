package com.andy.recruitment.web.controller.region.util;

import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.region.model.Region;
import com.andy.recruitment.web.controller.region.response.RegionVO;
import com.xgimi.commons.util.CollectionUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 地区工具类
 *
 * @author 庞先海 2019-01-10
 */
public class RegionUtil {

    public static RegionVO transformRegionVO(Region region) {
        if (null == region) {
            return null;
        }
        RegionVO regionVO = new RegionVO();
        regionVO.setLabel(region.getRegionName());
        regionVO.setValue(String.valueOf(region.getRegionId()));
        regionVO.setRegionId(region.getRegionId());
        return regionVO;
    }

    public static List<RegionVO> transformRegionVO(List<Region> regionList) {
        if (CollectionUtil.isEmpty(regionList)) {
            return null;
        }
        return regionList.stream().map(RegionUtil::transformRegionVO).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static List<RegionVO> buildSelectOptions(RegionAO regionAO) {
        List<Region> regionList = regionAO.getRegionByParentId(null);
        List<RegionVO> regionVOList = RegionUtil.transformRegionVO(regionList);
        if (CollectionUtil.isNotEmpty(regionVOList)) {
            for (RegionVO regionVO : regionVOList) {
                if (null == regionVO.getRegionId()) {
                    continue;
                }
                List<Region> children = regionAO.getRegionByParentId(regionVO.getRegionId());
                regionVO.setChildren(RegionUtil.transformRegionVO(children));
            }
        }
        return regionVOList;
    }
}
