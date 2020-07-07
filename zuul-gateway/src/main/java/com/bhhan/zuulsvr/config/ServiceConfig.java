package com.bhhan.zuulsvr.config;

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
    @Value("${signing.key}")
    private String jwtSigningKey = "";
}
