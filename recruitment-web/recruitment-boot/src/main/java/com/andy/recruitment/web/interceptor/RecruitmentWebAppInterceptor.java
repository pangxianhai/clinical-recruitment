package com.andy.recruitment.web.interceptor;

import com.andy.recruitment.web.RecruitmentSystemInfo;
import com.xgimi.boot.base.interceptor.MyHandlerInterceptor;
import com.xgimi.boot.base.logger.log4j.BootLogger;
import com.xgimi.boot.base.logger.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 系统拦截器
 *
 * @author 庞先海 2018-12-14
 */
@Configuration
public class RecruitmentWebAppInterceptor extends WebMvcConfigurerAdapter {

    @Logger
    private BootLogger logger;

    private final HttpMessageConverters httpMessageConverters;

    @Autowired
    public RecruitmentWebAppInterceptor(HttpMessageConverters httpMessageConverters) {
        this.httpMessageConverters = httpMessageConverters;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //公共拦截器
        MyHandlerInterceptor interceptor = new MyHandlerInterceptor() {
            @Override
            protected void recordGLog(HttpServletRequest request, HandlerMethod handlerMethod) {
            }
        };
        interceptor.setLogger(logger);
        interceptor.setHttpMessageConverters(httpMessageConverters);
        interceptor.setCharsetName(RecruitmentSystemInfo.CHARSET_NAME);
        registry.addInterceptor(interceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
