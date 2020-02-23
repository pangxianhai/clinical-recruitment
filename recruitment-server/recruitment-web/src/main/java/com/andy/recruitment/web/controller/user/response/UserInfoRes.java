package com.andy.recruitment.web.controller.user.response;

import com.andy.recruitment.dao.user.constant.Gender;
import com.andy.recruitment.dao.user.constant.UserStatus;
import java.io.Serializable;
import lombok.Data;

/**
 * 用户信息
 *
 * @author 庞先海 2020-02-02
 */
@Data
public class UserInfoRes implements Serializable {

    private static final long serialVersionUID = 5624181514448972231L;

    /**
     * 用户ID
     */
    private Long userId;
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
     * 性别
     */
    private Gender gender;
    /**
     * 用户状态
     */
    private UserStatus status;
}
