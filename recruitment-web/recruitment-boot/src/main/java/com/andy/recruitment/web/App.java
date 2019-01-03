package com.andy.recruitment.web;

import com.xgimi.converter.MyHandlerMethodArgumentResolver;
import com.xgimi.converter.MyHttpMessageConverter;
import com.xgimi.datasource.EnableDataSource;
import com.xgimi.executor.EnableExecutor;
import com.xgimi.logger.EnableLog;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
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
@EnableLog
@EnableExecutor
@EnableDataSource(mapperBasePackage = "com.andy.recruitment.*.mapper")
public class App extends SpringBootServletInitializer {


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
    public EmbeddedServletContainerFactory servletContainer() {
        return new TomcatEmbeddedServletContainerFactory();
    }


//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        super.addArgumentResolvers(argumentResolvers);
//        MyHandlerMethodArgumentResolver argumentResolver = new MyHandlerMethodArgumentResolver();
//        argumentResolvers.add(argumentResolver);
//    }

    @Override
    public SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }

    public static void main(String... args) {
        SpringApplication.run(App.class, args);
    }
}
