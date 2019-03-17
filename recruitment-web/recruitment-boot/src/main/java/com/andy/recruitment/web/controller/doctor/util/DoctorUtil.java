package com.andy.recruitment.web.controller.doctor.util;

import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.web.controller.doctor.request.DoctorAddRQ;
import com.andy.recruitment.web.controller.doctor.request.DoctorQueryRQ;
import com.andy.recruitment.web.controller.doctor.response.DoctorInfoVO;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.util.BeanUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public static DoctorInfoVO transformDoctorInfoVO(DoctorInfo doctorInfo) {
        if (null == doctorInfo) {
            return null;
        }
        DoctorInfoVO doctorInfoVO = new DoctorInfoVO();
        BeanUtil.copyProperties(doctorInfo, doctorInfoVO);
        return doctorInfoVO;
    }

    public static List<DoctorInfoVO> transformDoctorInfoVO(List<DoctorInfo> doctorInfoList) {
        if (CollectionUtil.isEmpty(doctorInfoList)) {
            return new ArrayList<>(0);
        }
        return doctorInfoList.stream().map(DoctorUtil::transformDoctorInfoVO).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static DoctorQueryParam transformDoctorQueryParam(DoctorQueryRQ queryRQ) {
        if (null == queryRQ) {
            return null;
        }
        DoctorQueryParam queryParam = new DoctorQueryParam();
        BeanUtil.copyProperties(queryRQ, queryParam);
        queryParam.setStatus(UserStatus.parse(queryRQ.getStatus()));
        return queryParam;
    }
}
