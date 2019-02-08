package com.andy.recruitment.web.controller.user.util;

import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import com.xgimi.util.BeanUtil;

/**
 * 用户工具
 *
 * @author 庞先海 2019-02-08
 */
public class UserUtil {

    public static UserInfoVO transformUserInfoVO(UserInfo userInfo) {
        if (null == userInfo) {
            return null;
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtil.copyProperties(userInfo, userInfoVO);
        return userInfoVO;
    }
}
