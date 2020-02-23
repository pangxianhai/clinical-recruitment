package com.andy.recruitment.manager.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;
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

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "ticket")
    private String ticket;

    @JSONField(name = "expires_in")
    private Long expiresIn;

    private Long addTime;
}
