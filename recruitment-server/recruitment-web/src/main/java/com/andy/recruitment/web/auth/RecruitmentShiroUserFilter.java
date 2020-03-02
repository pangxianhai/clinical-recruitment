package com.andy.recruitment.web.auth;

import com.andy.spring.base.Result;
import com.andy.spring.exception.CommonCode;
import com.andy.spring.message.MessageHandler;
import com.andy.spring.util.JsonUtil;
import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.filter.authc.UserFilter;

/**
 * 功能描述
 *
 * @author 庞先海 2020-01-28
 */
public class RecruitmentShiroUserFilter extends UserFilter {

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        Result<?> result = new Result<>(CommonCode.NOT_LOGIN.getCode(),
            MessageHandler.getMessage(CommonCode.NOT_LOGIN.getCode()));
        response.getWriter().write(JsonUtil.toJson(result));
    }
}
