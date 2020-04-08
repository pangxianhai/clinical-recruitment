package com.andy.recruitment.web.controller.hospital.util;

import com.andy.recruitment.api.hospital.request.HospitalAddReq;
import com.andy.recruitment.api.hospital.request.HospitalQueryReq;
import com.andy.recruitment.dao.hospital.entity.HospitalDO;
import com.andy.recruitment.dao.hospital.entity.HospitalQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * 机构工具
 *
 * @author 庞先海 2020-01-30
 */
@Component
public class HospitalUtil {

    public static HospitalQuery transformHospitalQuery(HospitalQueryReq queryReq) {
        if (queryReq == null) {
            return null;
        }
        HospitalQuery hospitalQuery = new HospitalQuery();
        BeanUtils.copyProperties(queryReq, hospitalQuery);
        return hospitalQuery;
    }

    public static HospitalDO transformHospitalDo(HospitalAddReq addReq) {
        if (addReq == null) {
            return null;
        }
        HospitalDO hospitalDo = new HospitalDO();
        BeanUtils.copyProperties(addReq, hospitalDo);
        return hospitalDo;
    }
}
