package com.andy.recruitment.dao.patient.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 患者查询信息
 *
 * @author 庞先海 2018-12-27
 */
@Data
public class PatientQuery implements Serializable {

    private static final long serialVersionUID = - 9159406254116615754L;

    /**
     * 患者ID
     */
    private Long patientId;
    /**
     * 用户ID
     */
    private Long userId;
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
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号码模糊查询
     */
    private String phoneLike;
}
