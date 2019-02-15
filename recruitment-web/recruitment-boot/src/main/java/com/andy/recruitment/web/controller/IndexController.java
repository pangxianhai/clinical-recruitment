package com.andy.recruitment.web.controller;

import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页
 *
 * @author 庞先海 2018-10-17
 */
@Controller
public class IndexController {

    private final UserAO userAO;

    @Autowired
    public IndexController(UserAO userAO) {
        this.userAO = userAO;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String homePage() {
        return "redirect:/recruitment/list";
    }

    @ResponseBody
    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public String initUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone("17780583960");
        userInfo.setPassword("123456");
        userInfo.setUserType(UserType.ADMIN);
        userInfo.setRealName("庞先海");
        userInfo.setGender(Gender.MALE);
        this.userAO.addUserInfo(userInfo, "系统");
        return "True";
    }
}