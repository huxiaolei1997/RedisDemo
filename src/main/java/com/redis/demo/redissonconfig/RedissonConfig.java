package com.redis.demo.redissonconfig;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 用途描述
 *
 * @author 胡晓磊
 * @company xxx
 * @date 2020年11月27日 21:29 胡晓磊 Exp $
 */
@Component
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        String serverHost = "localhost:6379";
//        String serverHost = "172.30.43.211:36379";
//        int dbIndex = 12;
        int dbIndex = 0;
        String redisProtocol = "redis://";
        Config config = new Config();
        config.useSingleServer()
                .setPassword("123456")
                .setAddress(redisProtocol + serverHost)
                .setRetryAttempts(300).setRetryInterval(2000)
                .setDatabase(dbIndex);

        return Redisson.create(config);
    }
}
