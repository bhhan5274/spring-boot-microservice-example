package com.bhhan.licenses.config;

import com.bhhan.utils.usercontext.UserContextFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hbh5274@gmail.com on 2020-07-03
 * Github : http://github.com/bhhan5274
 */

@Configuration
public class UserContextConfig {
    @Bean
    public UserContextFilter filter(){
        return new UserContextFilter();
    }
}
