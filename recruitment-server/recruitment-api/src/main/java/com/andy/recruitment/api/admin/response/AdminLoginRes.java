package com.andy.recruitment.api.admin.response;

import java.io.Serializable;
import lombok.Data;

/**
 * 管理员登录返回结果
 *
 * @author 庞先海 2020-01-28
 */
@Data
public class AdminLoginRes implements Serializable {

    private static final long serialVersionUID = - 1803514298465841448L;

    /**
     * 登录令牌
     */
    private String token;

    /**
     * 用户名
     */
    private String userName;
}
