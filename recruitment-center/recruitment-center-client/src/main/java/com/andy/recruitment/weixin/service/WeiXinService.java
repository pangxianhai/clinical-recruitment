package com.andy.recruitment.weixin.service;

import com.andy.recruitment.weixin.model.OauthToken;
import com.andy.recruitment.weixin.model.WxUserInfo;

/**
 * 微信对接接口
 *
 * @author 庞先海 2019-01-13
 */
public interface WeiXinService {

    /**
     * 获取微信AppId
     *
     * @return 微信appid
     */
    String getWeiXinAppId();

    /**
     * 获取微信登陆url
     *
     * @param redirectUri 回掉地址
     * @return 微信登陆url
     */
    String getWeiXinLoginUrl(String redirectUri);

    /**
     * 获取微信 accessToken
     *
     * @return accessToken
     */
    String getAccessToken();

    /**
     * 获取微信 jsapi_ticket
     *
     * @return jsapi_ticket
     */
    String getTicket();

    /**
     * 通过code获取账户认证token
     *
     * @param code 微信认证后的code
     * @return 账户认证token
     */
    OauthToken getOauthAccessToken(String code);

    /**
     * 获取微信用户信息
     *
     * @param accessToken 微信账户认证accessToken
     * @param openId      用户的openId
     * @return 微信用户信息
     */
    WxUserInfo getWxUserInfo(String accessToken, String openId);
}
