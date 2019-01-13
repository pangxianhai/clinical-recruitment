package com.andy.recruitment.weixin.ao;

import com.andy.recruitment.weixin.model.WeiXinSign;
import com.andy.recruitment.weixin.service.WeiXinService;
import com.xgimi.commons.util.DateUtil;
import com.xgimi.commons.util.RandomUtil;
import com.xgimi.commons.util.encrypt.HashUtil;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 微信业务
 *
 * @author 庞先海 2019-01-13
 */
@Component
public class WeiXinAOImpl implements WeiXinAO {

    private final WeiXinService weiXinService;

    @Autowired
    public WeiXinAOImpl(WeiXinService weiXinService) {
        this.weiXinService = weiXinService;
    }

    @Override
    public String getAccessToken() {
        return this.weiXinService.getAccessToken();
    }

    @Override
    public String getTicket() {
        return this.weiXinService.getTicket();
    }

    @Override
    public WeiXinSign buildSignature(String url) {
        String noncestr = RandomUtil.getRandCode(16, false);
        String timestamp = String.valueOf(DateUtil.currentMilliseconds() / 1000);
        String ticket = this.getTicket();

        Map<String, String> param = new TreeMap<>();
        param.put("noncestr", noncestr);
        param.put("jsapi_ticket", ticket);
        param.put("timestamp", timestamp);
        param.put("url", url);

        StringBuilder signatureBuffer = new StringBuilder();
        for (String key : param.keySet()) {
            signatureBuffer.append(key).append("=").append(param.get(key)).append("&");
        }
        String signatureStr = signatureBuffer.toString();
        signatureStr = signatureStr.substring(0, signatureStr.length() - 1);
        String signature = HashUtil.shaHash(signatureStr);

        WeiXinSign weiXinSign = new WeiXinSign();
        weiXinSign.setNoncestr(noncestr);
        weiXinSign.setTimestamp(timestamp);
        weiXinSign.setAppId(this.weiXinService.getWeiXinAppId());
        weiXinSign.setSignature(signature);

        return weiXinSign;
    }
}
