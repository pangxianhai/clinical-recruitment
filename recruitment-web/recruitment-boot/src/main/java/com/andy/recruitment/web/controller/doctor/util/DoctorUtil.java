package com.andy.recruitment.web.controller.doctor.util;

import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.web.controller.doctor.request.DoctorAddRQ;
import com.xgimi.util.BeanUtil;

/**
 * 医生工具类
 *
 * @author 庞先海 2019-01-20
 */
public class DoctorUtil {

    public static DoctorInfo transformDoctorInfo(DoctorAddRQ doctorAddRQ) {
        if (null == doctorAddRQ) {
            return null;
        }
        DoctorInfo doctorInfo = new DoctorInfo();
        BeanUtil.copyProperties(doctorAddRQ, doctorInfo);
        return doctorInfo;
    }
}
