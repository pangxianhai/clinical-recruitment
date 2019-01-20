package com.andy.recruitment.web.controller.doctor.webservice;

import com.andy.recruitment.doctor.ao.DoctorAO;
import com.andy.recruitment.doctor.model.DoctorInfo;
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

    @Autowired
    public DoctorWebservice(DoctorAO doctorAO) {
        this.doctorAO = doctorAO;
    }

    @Login
    @RequestMapping(value = "/register.json", method = RequestMethod.POST)
    public boolean register(@RequestBody DoctorAddRQ doctorAddRQ) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        DoctorInfo doctorInfo = DoctorUtil.transformDoctorInfo(doctorAddRQ);
        doctorInfo.setUserId(loginInfo.getUserId());
        this.doctorAO.addDoctorInfo(doctorInfo, ServletContext.getLoginUname());
        return true;
    }

}
