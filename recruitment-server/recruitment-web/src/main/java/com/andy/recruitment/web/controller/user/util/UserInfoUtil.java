package com.andy.recruitment.web.controller.user.util;

import com.andy.recruitment.api.user.response.WxLoginRes;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * 用户工具类
 *
 * @author 庞先海 2020-02-02
 */
@Component
public class UserInfoUtil {

    public static WxLoginRes transformWxLoginRes(UserInfoDO userInfoDo) {
        if (userInfoDo == null) {
            return null;
        }
        WxLoginRes userInfoRes = new WxLoginRes();
        BeanUtils.copyProperties(userInfoDo, userInfoRes);
        userInfoRes.setUserId(userInfoDo.getId());
        return userInfoRes;
    }
}
