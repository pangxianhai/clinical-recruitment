package com.andy.recruitment.web.controller.user.webservice;

import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.weixin.ao.WeiXinAO;
import com.xgimi.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 登陆controller
 *
 * @author 庞先海 2019-01-13
 */
@Controller
@RequestMapping("/user/login")
public class LoginController {

    @Value("${recruitment.address}")
    private String serverAddress;

    private final WeiXinAO weiXinAO;

    private final UserAO userAO;

    @Autowired
    public LoginController(WeiXinAO weiXinAO, UserAO userAO) {
        this.weiXinAO = weiXinAO;
        this.userAO = userAO;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String login() {
        return "redirect:" + weiXinAO.getWeiXinLoginUrl(serverAddress + "/user/login/weixinlogin");
    }

    @RequestMapping(value = "/weixinlogin", method = RequestMethod.GET)
    public String weixinRespons(String code) {
        UserInfo userInfo = this.userAO.loginByWeixin(code);
        if (StringUtil.isNotEmpty(userInfo.getPhone())) {
            return "redirect:/";
        } else {
            return "redirect:/user/login/bandPhone";
        }
    }


    @RequestMapping(value = "/bandPhone", method = RequestMethod.GET)
    public String bandPhone() {
        return "user/bandPhone";
    }
}
