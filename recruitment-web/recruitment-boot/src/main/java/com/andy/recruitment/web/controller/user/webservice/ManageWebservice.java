package com.andy.recruitment.web.controller.user.webservice;

import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.user.request.ManageAddRQ;
import com.andy.recruitment.web.controller.user.request.MangeLoginRQ;
import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.xgimi.auth.Login;
import com.xgimi.context.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Boolean manageAdd(@RequestBody ManageAddRQ addRQ) {
        UserInfo userInfo = UserUtil.transformUserInfo(addRQ);
        this.userAO.registerUser(userInfo, ServletContext.getLoginUname());
        return true;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserInfoVO manageLogin(@RequestBody MangeLoginRQ loginRQ) {
        UserInfo userInfo = this.userAO.loginByPhone(loginRQ.getPhone(), loginRQ.getPassword());
        if (null == userInfo) {
            return null;
        }
        return UserUtil.transformUserInfoVO(userInfo);
    }
}
