package com.andy.recruitment.web.controller.user.webservice;

import com.andy.recruitment.weixin.ao.WeiXinAO;
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

    @Value("${server.address}")
    private String serverAddress;

    private final WeiXinAO weiXinAO;

    @Autowired
    public LoginController(WeiXinAO weiXinAO) {
        this.weiXinAO = weiXinAO;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String login() {
        return "redirect:" + weiXinAO.getWeiXinLoginUrl(serverAddress + "/user/login/weixin_response");
    }

    @RequestMapping(value = "/weixin_response", method = RequestMethod.GET)
    public String weixinRespons(String code) {

    }
}
