package com.andy.recruitment.dao.user.dao;

import com.andy.recruitment.common.user.constant.UserStatus;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.dao.user.entity.UserQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.List;

/**
 * 用户dao接口
 *
 * @author 庞先海 2020-01-26
 */
public interface UserDAO {

    /**
     * 注册用户信息
     *
     * @param userInfoDo 用户信息
     * @param operator   操作人
     * @return 用户ID
     */
    Long registerUser(UserInfoDO userInfoDo, String operator);

    /**
     * 更新用户信息
     *
     * @param userInfoDo 用户信息
     * @param operator   操作人
     */
    void updateUserInfo(UserInfoDO userInfoDo, String operator);

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
    UserInfoDO getUserInfoByUserId(Long userId);

    /**
     * 通过OpenID查询用户信息
     *
     * @param openId 微信OPENID
     * @return 用户信息
     */
    UserInfoDO getUserInfoByOpenId(String openId);

    /**
     * 通过手机号查询用户
     *
     * @param phone 手机号码
     * @return 用户信息
     */
    UserInfoDO getUserInfoByPhone(String phone);

    /**
     * 分页用户查询
     *
     * @param queryParam 查询参数
     * @param paginator  分页参数
     * @return 分页查询结果
     */
    PageResult<UserInfoDO> getUserInfo(UserQuery queryParam, Paginator paginator);

    /**
     * 通过用户ID列表查询用户ID
     *
     * @param userIdList 用户已ID列表
     * @return 用户信息列表
     */
    List<UserInfoDO> getUserInfo(List<Long> userIdList);

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     */
    void delete(Long userId);
}
