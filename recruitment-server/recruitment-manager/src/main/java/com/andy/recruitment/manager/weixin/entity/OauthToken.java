package com.andy.recruitment.manager.weixin.entity;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

/**
 * 微信认证信息
 *
 * @author 庞先海 2019-01-13
 */
@Data
public class OauthToken implements Serializable {

    private static final long serialVersionUID = 2835723481404820602L;

    public static final String SUCCESS_CODE = "0";

    /**
     * 错误码
     */
    @SerializedName(value = "errCode", alternate = "errcode")
    private String errCode;

    @SerializedName(value = "accessToken", alternate = "access_token")
    private String accessToken;

    @SerializedName(value = "refreshToken", alternate = "refresh_token")
    private String refreshToken;

    @SerializedName(value = "expiresIn", alternate = "expires_in")
    private Long expiresIn;

    @SerializedName(value = "openId", alternate = "openid")
    private String openId;

    private String scope;
}
