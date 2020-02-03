package com.andy.recruitment.dao.user.entity;

import com.andy.recruitment.dao.user.constant.Gender;
import com.andy.recruitment.dao.user.constant.UserStatus;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 用户信息查询参数
 *
 * @author 庞先海 2018-12-28
 */
@Data
public class UserQuery implements Serializable {

    private static final long serialVersionUID = - 2490156560579695563L;
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
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 手机号码模糊查询
     */
    private String phoneLike;
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
    /**
     * 用户id列表
     */
    private List<Long> userIdLIst;
}
