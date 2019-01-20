package com.andy.recruitment.web.controller.user.request;

import java.io.Serializable;

/**
 * 登陆验证码发送参数
 *
 * @author 庞先海 2019-01-17
 */
public class VerCodeSendRQ implements Serializable {

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
