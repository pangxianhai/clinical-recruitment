package com.andy.recruitment.web.handler;

import com.andy.recruitment.web.RecruitmentSystemInfo;
import com.xgimi.handler.MyExceptionHandler;
import com.xgimi.logger.log4j.Logger;
import com.xgimi.logger.log4j.MyLogger;
import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 系统异常拦截器
 *
 * @author 庞先海 2018-12-14
 */
@ControllerAdvice
public class RecruitmentExceptionHandler extends MyExceptionHandler {

    @Logger
    private MyLogger logger;

    @PostConstruct
    public void init() {
        super.setLogger(logger);
        super.setCharsetName(RecruitmentSystemInfo.CHARSET_NAME);
    }
}
