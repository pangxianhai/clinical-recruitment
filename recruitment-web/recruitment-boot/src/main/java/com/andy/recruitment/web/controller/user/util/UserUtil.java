package com.andy.recruitment.web.controller.user.util;

import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.model.UserQueryParam;
import com.andy.recruitment.web.controller.user.request.UserQueryRQ;
import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.util.BeanUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public static List<UserInfoVO> transformUserInfoVO(List<UserInfo> userInfoList) {
        if (CollectionUtil.isEmpty(userInfoList)) {
            return new ArrayList<>(0);
        }
        return userInfoList.stream().map(UserUtil::transformUserInfoVO).filter(Objects::nonNull).collect(
            Collectors.toList());
    }

    public static UserQueryParam transformUserQueryParam(UserQueryRQ queryRQ) {
        if (null == queryRQ) {
            return null;
        }
        UserQueryParam queryParam = new UserQueryParam();
        BeanUtil.copyProperties(queryRQ, queryParam);
        queryParam.setStatus(UserStatus.parse(queryRQ.getStatus()));
        return queryParam;
    }
}
