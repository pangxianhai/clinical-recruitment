package com.andy.recruitment.web.controller.user.webservice;

import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.user.request.WxLoginRQ;
import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.andy.recruitment.weixin.ao.WeiXinAO;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.context.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户接口
 *
 * @author 庞先海 2019-03-18
 */
@RestController
@RequestMapping("/user")
public class UserWebservice {

    private final UserAO userAO;

    private final WeiXinAO weiXinAO;

    @Autowired
    public UserWebservice(UserAO userAO, WeiXinAO weiXinAO) {
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
        return UserUtil.transformUserInfoVO(userInfo);
    }

    @Login
    @RequestMapping(value = "/{userId:\\d+}/freeze.json", method = RequestMethod.POST)
    public Boolean manageFreeze(@PathVariable Long userId) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        this.userAO.updateUserStatus(userId, UserStatus.FREEZE, loginInfo.getRealName());
        return true;
    }

    @Login
    @RequestMapping(value = "/{userId:\\d+}/unfreeze.json", method = RequestMethod.POST)
    public Boolean manageUnfreeze(@PathVariable Long userId) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        this.userAO.updateUserStatus(userId, UserStatus.NORMAL, loginInfo.getRealName());
        return true;
    }

    //医生登陆页面 http://www.aiteruiyiyao.cn/user/login?userType=2
    //患者登陆页面 http://www.aiteruiyiyao.cn/user/login?userType=3
    //医生登陆页面 http://www.andy.com/user/login?userType=2
    //患者登陆页面 http://www.andy.com/user/login?userType=3
    @RequestMapping(value = "/login/wx", method = RequestMethod.GET)
    public String getWxLoginUrl(String redirectURL) {
        return weiXinAO.getWeiXinLoginUrl(redirectURL);
    }

    @RequestMapping(value = "/login/wx", method = RequestMethod.POST)
    public UserInfoVO wxLogin(@RequestBody WxLoginRQ wxLoginRQ) {
        UserInfo userInfo = this.userAO.loginByWeixin(wxLoginRQ.getCode());
        return UserUtil.transformUserInfoVO(userInfo);
    }
}
