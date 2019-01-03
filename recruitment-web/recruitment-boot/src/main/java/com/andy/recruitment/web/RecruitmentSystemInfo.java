package com.andy.recruitment.web;

import com.xgimi.commons.message.MessageHandler;
import com.xgimi.commons.util.DateUtil;
import com.xgimi.commons.util.encrypt.AESCBCUtil;
import com.xgimi.commons.util.encrypt.AESUtil;
import com.xgimi.commons.util.encrypt.EncodeUtil;
import com.xgimi.commons.util.encrypt.HashUtil;
import com.xgimi.commons.util.encrypt.RSAUtil;
import com.xgimi.logger.log4j.Logger;
import com.xgimi.logger.log4j.MyLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

/**
 * 系统常量
 *
 * @author 庞先海 2018-12-14
 */
public class RecruitmentSystemInfo implements CommandLineRunner {

    /**
     * 系统编码
     */
    public static String CHARSET_NAME = "UTF-8";

    /**
     * 服务名称
     */
    public static String SERVER_NAME;

    /**
     * 日期字符串格式
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Value("${server.charset.name}")
    private String charsetName;

    @Value("${server.name}")
    private String serverName;

    @Logger
    private MyLogger logger;

    @Override
    public void run(String... strings) throws Exception {
        //初始化配置
        initConfig();

        //设置系统默认值
        defaultInit();

        //初始化国际化
        initIntern();
    }

    private void initConfig() {
        RecruitmentSystemInfo.CHARSET_NAME = charsetName;
        RecruitmentSystemInfo.SERVER_NAME = serverName;
    }

    private void defaultInit() {
        DateUtil.setDefaultPattern(RecruitmentSystemInfo.DATE_PATTERN);
        HashUtil.setCharsetName(RecruitmentSystemInfo.CHARSET_NAME);
        AESUtil.setCharsetName(RecruitmentSystemInfo.CHARSET_NAME);
        AESUtil.setDefaultEncryptKey("g4pG79CAQ73l2xQZzABnFw==");
        AESCBCUtil.setCharsetName(RecruitmentSystemInfo.CHARSET_NAME);
        EncodeUtil.setCharsetName(RecruitmentSystemInfo.CHARSET_NAME);
        RSAUtil.setCharsetName(RecruitmentSystemInfo.CHARSET_NAME);
    }

    /**
     * 初始化国际化消息
     */
    private void initIntern() {
        MessageHandler.setBeanNames("i18/message/message-error");
    }

    public String getCharsetName() {
        return charsetName;
    }

    public String getServerName() {
        return serverName;
    }
}
