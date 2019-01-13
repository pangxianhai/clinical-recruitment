package com.andy.recruitment.weixin.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/**
 * 微信返回的用户信息
 *
 * @author 庞先海 2019-01-13
 */
public class WxUserInfo implements Serializable {

    @SerializedName("openid")
    private String openId;

    private String nickname;

    private String sex;

    private String country;

    private String province;

    private String city;

    @SerializedName("headimgurl")
    private String headImgUrl;

    private List<String> privilege;

    private String unionid;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public List<String> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<String> privilege) {
        this.privilege = privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
