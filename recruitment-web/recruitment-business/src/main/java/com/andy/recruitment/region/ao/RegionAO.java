package com.andy.recruitment.region.ao;

import com.andy.recruitment.region.model.Region;
import java.util.List;

/**
 * 地区字典业务
 *
 * @author 庞先海 2018-12-27
 */
public interface RegionAO {

    /**
     * 通过id获取地区信息
     *
     * @param regionId 地区ID
     * @return 地区信息
     */
    Region getRegionById(Long regionId);

    /**
     * 通过parentId获取地区信息 只获取parentId子地区 不获取孙子及一下地区。 仅获取有效的地区
     *
     * @param parentId 父ID
     * @return 地区列表
     */
    List<Region> getRegionByParentId(Long parentId);

    /**
     * 通过 parentId和name查询
     *
     * @param parentId   父ID
     * @param regionName 地区名
     * @return 地区信息
     */
    Region getRegionByParent(Long parentId, String regionName);
}
