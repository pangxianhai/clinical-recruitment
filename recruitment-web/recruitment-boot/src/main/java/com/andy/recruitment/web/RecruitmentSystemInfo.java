package com.andy.recruitment.web;

import com.xgimi.commons.constant.Constant;
import com.xgimi.commons.message.MessageHandler;
import com.xgimi.commons.util.RandomUtil;
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

    /**
     * 版本号
     */
    public static String VERSION = "version";

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

        VERSION = RandomUtil.getRandCode(16, false);
    }

    private void initConfig() {
        RecruitmentSystemInfo.CHARSET_NAME = charsetName;
        RecruitmentSystemInfo.SERVER_NAME = serverName;
    }

    private void defaultInit() {
        Constant.setDefaultPattern(CHARSET_NAME);
        Constant.DEFAULT_PATTERN = DATE_PATTERN;
    }

    /**
     * 初始化国际化消息
     */
    private void initIntern() {
        MessageHandler.setBeanNames("i18/recruitment/recruitment-error");
        MessageHandler.setBeanNames("i18/recruitment/recruitment-center-error");
        MessageHandler.setBeanNames("i18/common/commons-error");
    }

    public String getCharsetName() {
        return charsetName;
    }

    public String getServerName() {
        return serverName;
    }
}
