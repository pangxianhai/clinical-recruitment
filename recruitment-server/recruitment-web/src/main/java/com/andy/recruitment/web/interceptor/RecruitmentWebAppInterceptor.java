package com.andy.recruitment.web.interceptor;


import com.andy.recruitment.web.SystemInfo;
import com.soyoung.base.interceptor.MyWebHandlerInterceptor;
import com.soyoung.base.util.encrypt.EncodeUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 系统拦截器
 *
 * @author 庞先海 2018-12-14
 */
@Configuration
public class RecruitmentWebAppInterceptor implements WebMvcConfigurer {

    private final HttpMessageConverters httpMessageConverters;


    @Autowired
    public RecruitmentWebAppInterceptor(HttpMessageConverters httpMessageConverters) {
        this.httpMessageConverters = httpMessageConverters;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //公共拦截器
        MyRecruitmentWebAppInterceptor interceptor = new MyRecruitmentWebAppInterceptor();
        interceptor.setHttpMessageConverters(httpMessageConverters);
        interceptor.setCharsetName(SystemInfo.CHARSET_NAME);
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }

    public static class MyRecruitmentWebAppInterceptor extends MyWebHandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
            super.preHandle(request, response, handler);
            String token = request.getHeader("token");
            String userName = request.getHeader("userName");
            if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userName)) {
                return true;
            }
            userName = EncodeUtil.urlDecode(userName);
            token = EncodeUtil.urlDecode(token);
            UsernamePasswordToken passwordToken = new UsernamePasswordToken(userName, token);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(passwordToken);
            return true;
        }
    }
}
