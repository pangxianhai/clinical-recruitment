package com.andy.recruitment.web.controller.user.webservice;

import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.user.request.ManageAddRQ;
import com.andy.recruitment.web.controller.user.util.UserUtil;
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
 * 管理員接口
 *
 * @author 庞先海 2019-03-03
 */
@RestController
@RequestMapping("/user/manager")
public class ManageWebservice {

    private final UserAO userAO;

    @Autowired
    public ManageWebservice(UserAO userAO) {
        this.userAO = userAO;
    }

    @Login
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public Boolean manageAdd(@RequestBody ManageAddRQ addRQ) {
        UserInfo userInfo = UserUtil.transformUserInfo(addRQ);
        this.userAO.addUserInfo(userInfo, ServletContext.getLoginUname());
        return true;
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
}
