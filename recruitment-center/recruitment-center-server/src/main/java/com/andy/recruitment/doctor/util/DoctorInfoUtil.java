package com.andy.recruitment.doctor.util;

import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorInfoDO;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.util.BeanUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 医生信息工具
 *
 * @author 庞先海 2018-12-27
 */
public class DoctorInfoUtil {

    public static DoctorInfo transformDoctorInfo(DoctorInfoDO doctorInfoDO) {
        if (null == doctorInfoDO) {
            return null;
        }
        DoctorInfo doctorInfo = new DoctorInfo();
        BeanUtil.copyProperties(doctorInfoDO, doctorInfo);
        doctorInfo.setDoctorId(doctorInfoDO.getId());
        return doctorInfo;
    }

    public static List<DoctorInfo> transformDoctorInfo(List<DoctorInfoDO> doctorInfoDOList) {
        if (CollectionUtil.isEmpty(doctorInfoDOList)) {
            return null;
        }
        return doctorInfoDOList.stream().map(DoctorInfoUtil::transformDoctorInfo).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static DoctorInfoDO transformDoctorInfoDO(DoctorInfo doctorInfo) {
        if (null == doctorInfo) {
            return null;
        }
        DoctorInfoDO doctorInfoDO = new DoctorInfoDO();
        BeanUtil.copyProperties(doctorInfo, doctorInfoDO);
        doctorInfoDO.setId(doctorInfo.getDoctorId());
        return doctorInfoDO;
    }
}
