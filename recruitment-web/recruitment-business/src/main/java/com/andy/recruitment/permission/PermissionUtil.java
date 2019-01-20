package com.andy.recruitment.permission;

import com.andy.recruitment.user.model.UserInfo;
import com.xgimi.auth.LoginInfo;
import com.xgimi.util.BeanUtil;

/**
 * 权限工具类
 *
 * @author 庞先海 2019-01-17
 */
public class PermissionUtil {

    public static LoginInfo transformLoginInfo(UserInfo userInfo) {
        if (null == userInfo) {
            return null;
        }
        LoginInfo loginInfo = new LoginInfo();
        BeanUtil.copyProperties(userInfo, loginInfo);
        loginInfo.setPhoneNumber(userInfo.getPhone());
        return loginInfo;
    }
}
