package com.andy.recruitment.biz.user.service;

import com.andy.recruitment.dao.user.entity.UserInfoDO;

/**
 * 用户服务接口
 *
 * @author 庞先海 2020-02-02
 */
public interface UserService {

    /**
     * 通过手机号查询用户
     *
     * @param phone 手机号码
     * @return 用户信息
     */
    UserInfoDO getUserInfoByPhone(String phone);
}
