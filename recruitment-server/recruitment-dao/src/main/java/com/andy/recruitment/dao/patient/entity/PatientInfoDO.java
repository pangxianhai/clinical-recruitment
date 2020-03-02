package com.andy.recruitment.dao.patient.entity;

import com.andy.recruitment.dao.patient.constant.PatientStatus;
import com.andy.spring.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 患者信息DO
 *
 * @author 庞先海 2018-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PatientInfoDO extends BaseDO {

    private static final long serialVersionUID = - 8073736282151477798L;
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
     * 年龄
     */
    private Integer age;
    /**
     * 患者状态
     */
    private PatientStatus status;
}
