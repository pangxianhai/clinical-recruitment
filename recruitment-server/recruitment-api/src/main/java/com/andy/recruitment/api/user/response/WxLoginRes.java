package com.andy.recruitment.api.user.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信登录结果
 *
 * @author 庞先海 2020-02-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WxLoginRes extends UserInfoRes {

    private static final long serialVersionUID = - 5546662166963425349L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录成功的令牌
     */
    private String token;

    /**
     * 是否已经注册患者
     */
    private Boolean hasPatient;

    /**
     * 是否已经注册推荐人
     */
    private Boolean hasReference;

    /**
     * 是否已经注册研究员
     */
    private Boolean hasResearcher;
}
