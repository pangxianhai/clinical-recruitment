package com.andy.recruitment.permission;

import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.service.UserInfoService;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.auth.Permission;
import com.xgimi.auth.PermissionService;
import com.xgimi.commons.util.NumberUtil;
import com.xgimi.commons.util.StringUtil;
import com.xgimi.context.ServletContext;
import com.xgimi.util.CookieUtil;
import javax.servlet.http.HttpServletRequest;
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
        if (StringUtil.isEmpty(token)) {
            token = CookieUtil.getCookieValue("userId");
        }
        Long userId = NumberUtil.parseLong(token);
        UserInfo userInfo = this.userInfoService.getUserInfoByUserId(userId);
        HttpServletRequest request = ServletContext.getRequest();
        if (null != request) {
            request.setAttribute("userInfo", userInfo);
        }
        return PermissionUtil.transformLoginInfo(userInfo);
    }

    @Override
    public boolean hasPrivilege(String appId, Permission permission, Login login) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        if (null != login) {
            if (null == loginInfo) {
                throw new BusinessException(BusinessErrorCode.LOGIN_NOT_LOGIN);
            }
            UserInfo userInfo = (UserInfo)ServletContext.getRequest().getAttribute("userInfo");
            if (null == userInfo) {
                throw new BusinessException(BusinessErrorCode.LOGIN_NOT_LOGIN);
            }
            if (UserStatus.FREEZE.equals(userInfo.getStatus())) {
                throw new BusinessException(BusinessErrorCode.USER_FREEZE);
            }
        }
        return true;
    }
}
