package com.andy.recruitment.web.interceptor;

import com.andy.recruitment.web.RecruitmentSystemInfo;
import com.xgimi.interceptor.MyHandlerInterceptor;
import com.xgimi.logger.log4j.Logger;
import com.xgimi.logger.log4j.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Configuration;
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
    private MyLogger logger;

    private final HttpMessageConverters httpMessageConverters;

    @Autowired
    public RecruitmentWebAppInterceptor(HttpMessageConverters httpMessageConverters) {
        this.httpMessageConverters = httpMessageConverters;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //公共拦截器
        MyHandlerInterceptor interceptor = new MyHandlerInterceptor();
        interceptor.setLogger(logger);
        interceptor.setHttpMessageConverters(httpMessageConverters);
        interceptor.setCharsetName(RecruitmentSystemInfo.CHARSET_NAME);
        registry.addInterceptor(interceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
