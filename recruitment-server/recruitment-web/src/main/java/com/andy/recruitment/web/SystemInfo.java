package com.andy.recruitment.web;

import com.soyoung.base.constant.Constant;
import com.soyoung.base.message.MessageHandler;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 系统常量
 *
 * @author 庞先海 2018-12-14
 */
@Component
public class SystemInfo implements CommandLineRunner {

    /**
     * 系统编码
     */
    public static String CHARSET_NAME = StandardCharsets.UTF_8.name();
    /**
     * 日期字符串格式
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void run(String... strings) throws Exception {

        //设置系统默认值
        defaultInit();

        //初始化国际化
        initIntern();
    }

    private void defaultInit() {
        Constant.DEFAULT_CHARSET = StandardCharsets.UTF_8;
        Constant.DEFAULT_PATTERN = DateTimeFormatter.ofPattern(DATE_PATTERN);
    }

    /**
     * 初始化国际化消息
     */
    private void initIntern() {
        MessageHandler.setBeanNames("i18/recruitment/recruitment-error");
        MessageHandler.setBeanNames("i18/common/base-error");
    }
}
