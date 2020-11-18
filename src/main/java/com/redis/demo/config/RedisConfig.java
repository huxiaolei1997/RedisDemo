package com.redis.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Will.Wu 2018/5/31
 */
@Data
@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig {

    @Value("${spring.redis.maxIdle}")
    private String maxIdle;
    @Value("${spring.redis.maxTotal}")
    private String maxTotal;
    @Value("${spring.redis.minIdle}")
    private String minIdle;
    @Value("${spring.redis.maxWaitMillis}")
    private String maxWaitMillis;
    @Value("${spring.redis.testOnBorrow}")
    private String testOnBorrow;
    @Value("${spring.redis.testOnReturn}")
    private String testOnReturn;
    @Value("${spring.redis.testWhileIdle}")
    private String testWhileIdle;
    @Value("${spring.redis.timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;
    @Value("${spring.redis.numTestsPerEvictionRun}")
    private String numTestsPerEvictionRun;
    @Value("${spring.redis.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;

    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private String database;
    @Value("${spring.redis.timeout}")
    private String timeout;

    @Value("${spring.redis.mode}")
    private String mode;

    @Value("${spring.redis.hostName}")
    private String hostName;

    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public RedisConfigBean redisConfigBean() {

        RedisConfigBean redisConfigBean = new RedisConfigBean();

        redisConfigBean.setMode(mode);
        redisConfigBean.setMaxIdle(maxIdle);
        redisConfigBean.setMaxTotal(maxTotal);
        redisConfigBean.setMinIdle(minIdle);
        redisConfigBean.setMaxWaitMillis(maxWaitMillis);
        redisConfigBean.setTestOnBorrow(testOnBorrow);
        redisConfigBean.setTestOnReturn(testOnReturn);
        redisConfigBean.setTestWhileIdle(testWhileIdle);
        redisConfigBean.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        redisConfigBean.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        redisConfigBean.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);


        redisConfigBean.setPassword(password);
        redisConfigBean.setDatabase(database);
        redisConfigBean.setTimeout(timeout);
        redisConfigBean.setHostName(hostName);
        redisConfigBean.setPort(port);
        return redisConfigBean;
    }

}
