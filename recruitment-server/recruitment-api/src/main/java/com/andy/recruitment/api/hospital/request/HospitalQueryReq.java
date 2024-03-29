package com.andy.recruitment.api.hospital.request;

import com.andy.spring.base.BaseQueryReq;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 机构查询参数
 *
 * @author 庞先海 2020-01-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HospitalQueryReq extends BaseQueryReq {

    private static final long serialVersionUID = - 2883841511350932018L;

    /**
     * 机构名称模糊查询
     */
    @Length(max = 64)
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
