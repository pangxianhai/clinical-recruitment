package com.andy.recruitment.web.controller.user.webservice;

import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.weixin.ao.WeiXinAO;
import com.xgimi.auth.Login;
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

    //医生登陆页面 http://www.aiteruiyiyao.cn/user/login?userType=2
    //患者登陆页面 http://www.aiteruiyiyao.cn/user/login?userType=3
    //管理员登陆页面 http://www.aiteruiyiyao.cn/user/login?userType=1
    //医生登陆页面 http://www.andy.com/user/login?userType=2
    //患者登陆页面 http://www.andy.com/user/login?userType=3
    //管理员登陆页面 http://www.andy.com/user/login?userType=1
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
        Cookie cookie = new Cookie("userId", String.valueOf(userInfo.getUserId()));
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath("/");
        ServletContext.getResponse().addCookie(cookie);
        if (StringUtil.isNotEmpty(userInfo.getPhone())) {
            return "redirect:" + redirectURL;
        } else {
            UserType uType = UserType.parse(userType);
            String url = "/user/login/bandPhone";
            if (UserType.DOCTOR.equals(uType)) {
                url = "/doctor/register";
            } else if (UserType.PATIENT.equals(uType)) {
                url = "/patient/register";
            }
            return "redirect:" + url + "?redirectURL=" + redirectURL + "&userType=" + userType;
        }
    }

    @Login
    @RequestMapping(value = "/bandPhone", method = RequestMethod.GET)
    public String bandPhone(String redirectURL, Integer userType, Map<String, Object> model) {
        model.put("redirectURL", redirectURL);
        model.put("userType", userType);
        return "user/bandPhone";
    }
}
