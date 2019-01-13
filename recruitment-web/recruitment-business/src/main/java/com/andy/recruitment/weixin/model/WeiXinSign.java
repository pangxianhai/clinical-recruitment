package com.andy.recruitment.weixin.model;

import java.io.Serializable;

/**
 * 微信签名结果
 *
 * @author 庞先海 2019-01-13
 */
public class WeiXinSign implements Serializable {

    private String noncestr;
    private String timestamp;
    private String appId;
    private String signature;

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
