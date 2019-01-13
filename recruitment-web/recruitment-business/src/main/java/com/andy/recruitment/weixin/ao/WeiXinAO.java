package com.andy.recruitment.weixin.ao;

import com.andy.recruitment.weixin.model.WeiXinSign;

/**
 * 微信业务
 *
 * @author 庞先海 2019-01-13
 */
public interface WeiXinAO {

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
     * 构建微信签名
     *
     * @param url 当前页面url
     * @return 签名结果
     */
    WeiXinSign buildSignature(String url);

    /**
     * 获取微信登陆url
     * @param redirectUri 回掉地址
     * @return 微信登陆url
     */
    String getWeiXinLoginUrl(String redirectUri);
}
