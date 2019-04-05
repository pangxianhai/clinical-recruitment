package com.andy.recruitment.web.controller.user.webservice;

import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.user.request.WxLoginRQ;
import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.andy.recruitment.weixin.ao.WeiXinAO;
import com.xgimi.auth.LoginInfo;
import com.xgimi.context.ServletContext;
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

    private final WeiXinAO weiXinAO;

    @Autowired
    public LoginWebservice(UserAO userAO, WeiXinAO weiXinAO) {
        this.userAO = userAO;
        this.weiXinAO = weiXinAO;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public UserInfoVO getCurrentUserInfo() {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        if (null == loginInfo) {
            return null;
        }
        UserInfo userInfo = this.userAO.getUserInfoByUserId(loginInfo.getUserId());
        if (null == userInfo) {
            return null;
        }
        return UserUtil.transformUserInfoVO(userInfo);
    }

    @RequestMapping(value = "/wx", method = RequestMethod.GET)
    public String getWxLoginUrl(String redirectURL) {
        return weiXinAO.getWeiXinLoginUrl(redirectURL);
    }

    @RequestMapping(value = "/wx", method = RequestMethod.POST)
    public UserInfoVO wxLogin(@RequestBody WxLoginRQ wxLoginRQ) {
        UserInfo userInfo = this.userAO.loginByWeixin(wxLoginRQ.getCode());
        return UserUtil.transformUserInfoVO(userInfo);
    }

    //    @RequestMapping(value = "/sendVerCode.json", method = RequestMethod.POST)
    //    public boolean sendLoginVerCode(@RequestBody VerCodeSendRQ verCodeSendRQ) {
    //        //        this.userAO.sendLoginVerCode(verCodeSendRQ.getPhone());
    //        return true;
    //    }

    //    @RequestMapping(value = "/bandPhone.json", method = RequestMethod.POST)
    //    public boolean bandPhone(@RequestBody BandPhoneRQ bandPhoneRQ) {
    //        Long userId = ServletContext.getLoginInfo().getUserId();
    //        this.userAO.bandPhone(userId, bandPhoneRQ.getPhone(), bandPhoneRQ.getVerCode());
    //        return true;
    //    }
    //
    //    @RequestMapping(value = "/manage.json", method = RequestMethod.POST)
    //    public boolean manageLogin(@RequestBody MangeLoginRQ loginRQ) {
    //        UserInfo userInfo = this.userAO.loginByPhone(loginRQ.getPhone(), loginRQ.getPassword());
    //        if (null == userInfo) {
    //            return false;
    //        }
    //        Cookie cookie = new Cookie("userId", String.valueOf(userInfo.getUserId()));
    //        cookie.setMaxAge(Integer.MAX_VALUE);
    //        cookie.setPath("/");
    //        ServletContext.getResponse().addCookie(cookie);
    //        return true;
    //    }
}
