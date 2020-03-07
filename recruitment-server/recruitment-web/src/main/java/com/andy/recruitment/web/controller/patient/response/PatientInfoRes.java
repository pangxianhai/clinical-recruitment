package com.andy.recruitment.web.controller.patient.response;

import com.andy.recruitment.common.patient.constant.PatientStatus;
import java.io.Serializable;
import lombok.Data;

/**
 * 患者信息
 *
 * @author 庞先海 2020-02-16
 */
@Data
public class PatientInfoRes implements Serializable {

    private static final long serialVersionUID = 6944867281048775588L;

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
     * 推荐人所在地址
     */
    private String address;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 患者状态
     */
    private PatientStatus status;
}
