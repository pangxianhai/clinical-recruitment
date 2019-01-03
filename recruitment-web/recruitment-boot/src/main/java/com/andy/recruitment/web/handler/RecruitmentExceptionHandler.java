package com.andy.recruitment.web.handler;

import com.andy.recruitment.web.RecruitmentSystemInfo;
import com.xgimi.handler.MyExceptionHandler;
import com.xgimi.logger.log4j.Logger;
import com.xgimi.logger.log4j.MyLogger;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

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

    @ExceptionHandler(value = Throwable.class)
    public ModelAndView viewErrorHandler(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("500");
        return mav;
    }
}
