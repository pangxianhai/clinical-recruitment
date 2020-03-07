package com.andy.recruitment.api.organization.response;

import java.io.Serializable;
import lombok.Data;

/**
 * 机构信息
 *
 * @author 庞先海 2020-01-30
 */
@Data
public class OrganizationRes implements Serializable {

    private static final long serialVersionUID = - 3426651232075244635L;

    /**
     * 机构ID
     */
    private Long organizationId;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 省名
     */
    private String provinceName;

    /**
     * 市名
     */
    private String cityName;

    /**
     * 区名
     */
    private String districtName;

    /**
     * 地址
     */
    private String address;
}
