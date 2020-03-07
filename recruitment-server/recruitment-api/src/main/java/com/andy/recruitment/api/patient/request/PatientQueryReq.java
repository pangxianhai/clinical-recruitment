package com.andy.recruitment.api.patient.request;

import com.andy.spring.base.BaseQueryReq;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 患者插叙参数
 *
 * @author 庞先海 2020-02-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PatientQueryReq extends BaseQueryReq implements Serializable {

    private static final long serialVersionUID = 7089091334317288178L;
    /**
     * 真实姓名
     */
    @Length(max = 64)
    private String realName;
    /**
     * 手机号码模糊查询
     */
    @Length(max = 32)
    private String phoneLike;
    /**
     * 患者状态
     */
    private Integer status;
    /**
     * 省ID
     */
    private Long provinceId;
    /**
     * 城市ID
     */
    private Long cityId;
    /**
     * 区ID
     */
    private Long districtId;
}
