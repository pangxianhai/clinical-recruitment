package com.andy.recruitment.dao.patient.entity;

import com.andy.recruitment.common.patient.constant.PatientStatus;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 患者查询信息
 *
 * @author 庞先海 2018-12-27
 */
@Data
public class PatientQuery implements Serializable {

    private static final long serialVersionUID = - 3410326219398167056L;
    /**
     * 患者ID
     */
    private Long patientId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户ID列表
     */
    private List<Long> userIdList;
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
    /**
     * 患者状态
     */
    private PatientStatus status;
}
