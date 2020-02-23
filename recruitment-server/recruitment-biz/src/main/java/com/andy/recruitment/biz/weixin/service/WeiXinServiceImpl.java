package com.andy.recruitment.biz.weixin.service;

import com.andy.recruitment.manager.weixin.service.WeiXinManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信服务接口服务实现
 *
 * @author 庞先海 2020-02-22
 */
@Service
@Slf4j
public class WeiXinServiceImpl implements WeiXinService {

    private final WeiXinManagerService weiXinManagerService;

    @Autowired
    public WeiXinServiceImpl(WeiXinManagerService weiXinManagerService) {
        this.weiXinManagerService = weiXinManagerService;
    }

    @Override
    public String getWeiXinLoginUrl(String redirectUri) {
        return weiXinManagerService.getWeiXinLoginUrl(redirectUri);
    }
}
