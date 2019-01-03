package com.andy.recruitment.user.model;

import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.constant.UserStatus;
import com.xgimi.base.BaseDO;
import com.xgimi.commons.util.JsonUtil;

/**
 * 用户信息DO
 *
 * @author 庞先海 2018-12-28
 */
public class UserInfoDO extends BaseDO {

    /**
     * 微信OpenID
     */
    private String openId;
    /**
     * 微信关联唯一ID
     */
    private String unionId;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 性别
     */
    private Gender gender;
    /**
     * 用户状态
     */
    private UserStatus status;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}