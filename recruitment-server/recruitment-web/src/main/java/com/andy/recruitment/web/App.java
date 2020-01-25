package com.andy.recruitment.web;

import com.soyoung.base.datasource.EnableDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
}
