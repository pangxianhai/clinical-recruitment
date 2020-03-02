package com.andy.recruitment.dao.region.entity;

import com.andy.recruitment.dao.region.constant.RegionStatus;
import com.andy.recruitment.dao.region.constant.RegionType;
import com.andy.spring.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 地区信息DO
 *
 * @author 庞先海 2018-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RegionDO extends BaseDO {

    private static final long serialVersionUID = - 5580323914952030902L;
    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 地区名字
     */
    private String regionName;

    /**
     * 地区类型
     */
    private RegionType regionType;

    /**
     * 地区状态
     */
    private RegionStatus regionStatus;

}
