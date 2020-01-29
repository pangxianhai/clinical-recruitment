package com.andy.recruitment.biz.admin.service;

import com.soyoung.base.auth.LoginInfo;

/**
 * 管理员服务
 *
 * @author 庞先海 2020-01-28
 */
public interface AdministratorService {

    /**
     * 管理员登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return 登录成功返回管理员信息
     */
    LoginInfo passwordLogin(String userName, String password);

    /**
     * 通过token登录
     *
     * @param userName 用户名
     * @param token    令牌
     * @return 登录成功返回管理员信息
     */
    LoginInfo tokenLogin(String userName, String token);
}
