package com.andy.recruitment.web.controller;

import com.xgimi.converter.MyParameter;
import java.util.Date;
import java.util.Map;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 首页
 *
 * @author 庞先海 2018-10-17
 */
@Controller
public class IndexController {

    public static class UserInfo {

        @Length(max = 3)
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String test(Map<String, Object> model, @MyParameter UserInfo userInfo) {
        model.put("time", new Date());
        model.put("uNname", userInfo.getUserName());
        return "test";
    }
}