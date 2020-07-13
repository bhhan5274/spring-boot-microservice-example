package com.bhhan.licenses;

import com.bhhan.licenses.config.ServiceConfig;
import com.bhhan.licenses.event.CustomChannel;
import com.bhhan.utils.usercontext.UserContextInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hbh5274@gmail.com on 2020-07-01
 * Github : http://github.com/bhhan5274
 */

@EnableResourceServer
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableBinding(CustomChannel.class)
@RequiredArgsConstructor
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final ServiceConfig serviceConfig;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors()
        .add(new UserContextInterceptor());

        return restTemplate;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        final JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        final RedisStandaloneConfiguration standaloneConfiguration = jedisConnectionFactory.getStandaloneConfiguration();
        standaloneConfiguration.setHostName(serviceConfig.getRedisServer());
        standaloneConfiguration.setPort(Integer.parseInt(serviceConfig.getRedisPort()));

        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
