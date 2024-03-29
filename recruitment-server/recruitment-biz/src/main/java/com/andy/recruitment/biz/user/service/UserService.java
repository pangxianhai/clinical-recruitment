package com.andy.recruitment.biz.user.service;

import com.andy.recruitment.api.user.response.UserInfoRes;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.spring.auth.LoginInfo;
import java.util.List;
import java.util.Map;

/**
 * 用户服务接口
 *
 * @author 庞先海 2020-02-02
 */
public interface UserService {

    /**
     * 注册用户信息
     *
     * @param userInfoDo 用户信息
     * @param operator   操作人
     * @return 用户ID
     */
    Long registerUser(UserInfoDO userInfoDo, String operator);

    /**
     * 通过手机号查询用户
     *
     * @param phone 手机号码
     * @return 用户信息
     */
    UserInfoRes getUserInfoByPhone(String phone);

    /**
     * 通过手机号查询用户
     *
     * @param phone 手机号码
     * @return 用户信息Do
     */
    UserInfoDO getUserInfoDoByPhone(String phone);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfoRes getUserInfoByUserId(Long userId);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfoDO getUserDoByUserId(Long userId);

    /**
     * 通过用户ID列表查询用户ID
     *
     * @param userIdList 用户已ID列表
     * @return 用户信息列表
     */
    List<UserInfoDO> getUserInfo(List<Long> userIdList);

    /**
     * 通过用户ID列表查询用户ID
     *
     * @param userIdList 用户已ID列表
     * @return 用户信息列表
     */
    Map<Long, UserInfoRes> getUserInfoRes(List<Long> userIdList);

    /**
     * 微信登录
     *
     * @param code 授权码
     * @return 用户信息
     */
    UserInfoDO wxLogin(String code);

    /**
     * 通过用户信息登录 所有信息都在userInfoDo 生成token和shirodeng登录
     *
     * @param userInfoDo 用户信息
     * @return 登录信息
     */
    LoginInfo userInfoLogin(UserInfoDO userInfoDo);

    /**
     * 更新用户信息
     *
     * @param userInfoDo 用户信息
     * @param operator   操作人
     */
    void updateUserInfo(UserInfoDO userInfoDo, String operator);

}
