package com.andy.recruitment.weixin.service;

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
}
