package com.andy.recruitment.web.controller.user.request;

import java.io.Serializable;

/**
 * 修改管理员参数
 *
 * @author 庞先海 2019-05-23
 */
public class ManagerUpdateRQ implements Serializable {

    /**
     * 真实姓名
     */
    private String realName;

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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
