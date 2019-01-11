package com.andy.recruitment.sms;

import com.andy.recruitment.sms.service.SmsService;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * sms服务实现
 *
 * @author 庞先海 2019-01-11
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${yunpian.api.key}")
    private String yunpianApiKey;

    private YunpianClient client;

    @PostConstruct
    public void init() {
        client = new YunpianClient(yunpianApiKey).init();
    }

    @Override
    public void sendMessage(String phone, String content) {
        Map<String, String> param = client.newParam(2);
        param.put(YunpianClient.MOBILE, phone);
        param.put(YunpianClient.TEXT, content);
        Result<SmsSingleSend> r = client.sms().single_send(param);
    }

    @PreDestroy
    public void destory() {
        if (null != client) {
            client.close();
        }
    }
}
