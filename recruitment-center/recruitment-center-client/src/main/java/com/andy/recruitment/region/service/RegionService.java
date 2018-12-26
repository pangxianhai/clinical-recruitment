package com.andy.recruitment.region.service;

import com.andy.recruitment.region.model.Region;
import java.util.List;

/**
 * 地区字典服务
 *
 * @author 庞先海 2018-12-26
 */
public interface RegionService {

    /**
     * 中国的默认ID
     */
    Long CHINA_REGION_ID = 1L;

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

    /**
     * 通过parentId获取地区信息 只获取parentId子地区 不获取孙子及一下地区。 获取所有状态的地区 改该方方法直接读取数据库
     *
     * @param parentId 父ID
     * @return 地区列表
     */
    List<Region> getAllRegionByParentId(Long parentId);
}
