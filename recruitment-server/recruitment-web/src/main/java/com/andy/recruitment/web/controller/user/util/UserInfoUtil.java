package com.andy.recruitment.web.controller.user.util;

import com.andy.recruitment.biz.user.service.UserService;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.web.controller.user.response.UserInfoRes;
import com.andy.recruitment.web.controller.user.response.WxLoginRes;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户工具类
 *
 * @author 庞先海 2020-02-02
 */
@Component
public class UserInfoUtil {

    private static UserService userService;

    @Autowired
    public UserInfoUtil(UserService userService) {
        UserInfoUtil.userService = userService;
    }

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

    public static Map<Long, UserInfoRes> getUserInfoRes(List<Long> userIdList) {
        List<UserInfoDO> userInfoDoList = userService.getUserInfo(userIdList);
        List<UserInfoRes> userInfoResList = UserInfoUtil.transformUserInfoRes(userInfoDoList);
        return userInfoResList.stream().collect(
            Collectors.toMap(UserInfoRes::getUserId, Function.identity(), (u1, u2) -> u1));
    }

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
