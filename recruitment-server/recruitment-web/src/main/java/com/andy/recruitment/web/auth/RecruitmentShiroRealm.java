package com.andy.recruitment.web.auth;

import com.andy.recruitment.common.exception.RecruitmentErrorCode;
import com.andy.recruitment.common.exception.RecruitmentException;
import com.andy.spring.auth.LoginInfo;
import com.andy.spring.constant.Constant;
import com.andy.spring.context.ServletContext;
import com.andy.spring.util.JsonUtil;
import com.andy.spring.util.encrypt.Rc4Util;
import java.util.Base64;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 用户认证
 *
 * @author 庞先海 2020-01-28
 */
public class RecruitmentShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(String.valueOf(loginInfo.getRoleType().getCode()));
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        String userName = (String)authToken.getPrincipal();
        String token = new String((char[])authToken.getCredentials());
        LoginInfo loginInfo = this.parseToken(userName, token);
        ServletContext.setLoginInfo(loginInfo);
        return new SimpleAuthenticationInfo(userName, token, getName());
    }


    private LoginInfo parseToken(String userName, String token) {
        try {
            byte[] encodeByte = Base64.getUrlDecoder().decode(token);
            String sourceStr = new String(Rc4Util.rc4(encodeByte, userName), Constant.DEFAULT_CHARSET);
            LoginInfo loginInfo = JsonUtil.fromJson(sourceStr, LoginInfo.class);
            if (loginInfo == null) {
                throw new RecruitmentException(RecruitmentErrorCode.USER_NOT_LOGIN);
            }
            return loginInfo;
        } catch (Exception e) {
            throw new RecruitmentException(RecruitmentErrorCode.USER_NOT_LOGIN, e);
        }
    }
}
