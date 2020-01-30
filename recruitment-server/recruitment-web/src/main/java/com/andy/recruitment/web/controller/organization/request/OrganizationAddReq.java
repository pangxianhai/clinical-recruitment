package com.andy.recruitment.web.controller.organization.request;

import java.io.Serializable;
import lombok.Data;

/**
 * 机构添加参数
 *
 * @author 庞先海 2020-01-30
 */
@Data
public class OrganizationAddReq implements Serializable {

    private static final long serialVersionUID = - 7213798303465383762L;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 省ID
     */
    private Long provinceId;

    /**
     * 市ID
     */
    private Long cityId;

    /**
     * 区ID
     */
    private Long districtId;
}
