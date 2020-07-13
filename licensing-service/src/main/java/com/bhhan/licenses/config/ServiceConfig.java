package com.bhhan.licenses.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by hbh5274@gmail.com on 2020-07-01
 * Github : http://github.com/bhhan5274
 */

@Component
@Getter
public class ServiceConfig {
    @Value("${example.property}")
    private String exampleProperty;

    @Value("${signing.key}")
    private String jwtSigningKey = "";

    @Value("${redis.server}")
    private String redisServer = "";

    @Value("${redis.port}")
    private String redisPort = "";
}
