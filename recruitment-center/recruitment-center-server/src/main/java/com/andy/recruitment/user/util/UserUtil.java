package com.andy.recruitment.user.util;

import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.model.UserInfoDO;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.util.BeanUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户工具类
 *
 * @author 庞先海 2018-12-28
 */
public class UserUtil {

    public static UserInfo transformUserInfo(UserInfoDO userInfoDO) {
        if (null == userInfoDO) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(userInfoDO, userInfo);
        userInfo.setUserId(userInfoDO.getId());
        return userInfo;
    }

    public static List<UserInfo> transformUserInfo(List<UserInfoDO> userInfoDOList) {
        if (CollectionUtil.isEmpty(userInfoDOList)) {
            return null;
        }
        return userInfoDOList.stream().map(UserUtil::transformUserInfo).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static UserInfoDO transformUserInfoDO(UserInfo userInfo) {
        if (null == userInfo) {
            return null;
        }
        UserInfoDO userInfoDO = new UserInfoDO();
        BeanUtil.copyProperties(userInfo, userInfoDO);
        userInfoDO.setId(userInfo.getUserId());
        return userInfoDO;
    }
}
