package com.andy.recruitment.weixin.service;

import com.andy.recruitment.weixin.model.AccessToken;
import com.andy.recruitment.weixin.model.OauthToken;
import com.andy.recruitment.weixin.model.WxUserInfo;
import com.xgimi.commons.util.DateUtil;
import com.xgimi.commons.util.HttpClientUtil;
import com.xgimi.commons.util.JsonUtil;
import com.xgimi.commons.util.encrypt.EncodeUtil;
import com.xgimi.logger.log4j.Logger;
import com.xgimi.logger.log4j.MyLogger;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 微信对接接口
 *
 * @author 庞先海 2019-01-13
 */
@Service
public class WeiXinServiceImpl implements WeiXinService {

    @Logger
    private MyLogger logger;

    @Value("${weixin.app.id}")
    private String wxAppId;

    @Value("${weixin.app.secret}")
    private String wxAppSecret;

    @Value("${weixin.api.address}")
    private String wxApiAddress;

    @Value("${weixin.open.address}")
    private String wxOpenAddress;

    private final static ConcurrentMap<String, Future<AccessToken>> accessTokenCache = new ConcurrentHashMap<>();

    @Override
    public String getWeiXinAppId() {
        return this.wxAppId;
    }

    @Override
    public String getWeiXinLoginUrl(String redirectUri) {
        String formatStr = "/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        return wxApiAddress + MessageFormat.format(formatStr, wxAppId, EncodeUtil.urlEncode(redirectUri));
    }

    @Override
    public String getAccessToken() {
        String cacheKey = "weixinAccessTokenKey";
        while (true) {
            Future<AccessToken> cacheAccessTokenFuture = accessTokenCache.get(cacheKey);
            if (null == cacheAccessTokenFuture) {
                FutureTask<AccessToken> futureTask = new FutureTask<>(() -> {

                    String url = wxApiAddress + "/cgi-bin/token";
                    Map<String, Object> params = new HashMap<>();
                    params.put("grant_type", "client_credential");
                    params.put("appid", wxAppId);
                    params.put("secret", wxAppSecret);
                    String accessTokenStr = HttpClientUtil.sendGet(url, params);
                    AccessToken accessToken = JsonUtil.fromJson(accessTokenStr, AccessToken.class);
                    accessToken.setAddTime(DateUtil.currentMilliseconds());
                    return accessToken;
                });
                cacheAccessTokenFuture = accessTokenCache.putIfAbsent(cacheKey, futureTask);
                if (null == cacheAccessTokenFuture) {
                    cacheAccessTokenFuture = futureTask;
                    futureTask.run();
                }
            }
            try {
                AccessToken accessToken = cacheAccessTokenFuture.get();
                long time = DateUtil.currentMilliseconds() - accessToken.getAddTime();
                if (time / 1000 >= accessToken.getExpiresIn() - 10) {
                    accessTokenCache.remove(cacheKey, cacheAccessTokenFuture);
                } else {
                    return accessToken.getAccessToken();
                }
            } catch (CancellationException e) {
                accessTokenCache.remove(cacheKey, cacheAccessTokenFuture);
            } catch (ExecutionException | InterruptedException e) {
                logger.error("getAccessToken failed", e);
                return null;
            }
        }
    }

    @Override
    public String getTicket() {
        String cacheKey = "weixinTicketKey";
        while (true) {
            Future<AccessToken> cacheAccessTokenFuture = accessTokenCache.get(cacheKey);
            if (null == cacheAccessTokenFuture) {
                FutureTask<AccessToken> futureTask = new FutureTask<>(() -> {
                    String accessToken = this.getAccessToken();
                    String url = wxApiAddress + "/cgi-bin/ticket/getticket";
                    Map<String, Object> params = new HashMap<>();
                    params.put("access_token", accessToken);
                    params.put("type", "jsapi");
                    String ticketStr = HttpClientUtil.sendGet(url, params);
                    AccessToken accessTokenObj = JsonUtil.fromJson(ticketStr, AccessToken.class);
                    accessTokenObj.setAddTime(DateUtil.currentMilliseconds());
                    return accessTokenObj;
                });
                cacheAccessTokenFuture = accessTokenCache.putIfAbsent(cacheKey, futureTask);
                if (null == cacheAccessTokenFuture) {
                    cacheAccessTokenFuture = futureTask;
                    futureTask.run();
                }
            }
            try {
                AccessToken accessToken = cacheAccessTokenFuture.get();
                long time = DateUtil.currentMilliseconds() - accessToken.getAddTime();
                if (time / 1000 >= accessToken.getExpiresIn() - 10) {
                    accessTokenCache.remove(cacheKey, cacheAccessTokenFuture);
                } else {
                    return accessToken.getTicket();
                }
            } catch (CancellationException e) {
                accessTokenCache.remove(cacheKey, cacheAccessTokenFuture);
            } catch (ExecutionException | InterruptedException e) {
                logger.error("getTicket failed", e);
                return null;
            }
        }
    }

    @Override
    public OauthToken getOauthAccessToken(String code) {
        String url = this.wxApiAddress + "/sns/oauth2/access_token";
        Map<String, Object> params = new HashMap<>();
        params.put("appid", wxAppId);
        params.put("secret", wxAppSecret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        String oauthResultStr = HttpClientUtil.sendGet(url, params);
        return JsonUtil.fromJson(oauthResultStr, OauthToken.class);
    }

    public WxUserInfo getWxUserInfo(String accessToken, String openId) {
        String url = this.wxApiAddress + "/sns/userinfo";
        Map<String, Object> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("openid", openId);
        params.put("lang", "zh_CN");
        String wxUserInfoStr = HttpClientUtil.sendGet(url, params);
        return JsonUtil.fromJson(wxUserInfoStr, WxUserInfo.class);
    }

}
