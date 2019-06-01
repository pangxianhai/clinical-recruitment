package com.andy.recruitment.web.controller.user.request;

import java.io.Serializable;

/**
 * 更新密码参数
 *
 * @author 庞先海 2019-05-31
 */
public class UpdatePasswordRQ implements Serializable {

    /**
     * 原密码
     */
    private String password;

    /**
     * 新密码
     */
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
