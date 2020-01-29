package com.andy.recruitment.web.handler;

import com.andy.recruitment.web.SystemInfo;
import com.soyoung.base.handler.MyExceptionHandler;
import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 系统异常拦截器
 *
 * @author 庞先海 2018-12-14
 */
@ControllerAdvice
public class RecruitmentExceptionHandler extends MyExceptionHandler {

    @PostConstruct
    public void init() {
        super.setCharsetName(SystemInfo.CHARSET_NAME);
    }
}
