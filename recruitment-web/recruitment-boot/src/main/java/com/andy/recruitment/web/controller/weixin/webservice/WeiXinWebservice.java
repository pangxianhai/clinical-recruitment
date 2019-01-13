package com.andy.recruitment.web.controller.weixin.webservice;

import com.andy.recruitment.weixin.ao.WeiXinAO;
import com.andy.recruitment.weixin.model.WeiXinSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信接口
 *
 * @author 庞先海 2019-01-13
 */
@RestController
@RequestMapping("/weixin")
public class WeiXinWebservice {

    private final WeiXinAO weiXinAO;

    @Autowired
    public WeiXinWebservice(WeiXinAO weiXinAO) {
        this.weiXinAO = weiXinAO;
    }

    @RequestMapping(value = "/sign", method = {RequestMethod.GET})
    public WeiXinSign buildSignature(String address) {
        return this.weiXinAO.buildSignature(address);
    }
}
