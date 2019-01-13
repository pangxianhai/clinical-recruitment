package com.andy.recruitment.weixin.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * 微信Token
 *
 * @author 庞先海 2019-01-13
 */
public class AccessToken implements Serializable {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("ticket")
    private String ticket;

    @SerializedName("expires_in")
    private Long expiresIn;

    private Long addTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
