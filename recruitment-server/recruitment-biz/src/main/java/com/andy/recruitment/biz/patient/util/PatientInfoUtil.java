package com.andy.recruitment.biz.patient.util;

import com.andy.recruitment.api.patient.response.PatientInfoRes;
import com.andy.recruitment.biz.region.service.RegionService;
import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import org.springframework.beans.BeanUtils;

/**
 * 患者工具类
 *
 * @author 庞先海 2020-04-08
 */
public class PatientInfoUtil {

    public static PatientInfoRes transformReferenceRes(PatientInfoDO patientInfoDo, RegionService regionService) {
        if (patientInfoDo == null) {
            return null;
        }
        PatientInfoRes patientInfoRes = new PatientInfoRes();
        BeanUtils.copyProperties(patientInfoDo, patientInfoRes);
        patientInfoRes.setPatientId(patientInfoDo.getId());
        patientInfoRes.setAddress(
            regionService.parseAddressName(patientInfoDo.getProvinceId(), patientInfoDo.getCityId(),
                patientInfoDo.getDistrictId()));
        return patientInfoRes;
    }
}
