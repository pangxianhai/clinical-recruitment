package com.andy.recruitment.web;

import com.andy.recruitment.web.auth.RecruitmentShiroRealm;
import com.andy.recruitment.web.auth.RecruitmentShiroUserFilter;
import com.soyoung.base.converter.MyHttpMessageConverter;
import com.soyoung.base.datasource.EnableDataSource;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * 招募项目入口
 *
 * @author 庞先海 2020-01-25
 */
@SpringBootApplication
@ComponentScan("com.andy.recruitment")
@EnableDataSource
@EnableScheduling
public class App {

    public static void main(String... args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public MyHttpMessageConverter httpMessageConverter() {
        return new MyHttpMessageConverter(SystemInfo.CHARSET_NAME);
    }

    @Bean
    public RecruitmentShiroRealm recruitmentShiroRealm() {
        return new RecruitmentShiroRealm();
    }

    @Bean
    public SecurityManager securityManager(RecruitmentShiroRealm recruitmentShiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(recruitmentShiroRealm);
        return securityManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("authc", new RecruitmentShiroUserFilter());
        return shiroFilterFactoryBean;
    }

}
