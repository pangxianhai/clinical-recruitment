package com.andy.recruitment.web.controller.user.webservice;

import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.user.request.BandPhoneRQ;
import com.andy.recruitment.web.controller.user.request.MangeLoginRQ;
import com.andy.recruitment.web.controller.user.request.VerCodeSendRQ;
import com.xgimi.context.ServletContext;
import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆接口
 *
 * @author 庞先海 2019-01-17
 */
@RestController
@RequestMapping("/user/login")
public class LoginWebservice {

    private final UserAO userAO;

    @Autowired
    public LoginWebservice(UserAO userAO) {
        this.userAO = userAO;
    }

    @RequestMapping(value = "/sendVerCode.json", method = RequestMethod.POST)
    public boolean sendLoginVerCode(@RequestBody VerCodeSendRQ verCodeSendRQ) {
        //        this.userAO.sendLoginVerCode(verCodeSendRQ.getPhone());
        return true;
    }

    @RequestMapping(value = "/bandPhone.json", method = RequestMethod.POST)
    public boolean bandPhone(@RequestBody BandPhoneRQ bandPhoneRQ) {
        Long userId = ServletContext.getLoginInfo().getUserId();
        this.userAO.bandPhone(userId, bandPhoneRQ.getPhone(), bandPhoneRQ.getVerCode());
        return true;
    }

    @RequestMapping(value = "/manage.json", method = RequestMethod.POST)
    public boolean manageLogin(@RequestBody MangeLoginRQ loginRQ) {
        UserInfo userInfo = this.userAO.loginByPhone(loginRQ.getPhone(), loginRQ.getPassword());
        if (null == userInfo) {
            return false;
        }
        Cookie cookie = new Cookie("userId", String.valueOf(userInfo.getUserId()));
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setPath("/");
        ServletContext.getResponse().addCookie(cookie);
        return true;
    }
}
