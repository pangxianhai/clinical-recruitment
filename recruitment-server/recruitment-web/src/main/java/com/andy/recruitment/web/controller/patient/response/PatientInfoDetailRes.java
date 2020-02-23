package com.andy.recruitment.web.controller.patient.response;

import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 患者详情
 *
 * @author 庞先海 2020-02-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PatientInfoDetailRes extends PatientInfoRes {

    private static final long serialVersionUID = 1450339553508440119L;
    /**
     * 用户信息
     */
    private UserInfoRes userInfoRes;
}
