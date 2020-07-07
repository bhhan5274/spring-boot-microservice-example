package com.bhhan.licenses.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Created by hbh5274@gmail.com on 2020-07-06
 * Github : http://github.com/bhhan5274
 */

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/v1/organizations/**")
            .hasRole("ADMIN")
            .anyRequest()
            .authenticated();
    }
}
