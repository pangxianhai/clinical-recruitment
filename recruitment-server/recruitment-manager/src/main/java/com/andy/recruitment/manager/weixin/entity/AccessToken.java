package com.andy.recruitment.manager.weixin.entity;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.Data;

/**
 * 微信Token
 *
 * @author 庞先海 2019-01-13
 */
@Data
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 1690570674133696123L;

    @SerializedName(value = "accessToken", alternate = "access_token")
    private String accessToken;

    private String ticket;

    @SerializedName(value = "expiresIn", alternate = "expires_in")
    private Long expiresIn;

    private Long addTime;
}
