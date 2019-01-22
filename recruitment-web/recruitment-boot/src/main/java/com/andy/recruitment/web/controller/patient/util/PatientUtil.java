package com.andy.recruitment.web.controller.patient.util;

import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.web.controller.patient.request.PatientAddRQ;
import com.xgimi.util.BeanUtil;

/**
 * 患者工具类
 *
 * @author 庞先海 2019-01-22
 */
public class PatientUtil {

    public static PatientInfo transformPatientInfo(PatientAddRQ patientAddRQ) {
        if (null == patientAddRQ) {
            return null;
        }
        PatientInfo patientInfo = new PatientInfo();
        BeanUtil.copyProperties(patientAddRQ, patientInfo);
        return patientInfo;
    }
}
