package com.andy.recruitment.web.controller.admin.response;

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

    private String token;
}
