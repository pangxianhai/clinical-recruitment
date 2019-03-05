package com.andy.recruitment.web.handler;

import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.web.RecruitmentSystemInfo;
import com.xgimi.commons.base.Result;
import com.xgimi.commons.exception.CommonCode;
import com.xgimi.commons.util.JsonUtil;
import com.xgimi.commons.util.encrypt.EncodeUtil;
import com.xgimi.handler.MyExceptionHandler;
import com.xgimi.logger.log4j.Logger;
import com.xgimi.logger.log4j.MyLogger;
import com.xgimi.util.ServletUtil;
import java.io.IOException;
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

    @Override
    @ExceptionHandler(value = Exception.class)
    public Object jsonErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e)
        throws IOException {
        response.setHeader("Content-type", "text/html;charset=" + RecruitmentSystemInfo.CHARSET_NAME);
        Result result;
        Object resultObj = this.parseException(e);
        if (resultObj instanceof Result) {
            result = (Result)resultObj;
        } else {
            result = new Result<>(resultObj);
        }
        if (result.getCode() == BusinessErrorCode.LOGIN_NOT_LOGIN.getCode()) {
            String redirectURL = EncodeUtil.urlEncode(request.getRequestURL().toString());
            if (ServletUtil.isMobile()) {
                String loginUrl = "/user/login?userType=" + UserType.PATIENT.getCode() + "&redirectURL=" + redirectURL;
                response.sendRedirect(loginUrl);
            } else {
                response.sendRedirect("/user/login/manage?redirectURL=" + redirectURL);
            }
            return null;
        }
        //用户传入的参数发生的错误不打印日志
        if (result.getCode() != CommonCode.PARAM_ERROR.getCode()) {
            printErrorLog(result, e);
        }
        if (request.getRequestURI().endsWith(".json")) {
            this.jsonError(result, response);
            return null;
        } else {
            return this.viewError(request, result);
        }
    }

    private void jsonError(Result result, HttpServletResponse response) throws IOException {
        response.getOutputStream().write(JsonUtil.toJson(result).getBytes());
    }

    private ModelAndView viewError(HttpServletRequest request, Result result) {
        ModelAndView mav = new ModelAndView();
        if (ServletUtil.isMobile()) {
            mav.setViewName("500");
        } else {
            mav.setViewName("500-pc");
        }
        request.setAttribute("result", result);
        return mav;
    }
}
