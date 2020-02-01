package com.andy.recruitment.biz.recruitment.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 研究机构科室信息BO
 *
 * @author 庞先海 2020-02-01
 */
@Data
public class DepartmentInfoBO implements Serializable {

    private static final long serialVersionUID = - 56228159141663335L;

    /**
     * 机构ID
     */
    private Long organizationId;
    /**
     * 机构地址
     */
    private String organizationAddress;
    /**
     * 机构名称
     */
    private String organizationName;
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

    /**
     * 科室ID
     */
    private Long departmentId;
    /**
     * 科室名称
     */
    private String departmentName;
}
