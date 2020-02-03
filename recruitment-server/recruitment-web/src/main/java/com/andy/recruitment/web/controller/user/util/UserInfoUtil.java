package com.andy.recruitment.web.controller.user.util;

import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

/**
 * 用户工具类
 *
 * @author 庞先海 2020-02-02
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
            return new ArrayList<>(0);
        }
        return userInfoDoList.stream().map(UserInfoUtil::transformUserInfoRes).filter(Objects::nonNull).collect(
            Collectors.toList());
    }
}
