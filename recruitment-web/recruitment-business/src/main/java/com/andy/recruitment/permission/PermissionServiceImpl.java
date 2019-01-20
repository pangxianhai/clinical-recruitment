package com.andy.recruitment.permission;

import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.service.UserInfoService;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.auth.Permission;
import com.xgimi.auth.PermissionService;
import com.xgimi.commons.util.NumberUtil;
import com.xgimi.context.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 权限实现
 *
 * @author 庞先海 2019-01-17
 */
@Component
public class PermissionServiceImpl implements PermissionService {

    private final UserInfoService userInfoService;

    @Autowired
    public PermissionServiceImpl(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public LoginInfo getLoginInfo(String appId, String token) {
        Long userId = NumberUtil.parseLong(token);
        UserInfo userInfo = this.userInfoService.getUserInfoByUserId(userId);
        return PermissionUtil.transformLoginInfo(userInfo);
    }

    @Override
    public boolean hasPrivilege(String appId, Permission permission, Login login) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        if (null != login && null == loginInfo) {
            throw new BusinessException(BusinessErrorCode.LOGIN_NOT_LOGIN);
        }
        return true;
    }
}
