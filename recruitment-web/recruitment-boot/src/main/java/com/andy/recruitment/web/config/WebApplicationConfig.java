package com.andy.recruitment.web.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 系统配置
 *
 * @author 庞先海 2018-12-14
 */
@Configuration
@PropertySource({"classpath:configuration.properties"})
public class WebApplicationConfig extends WebSecurityConfigurerAdapter implements EnvironmentAware {

    private Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getConfig(String key) {
        return getConfig(key, null);
    }

    public String getConfig(String key, String defaultValue) {
        String value = environment.getProperty(key);
        return null == value ? defaultValue : value;
    }

}
