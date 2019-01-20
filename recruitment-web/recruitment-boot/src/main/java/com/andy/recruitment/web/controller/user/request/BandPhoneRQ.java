package com.andy.recruitment.web.controller.user.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * 绑定手机号参数
 *
 * @author 庞先海 2019-01-17
 */
public class BandPhoneRQ implements Serializable {

    @NotBlank
    @Length(max = 20)
    private String phone;

    private Integer userType;

    private String verCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
