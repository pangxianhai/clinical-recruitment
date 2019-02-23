package com.andy.recruitment.user.ao;

import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.model.UserQueryParam;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.page.Paginator;

/**
 * 用户AO
 *
 * @author 庞先海 2018-12-28
 */
public interface UserAO {

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

    /**
     * 发送验证码短信
     *
     * @param phone 手机号
     */
    void sendLoginVerCode(String phone);

    /**
     * 用户绑定手机号
     *
     * @param phone   手机号
     * @param userId  用户ID
     * @param verCode 验证码
     */
    void bandPhone(Long userId, String phone, String verCode);

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     * @param operator 操作人
     */
    void updateUserInfo(UserInfo userInfo, String operator);

    /**
     * 通过手机号码登陆
     *
     * @param phone    手机号
     * @param password 密码
     * @return 用户信息
     */
    UserInfo loginByPhone(String phone, String password);

    /**
     * 添加用户信息
     *
     * @param userInfo 用户信息
     * @param operator 操作人
     */
    void addUserInfo(UserInfo userInfo, String operator);

    /**
     * 分页用户查询
     *
     * @param queryParam 查询参数
     * @param paginator  分页参数
     * @return 分页查询结果
     */
    PageResult<UserInfo> getUserInfo(UserQueryParam queryParam, Paginator paginator);
}
