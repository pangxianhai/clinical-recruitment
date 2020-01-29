package com.andy.recruitment.web.auth;

import com.soyoung.base.base.Result;
import com.soyoung.base.exception.CommonCode;
import com.soyoung.base.message.MessageHandler;
import com.soyoung.base.util.JsonUtil;
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
