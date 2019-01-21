package com.andy.recruitment.web.controller.doctor.webservice;

import com.andy.recruitment.doctor.ao.DoctorAO;
import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.region.model.AddressInfo;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.web.controller.doctor.request.DoctorAddRQ;
import com.andy.recruitment.web.controller.doctor.util.DoctorUtil;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.context.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 医生webservice接口
 *
 * @author 庞先海 2019-01-20
 */
@RestController
@RequestMapping("/doctor")
public class DoctorWebservice {

    private final DoctorAO doctorAO;

    private final RegionAO regionAO;

    private final UserAO userAO;

    @Autowired
    public DoctorWebservice(DoctorAO doctorAO, RegionAO regionAO, UserAO userAO) {
        this.doctorAO = doctorAO;
        this.regionAO = regionAO;
        this.userAO = userAO;
    }

    @Login
    @RequestMapping(value = "/register.json", method = RequestMethod.POST)
    public boolean register(@RequestBody DoctorAddRQ doctorAddRQ) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        DoctorInfo doctorInfo = DoctorUtil.transformDoctorInfo(doctorAddRQ);
        doctorInfo.setUserId(loginInfo.getUserId());
        AddressInfo addressInfo = regionAO.parseAddressInfo(doctorAddRQ.getAddress());
        if (null != addressInfo.getProvince()) {
            doctorInfo.setProvinceId(addressInfo.getProvince().getRegionId());
        }
        if (null != addressInfo.getCity()) {
            doctorInfo.setCityId(addressInfo.getCity().getRegionId());
        }
        if (null != addressInfo.getDistrict()) {
            doctorInfo.setDistrictId(addressInfo.getDistrict().getRegionId());
        }
        this.doctorAO.addDoctorInfo(doctorInfo, ServletContext.getLoginUname());
        this.userAO.bandPhone(loginInfo.getUserId(), doctorAddRQ.getPhone(), null, UserType.DOCTOR);
        return true;
    }

}
