package com.andy.recruitment.manager.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
    @JSONField(name = "errcode")
    private String errCode;

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "refresh_token")
    private String refreshToken;

    @JSONField(name = "expires_in")
    private Long expiresIn;

    @JSONField(name = "openid")
    private String openId;

    private String scope;
}
