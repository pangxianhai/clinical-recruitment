package com.andy.recruitment.web.controller.user.webservice;

import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.weixin.ao.WeiXinAO;
import com.xgimi.commons.util.StringUtil;
import com.xgimi.context.ServletContext;
import java.util.Map;
import javax.servlet.http.Cookie;
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
    public String login(String redirectURL, Integer userType) {
        if (StringUtil.isEmpty(redirectURL) || redirectURL.contains("/user/login")) {
            redirectURL = "/";
        }
        if (null == userType) {
            userType = UserType.PATIENT.getCode();
        }
        String url = serverAddress + "/user/login/weixinlogin?redirectURL=" + redirectURL;
        url += "&userType=" + userType;
        return "redirect:" + weiXinAO.getWeiXinLoginUrl(url);
    }

    @RequestMapping(value = "/weixinlogin", method = RequestMethod.GET)
    public String weixinResponse(String code, String redirectURL, Integer userType) {
        UserInfo userInfo = this.userAO.loginByWeixin(code);
        ServletContext.getResponse().addCookie(new Cookie("userId", String.valueOf(userInfo.getUserId())));
        if (StringUtil.isNotEmpty(userInfo.getPhone())) {
            return "redirect:" + redirectURL;
        } else {
            return "redirect:/user/login/bandPhone?redirectURL=" + redirectURL + "&userType=" + userType;
        }
    }

    @RequestMapping(value = "/bandPhone", method = RequestMethod.GET)
    public String bandPhone(String redirectURL, Integer userType, Map<String, Object> model) {
        model.put("redirectURL", redirectURL);
        model.put("userType", userType);
        return "user/bandPhone";
    }


}
