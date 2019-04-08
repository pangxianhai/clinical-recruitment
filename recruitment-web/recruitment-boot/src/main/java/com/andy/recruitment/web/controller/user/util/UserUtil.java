package com.andy.recruitment.web.controller.user.util;

import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.model.UserQueryParam;
import com.andy.recruitment.web.controller.user.request.ManageAddRQ;
import com.andy.recruitment.web.controller.user.request.UserQueryRQ;
import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import com.xgimi.auth.LoginInfo;
import com.xgimi.commons.util.CollectionUtil;
import com.xgimi.context.ServletContext;
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

    public static UserInfo transformUserInfo(ManageAddRQ manageAddRQ) {
        if (null == manageAddRQ) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtil.copyProperties(manageAddRQ, userInfo);
        userInfo.setGender(Gender.parse(manageAddRQ.getGender()));
        userInfo.setUserType(UserType.ADMIN);
        return userInfo;
    }

    public static String getOperator(String defaultOperator) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        return null == loginInfo ? defaultOperator : loginInfo.getRealName();
    }
}
