package com.andy.recruitment.sms.service;

/**
 * sms服务接口
 *
 * @author 庞先海 2019-01-11
 */
public interface SmsService {

    void sendMessage(String phone, String content);
}
