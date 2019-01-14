package com.andy.recruitment.user.ao;

import com.andy.recruitment.user.model.UserInfo;

/**
 * 用户AO
 *
 * @author 庞先海 2018-12-28
 */
public interface UserAO {

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     * @param operator 操作人
     */
    void updateUserInfo(UserInfo userInfo, String operator);

    /**
     * 通过用户ID查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfo getUserInfoByUserId(Long userId);

    /**
     * 通过OpenID查询用户信息
     *
     * @param openId 微信OPENID
     * @return 用户信息
     */
    UserInfo getUserInfoByOpenId(String openId);

    /**
     * 微信登陆
     *
     * @param code 微信授权code
     * @return 用户信息
     */
    UserInfo loginByWeixin(String code);
}
