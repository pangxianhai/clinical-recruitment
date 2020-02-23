package com.andy.recruitment.manager.weixin.service;

import com.andy.recruitment.manager.weixin.entity.AccessToken;
import com.andy.recruitment.manager.weixin.entity.OauthToken;
import com.andy.recruitment.manager.weixin.entity.WxUserInfo;
import com.andy.recruitment.manager.weixin.properties.WeiXinProperties;
import com.soyoung.base.util.DateUtil;
import com.soyoung.base.util.HttpClientUtil;
import com.soyoung.base.util.JsonUtil;
import com.soyoung.base.util.encrypt.EncodeUtil;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信服务接口服务实现
 *
 * @author 庞先海 2020-02-22
 */
@Service
@Slf4j
public class WeiXinManagerServiceImpl implements WeiXinManagerService {

    private final WeiXinProperties weiXinProperties;

    private final static ConcurrentMap<String, Future<AccessToken>> ACCESS_TOKEN_CACHE = new ConcurrentHashMap<>();

    @Autowired
    public WeiXinManagerServiceImpl(WeiXinProperties weiXinProperties) {
        this.weiXinProperties = weiXinProperties;
    }

    @Override
    public String getWeiXinLoginUrl(String redirectUri) {
        String formatStr = "/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        return weiXinProperties.getOpenAddress() + MessageFormat.format(formatStr, weiXinProperties.getAppId(),
            EncodeUtil.urlEncode(redirectUri));
    }

    @Override
    public String getAccessToken() {
        String cacheKey = "weixinAccessTokenKey";
        while (true) {
            Future<AccessToken> cacheAccessTokenFuture = ACCESS_TOKEN_CACHE.get(cacheKey);
            if (null == cacheAccessTokenFuture) {
                FutureTask<AccessToken> futureTask = new FutureTask<>(() -> {

                    String url = weiXinProperties.getApiAddress() + "/cgi-bin/token";
                    Map<String, Object> params = new HashMap<>(3);
                    params.put("grant_type", "client_credential");
                    params.put("appid", weiXinProperties.getAppId());
                    params.put("secret", weiXinProperties.getAppSecret());
                    String accessTokenStr = HttpClientUtil.sendGet(url, params);
                    AccessToken accessToken = JsonUtil.fromJson(accessTokenStr, AccessToken.class);
                    accessToken.setAddTime(DateUtil.currentMilliseconds());
                    return accessToken;
                });
                cacheAccessTokenFuture = ACCESS_TOKEN_CACHE.putIfAbsent(cacheKey, futureTask);
                if (null == cacheAccessTokenFuture) {
                    cacheAccessTokenFuture = futureTask;
                    futureTask.run();
                }
            }
            try {
                AccessToken accessToken = cacheAccessTokenFuture.get();
                long time = DateUtil.currentMilliseconds() - accessToken.getAddTime();
                if (time / 1000 >= accessToken.getExpiresIn() - 10) {
                    ACCESS_TOKEN_CACHE.remove(cacheKey, cacheAccessTokenFuture);
                } else {
                    return accessToken.getAccessToken();
                }
            } catch (CancellationException e) {
                ACCESS_TOKEN_CACHE.remove(cacheKey, cacheAccessTokenFuture);
            } catch (ExecutionException | InterruptedException e) {
                log.error("getAccessToken failed", e);
                return null;
            }
        }
    }

    @Override
    public String getTicket() {
        String cacheKey = "weixinTicketKey";
        while (true) {
            Future<AccessToken> cacheAccessTokenFuture = ACCESS_TOKEN_CACHE.get(cacheKey);
            if (null == cacheAccessTokenFuture) {
                FutureTask<AccessToken> futureTask = new FutureTask<>(() -> {
                    String accessToken = this.getAccessToken();
                    String url = weiXinProperties.getApiAddress() + "/cgi-bin/ticket/getticket";
                    Map<String, Object> params = new HashMap<>(2);
                    params.put("access_token", accessToken);
                    params.put("type", "jsapi");
                    String ticketStr = HttpClientUtil.sendGet(url, params);
                    AccessToken accessTokenObj = JsonUtil.fromJson(ticketStr, AccessToken.class);
                    accessTokenObj.setAddTime(DateUtil.currentMilliseconds());
                    return accessTokenObj;
                });
                cacheAccessTokenFuture = ACCESS_TOKEN_CACHE.putIfAbsent(cacheKey, futureTask);
                if (null == cacheAccessTokenFuture) {
                    cacheAccessTokenFuture = futureTask;
                    futureTask.run();
                }
            }
            try {
                AccessToken accessToken = cacheAccessTokenFuture.get();
                long time = DateUtil.currentMilliseconds() - accessToken.getAddTime();
                if (time / 1000 >= accessToken.getExpiresIn() - 10) {
                    ACCESS_TOKEN_CACHE.remove(cacheKey, cacheAccessTokenFuture);
                } else {
                    return accessToken.getTicket();
                }
            } catch (CancellationException e) {
                ACCESS_TOKEN_CACHE.remove(cacheKey, cacheAccessTokenFuture);
            } catch (ExecutionException | InterruptedException e) {
                log.error("getTicket failed", e);
                return null;
            }
        }
    }

    @Override
    public OauthToken getOauthAccessToken(String code) {
        String url = this.weiXinProperties.getApiAddress() + "/sns/oauth2/access_token";
        Map<String, Object> params = new HashMap<>(4);
        params.put("appid", this.weiXinProperties.getAppId());
        params.put("secret", this.weiXinProperties.getAppSecret());
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        String oauthResultStr = HttpClientUtil.sendGet(url, params);
        OauthToken oauthToken = JsonUtil.fromJson(oauthResultStr, OauthToken.class);
        if (StringUtils.isEmpty(oauthToken.getErrCode()) || OauthToken.SUCCESS_CODE.equals(oauthToken.getErrCode())) {
            return oauthToken;
        } else {
            log.warn("[微信登录] 获取 access token 失败 微信返回结果: {}", oauthResultStr);
            return null;
        }
    }

    @Override
    public WxUserInfo getWxUserInfo(String accessToken, String openId) {
        String url = this.weiXinProperties.getApiAddress() + "/sns/userinfo";
        Map<String, Object> params = new HashMap<>(3);
        params.put("access_token", accessToken);
        params.put("openid", openId);
        params.put("lang", "zh_CN");
        String wxUserInfoStr = HttpClientUtil.sendGet(url, params);
        WxUserInfo wxUserInfo = JsonUtil.fromJson(wxUserInfoStr, WxUserInfo.class);
        if (StringUtils.isEmpty(wxUserInfo.getErrCode()) || WxUserInfo.SUCCESS_CODE.equals(wxUserInfo.getErrCode())) {
            return wxUserInfo;
        } else {
            log.warn("[微信登录] 获取用户信息失败 微信返回结果: {}", wxUserInfoStr);
            return null;
        }
    }
}
