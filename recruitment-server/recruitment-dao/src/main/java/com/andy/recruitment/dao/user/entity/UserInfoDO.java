package com.andy.recruitment.dao.user.entity;

import com.andy.recruitment.common.user.constant.Gender;
import com.andy.recruitment.common.user.constant.UserStatus;
import com.andy.spring.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户信息DO
 *
 * @author 庞先海 2018-12-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserInfoDO extends BaseDO {

    private static final long serialVersionUID = - 4909043357013180156L;

    /**
     * 微信OpenID
     */
    private String openId;
    /**
     * 微信关联唯一ID
     */
    private String unionId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private Gender gender;
    /**
     * 用户状态
     */
    private UserStatus status;
}
