package com.andy.recruitment.biz.user.util;

import com.andy.recruitment.api.user.response.UserInfoRes;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * 用户信息工具
 *
 * @author 庞先海 2020-03-07
 */
public class UserInfoUtil {

    public static UserInfoRes transformUserInfoRes(UserInfoDO userInfoDo) {
        if (userInfoDo == null) {
            return null;
        }
        UserInfoRes userInfoRes = new UserInfoRes();
        BeanUtils.copyProperties(userInfoDo, userInfoRes);
        userInfoRes.setUserId(userInfoDo.getId());
        return userInfoRes;
    }

    public static List<UserInfoRes> transformUserInfoRes(List<UserInfoDO> userInfoDoList) {
        if (CollectionUtils.isEmpty(userInfoDoList)) {
            return Collections.emptyList();
        }
        return userInfoDoList.stream().map(UserInfoUtil::transformUserInfoRes).filter(Objects::nonNull).collect(
            Collectors.toList());
    }
}
