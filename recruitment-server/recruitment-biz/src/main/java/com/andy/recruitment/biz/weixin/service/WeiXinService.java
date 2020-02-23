package com.andy.recruitment.biz.weixin.service;

/**
 * 微信服务
 *
 * @author 庞先海 2020-02-22
 */
public interface WeiXinService {

    /**
     * 获取微信登陆url
     *
     * @param redirectUri 回掉地址
     * @return 微信登陆url
     */
    String getWeiXinLoginUrl(String redirectUri);

}
