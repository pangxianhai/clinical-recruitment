package com.andy.recruitment.web.controller.user.request;

import java.io.Serializable;

/**
 * 微信登录参数
 *
 * @author 庞先海 2019-04-05
 */
public class WxLoginRQ implements Serializable {

    /**
     * 微信获取用户信息的code
     */
    private String code;
    /**
     * 需要的动作 比如报名
     */
    private String action;
    /**
     * 招募ID
     */
    private Long recruitmentId;
    /**
     * 用户类型
     */
    private Integer userType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
