package com.andy.recruitment.web.handler;

import com.andy.recruitment.web.SystemInfo;
import com.soyoung.base.base.Result;
import com.soyoung.base.context.ServletContext;
import com.soyoung.base.exception.CommonCode;
import com.soyoung.base.exception.ExceptionUtil;
import com.soyoung.base.handler.MyExceptionHandler;
import com.soyoung.base.message.MessageHandler;
import com.soyoung.base.util.ServletUtil;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * 系统异常拦截器
 *
 * @author 庞先海 2018-12-14
 */
@ControllerAdvice
@Slf4j
public class RecruitmentExceptionHandler extends MyExceptionHandler {

    @PostConstruct
    public void init() {
        super.setCharsetName(SystemInfo.CHARSET_NAME);
    }

    @Override
    protected Object parseException(Exception e) {
        if (e instanceof UnauthenticatedException || e instanceof UnauthorizedException) {
            int sysCode = CommonCode.UNAUTHORIZED.getCode();
            return new Result<>(sysCode, MessageHandler.getMessage(sysCode));
        }
        return ExceptionUtil.parseException(e);
    }

    @Override
    protected void printErrorLog(Result<?> result, Exception e) {
        String errorMessage = "";
        HttpServletRequest request = ServletContext.getRequest();
        if (request != null) {
            errorMessage += request.getMethod() + " address:" + request.getRequestURI() + ". params:"
                            + ServletUtil.parseParam(request);
        }
        if (null == result) {
            log.error(errorMessage, e);
        } else {
            errorMessage += ". error code:" + result.getCode() + ". error message:" + result.getMsg();
            if (CommonCode.SYS_ERROR.getCode() == result.getCode()) {
                log.error(errorMessage, e);
            } else {
                log.warn(errorMessage);
            }
        }
    }

}
