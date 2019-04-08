package com.andy.recruitment.web.controller;

import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.xgimi.util.ServletUtil;
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
        if (ServletUtil.isMobile()) {
            return "redirect:/recruitment/list";
        } else {
            return "redirect:/recruitment/list-pc";
        }
    }
}