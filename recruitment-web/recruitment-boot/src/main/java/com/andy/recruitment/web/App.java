package com.andy.recruitment.web;

import com.xgimi.boot.base.converter.MyHandlerMethodArgumentResolver;
import com.xgimi.boot.base.converter.MyHttpMessageConverter;
import java.util.List;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
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
@EnableAutoConfiguration
@EnableScheduling
@Configuration
@ComponentScan(basePackages = {"com.andy.recruitment"})
public class App extends WebMvcConfigurerAdapter {

    @Bean
    public RecruitmentSystemInfo recruitmentSystemInfo() {
        return new RecruitmentSystemInfo();
    }

    @Bean
    public MyHttpMessageConverter httpMessageConverter(RecruitmentSystemInfo systemInfo) {
        return new MyHttpMessageConverter(systemInfo.getCharsetName());
    }

    @Bean
    public HttpMessageConverters messageWebMessageConverters(MyHttpMessageConverter httpMessageConverter) {
        return new HttpMessageConverters(httpMessageConverter);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.xgimi.message.business.service.*.mapper");
        return mapperScannerConfigurer;
    }

    @Bean
    public BeanNameAutoProxyCreator txProxy() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setInterceptorNames("txAdvice");
        creator.setBeanNames("*Service", "*ServiceImpl");
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new JettyEmbeddedServletContainerFactory();
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        MyHandlerMethodArgumentResolver argumentResolver = new MyHandlerMethodArgumentResolver();
        argumentResolvers.add(argumentResolver);
    }

    public static void main(String... args) {
        SpringApplication.run(App.class, args);
    }
}
