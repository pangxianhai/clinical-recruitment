package com.andy.recruitment.patient.util;

import com.andy.recruitment.patient.model.PatientInfo;
import com.andy.recruitment.patient.model.PatientInfoDO;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.util.BeanUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 患者工具类
 *
 * @author 庞先海 2018-12-27
 */
public class PatientUtil {

    public static PatientInfo transformPatientInfo(PatientInfoDO patientInfoDO) {
        if (null == patientInfoDO) {
            return null;
        }
        PatientInfo patientInfo = new PatientInfo();
        BeanUtil.copyProperties(patientInfoDO, patientInfo);
        patientInfo.setPatientId(patientInfoDO.getId());
        return patientInfo;
    }

    public static List<PatientInfo> transformPatientInfo(List<PatientInfoDO> patientInfoDOList) {
        if (CollectionUtil.isEmpty(patientInfoDOList)) {
            return null;
        }
        return patientInfoDOList.stream().map(PatientUtil::transformPatientInfo).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static PatientInfoDO transformPatientInfoDO(PatientInfo patientInfo) {
        if (null == patientInfo) {
            return null;
        }
        PatientInfoDO patientInfoDO = new PatientInfoDO();
        BeanUtil.copyProperties(patientInfo, patientInfoDO);
        patientInfoDO.setId(patientInfo.getPatientId());
        return patientInfoDO;
    }
}
