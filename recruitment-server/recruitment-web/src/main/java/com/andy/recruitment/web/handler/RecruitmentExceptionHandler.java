package com.andy.recruitment.web.handler;

import com.andy.recruitment.web.SystemInfo;
import com.soyoung.base.base.Result;
import com.soyoung.base.exception.CommonCode;
import com.soyoung.base.exception.ExceptionUtil;
import com.soyoung.base.handler.MyExceptionHandler;
import com.soyoung.base.message.MessageHandler;
import javax.annotation.PostConstruct;
import org.apache.shiro.authz.UnauthenticatedException;
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

    @Override
    protected Object parseException(Exception e) {
        if (e instanceof UnauthenticatedException) {
            int sysCode = CommonCode.UNAUTHORIZED.getCode();
            return new Result<>(sysCode, MessageHandler.getMessage(sysCode));
        }
        return ExceptionUtil.parseException(e);
    }
}
