package com.andy.recruitment.manager.weixin.properties;

import java.io.Serializable;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * 微信配置
 *
 * @author 庞先海 2020-02-22
 */
@Data
@ConfigurationProperties(prefix = "weixin")
@Service
public class WeiXinProperties implements Serializable {

    private static final long serialVersionUID = 7429100479802111646L;

    private String appId;

    private String appSecret;

    private String apiAddress;

    private String openAddress;
}
