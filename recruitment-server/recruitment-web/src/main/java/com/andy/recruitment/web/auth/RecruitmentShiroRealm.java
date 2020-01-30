package com.andy.recruitment.web.auth;

import com.andy.recruitment.biz.admin.service.AdministratorService;
import com.soyoung.base.auth.LoginInfo;
import com.soyoung.base.context.ServletContext;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能描述
 *
 * @author 庞先海 2020-01-28
 */
public class RecruitmentShiroRealm extends AuthorizingRealm {

    @Autowired
    private AdministratorService administratorService;

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
        LoginInfo loginInfo = administratorService.tokenLogin(userName, token);
        ServletContext.setLoginInfo(loginInfo);
        return new SimpleAuthenticationInfo(userName, token, getName());
    }
}
