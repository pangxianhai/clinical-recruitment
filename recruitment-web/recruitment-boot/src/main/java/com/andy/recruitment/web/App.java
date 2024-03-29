package com.andy.recruitment.web;

import com.xgimi.converter.MyHandlerMethodArgumentResolver;
import com.xgimi.converter.MyHttpMessageConverter;
import com.xgimi.datasource.EnableDataSource;
import com.xgimi.executor.EnableExecutor;
import com.xgimi.logger.EnableLog;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 系统主方法
 *
 * @author 庞先海 2018-12-14
 */
@EnableScheduling
@Configuration
@ComponentScan(basePackages = {"com.andy.recruitment"})
@EnableLog
@EnableExecutor
@EnableDataSource(autoMapperPackage = "com.andy.recruitment.*.mapper")
@SpringBootApplication
public class App extends WebMvcConfigurerAdapter {

    private static ApplicationContext context;

    @Bean
    public RecruitmentSystemInfo recruitmentSystemInfo() {
        return new RecruitmentSystemInfo();
    }

    @Bean
    public MyHttpMessageConverter httpMessageConverter(RecruitmentSystemInfo systemInfo) {
        return new MyHttpMessageConverter(systemInfo.getCharsetName());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        MyHandlerMethodArgumentResolver argumentResolver = new MyHandlerMethodArgumentResolver();
        argumentResolvers.add(argumentResolver);
    }

    public static void main(String... args) {
        context = SpringApplication.run(App.class, args);
    }

    public static ApplicationContext getContext() {
        return context;
    }
}
