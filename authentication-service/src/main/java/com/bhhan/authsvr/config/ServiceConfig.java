package com.bhhan.authsvr.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by hbh5274@gmail.com on 2020-07-07
 * Github : http://github.com/bhhan5274
 */

@Component
@Configuration
@Getter
public class ServiceConfig {
    @Value("${signing.key}")
    private String jwtSigningKey = "";
}
