package com.andy.recruitment.web.controller.patient.webservice;


import com.xgimi.auth.Login;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 患者controller
 *
 * @author 庞先海 2019-01-22
 */
@Controller
@RequestMapping("/patient")
public class PatientController {

    @Login
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(String redirectURL, Integer userType, Map<String, Object> model) {
        model.put("redirectURL", redirectURL);
        model.put("userType", userType);
        return "patient/register";
    }
}
