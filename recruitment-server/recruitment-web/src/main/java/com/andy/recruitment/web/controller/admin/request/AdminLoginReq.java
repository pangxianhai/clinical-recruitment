package com.andy.recruitment.web.controller.admin.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 管理员登录参数
 *
 * @author 庞先海 2020-01-28
 */
@Data
public class AdminLoginReq implements Serializable {

    private static final long serialVersionUID = 8004745265678406754L;

    /**
     * 用户名
     */
    @NotBlank
    @Length(max = 64)
    private String userName;

    /**
     * 密码
     */
    @NotBlank
    @Length(max = 64)
    private String password;
}
