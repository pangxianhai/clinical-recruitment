package com.andy.recruitment.biz.admin.service;

import com.andy.recruitment.api.admin.response.AdminInfoDetailRes;
import com.andy.recruitment.common.admin.constant.AdministratorStatus;
import com.andy.recruitment.dao.admin.entity.AdministratorInfoDO;
import com.andy.recruitment.dao.admin.entity.AdministratorQuery;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.spring.auth.LoginInfo;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;

/**
 * 管理员服务
 *
 * @author 庞先海 2020-01-28
 */
public interface AdministratorService {

    /**
     * 管理员登录
     *
     * @param phone    手机号
     * @param password 密码
     * @return 登录成功返回管理员信息
     */
    LoginInfo passwordLogin(String phone, String password);

    /**
     * 分页查询管理员
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 管理员信息列表
     */
    PageResult<AdminInfoDetailRes> listAdministratorInfo(AdministratorQuery query, Paginator paginator);

    /**
     * 更新管理员信息
     *
     * @param adminId  管理员Id
     * @param status   修改后状态
     * @param operator 操作人
     */
    void updateAdminStatus(Long adminId, AdministratorStatus status, String operator);

    /**
     * 更新管理员信息
     *
     * @param administratorInfoDo 管理员信息
     * @param userInfoDo          用户信息
     * @param operator            操作人
     */
    void updateAdministrator(AdministratorInfoDO administratorInfoDo, UserInfoDO userInfoDo, String operator);
}
