package com.andy.recruitment.biz.region.entity;

import com.andy.recruitment.dao.region.entity.RegionDO;
import java.io.Serializable;
import lombok.Data;

/**
 * 功能描述
 *
 * @author 庞先海 2020-02-23
 */
@Data
public class AddressInfo implements Serializable {

    private static final long serialVersionUID = 4112640884533532443L;
    /**
     * 省
     */
    private RegionDO province;
    /**
     * 市
     */
    private RegionDO city;
    /**
     * 区
     */
    private RegionDO district;
}
