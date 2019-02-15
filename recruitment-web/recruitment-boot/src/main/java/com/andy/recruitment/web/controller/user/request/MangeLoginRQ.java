package com.andy.recruitment.web.controller.user.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * 管理员登陆参数
 *
 * @author 庞先海 2019-02-15
 */
public class MangeLoginRQ implements Serializable {

    @NotBlank
    @Length(max = 20)
    private String phone;

    @NotBlank
    @Length(max = 16)
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
