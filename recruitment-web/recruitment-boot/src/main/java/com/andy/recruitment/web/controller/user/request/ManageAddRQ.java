package com.andy.recruitment.web.controller.user.request;

import java.io.Serializable;

/**
 * 管理員添加參數
 *
 * @author 庞先海 2019-03-03
 */
public class ManageAddRQ implements Serializable {

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
    private Integer gender;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
