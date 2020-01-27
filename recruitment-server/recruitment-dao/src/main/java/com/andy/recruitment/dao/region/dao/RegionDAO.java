package com.andy.recruitment.dao.region.dao;

import com.andy.recruitment.dao.region.model.RegionDO;
import java.util.List;

/**
 * 地区服务DAO
 *
 * @author 庞先海 2020-01-27
 */
public interface RegionDAO {

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
    RegionDO getRegionById(Long regionId);

    /**
     * 通过parentId获取地区信息 只获取parentId子地区 不获取孙子及一下地区。 仅获取有效的地区
     *
     * @param parentId 父ID
     * @return 地区列表
     */
    List<RegionDO> getRegionByParentId(Long parentId);

    /**
     * 通过 parentId和name查询
     *
     * @param parentId   父ID
     * @param regionName 地区名
     * @return 地区信息
     */
    RegionDO getRegionByParent(Long parentId, String regionName);
}
