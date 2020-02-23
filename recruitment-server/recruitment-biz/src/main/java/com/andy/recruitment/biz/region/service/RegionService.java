package com.andy.recruitment.biz.region.service;

import com.andy.recruitment.biz.region.entity.AddressInfo;
import com.andy.recruitment.dao.region.entity.RegionDO;
import java.util.List;

/**
 * 地区服务
 *
 * @author 庞先海 2020-01-30
 */
public interface RegionService {

    /**
     * 通过id获取地区信息
     *
     * @param regionId 地区ID
     * @return 地区信息
     */
    RegionDO getRegionById(Long regionId);

    /**
     * 通过id获取地区名称
     *
     * @param regionId 地区ID
     * @return 地区名称
     */
    String getRegionNameById(Long regionId);

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


    /**
     * 将地区ID抓换成名称字符串
     *
     * @param provinceId 省ID
     * @param cityId     城市ID
     * @param districtId 地区ID
     * @return 名称字符串
     */
    String parseAddressName(Long provinceId, Long cityId, Long districtId);

    /**
     * 将 '四川 成都 武侯区'这种字符串解析成地址对象
     *
     * @param text 地区文本
     * @return 地址对象
     */
    AddressInfo parseAddressInfo(String text);
}
