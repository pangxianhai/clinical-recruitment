package com.andy.recruitment.web.controller.region.util;

import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.region.model.Region;
import com.andy.recruitment.web.controller.region.response.RegionVO;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.util.BeanUtil;
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
        BeanUtil.copyProperties(region, regionVO);
        return regionVO;
    }

    public static List<RegionVO> transformRegionVO(List<Region> regionList) {
        if (CollectionUtil.isEmpty(regionList)) {
            return null;
        }
        return regionList.stream().map(RegionUtil::transformRegionVO).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static List<RegionVO> buildSelectOptions(Long regionId, RegionAO regionAO) {
        List<Region> regionList = regionAO.getRegionByParentId(regionId);
        if (CollectionUtil.isEmpty(regionList)) {
            return null;
        }
        List<RegionVO> regionVOList = RegionUtil.transformRegionVO(regionList);
        if (CollectionUtil.isNotEmpty(regionVOList)) {
            for (RegionVO regionVO : regionVOList) {
                if (null == regionVO.getRegionId()) {
                    continue;
                }
                List<RegionVO> childRegionVOList = RegionUtil.buildSelectOptions(regionVO.getRegionId(), regionAO);
                if (CollectionUtil.isNotEmpty(childRegionVOList)) {
                    regionVO.setChildren(childRegionVOList);
                }
            }
        }
        return regionVOList;
    }
}
