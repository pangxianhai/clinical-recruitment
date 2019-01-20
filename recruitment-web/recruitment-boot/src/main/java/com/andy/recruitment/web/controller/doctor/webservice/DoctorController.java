package com.andy.recruitment.web.controller.doctor.webservice;

import com.xgimi.auth.Login;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 医生controller
 *
 * @author 庞先海 2019-01-20
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Login
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(String redirectURL, Integer userType, Map<String, Object> model) {
        model.put("redirectURL", redirectURL);
        model.put("userType", userType);
        return "doctor/register";
    }

}
