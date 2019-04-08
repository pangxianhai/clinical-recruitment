package com.andy.recruitment.user.service;

import com.andy.recruitment.user.constant.UserStatus;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.model.UserQueryParam;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;

/**
 * 用户信息服务
 *
 * @author 庞先海 2018-12-28
 */
public interface UserInfoService {

    /**
     * 注册用户信息
     *
     * @param userInfo 用户信息
     * @param operator 操作人
     * @return 用户ID
     */
    Long registerUser(UserInfo userInfo, String operator);

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     * @param operator 操作人
     */
    void updateUserInfo(UserInfo userInfo, String operator);

    /**
     * 更新用户状态
     *
     * @param userId   用户ID
     * @param status   更新后的状态
     * @param operator 操作人
     */
    void updateUserStatus(Long userId, UserStatus status, String operator);

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
     * 通过手机号查询用户
     *
     * @param phone 手机号码
     * @return 用户信息
     */
    UserInfo getUserInfoByPhone(String phone);

    /**
     * 分页用户查询
     *
     * @param queryParam 查询参数
     * @param paginator  分页参数
     * @return 分页查询结果
     */
    PageResult<UserInfo> getUserInfo(UserQueryParam queryParam, Paginator paginator);

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     */
    void delete(Long userId);
}
