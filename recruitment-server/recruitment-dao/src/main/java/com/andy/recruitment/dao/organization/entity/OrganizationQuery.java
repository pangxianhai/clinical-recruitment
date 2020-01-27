package com.andy.recruitment.dao.organization.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 机构信息查询参数
 *
 * @author 庞先海 2020-01-27
 */
@Data
public class OrganizationQuery implements Serializable {

    private static final long serialVersionUID = - 5991416483672186462L;

    /**
     * 机构名称模糊查询
     */
    private String nameLike;

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
