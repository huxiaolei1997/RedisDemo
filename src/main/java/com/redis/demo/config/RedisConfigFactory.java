package com.redis.demo.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 用途描述
 *
 * @author 胡晓磊
 * @company xxx
 * @date 2020年11月18日 21:04 胡晓磊 Exp $
 */
@Configuration
public class RedisConfigFactory {
    @Autowired
    private ApplicationContext applicationContext;
    private static Logger LOGGER = LoggerFactory.getLogger(RedisConfigFactory.class);

    public RedisConfigFactory() {
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisConfigBean redisConfigBean = (RedisConfigBean) this.applicationContext.getBean("redisConfigBean", RedisConfigBean.class);
        JedisConnectionFactory jedisConnectionFactory;
        if (StringUtils.isNotBlank(redisConfigBean.getMode()) && StringUtils.equalsIgnoreCase(redisConfigBean.getMode(), "cluster")) {
            jedisConnectionFactory = new JedisConnectionFactory(redisConfigBean.getRedisClusterConfiguration(), redisConfigBean.getJedisPoolConfig());
        } else if (StringUtils.isNotBlank(redisConfigBean.getMode()) && StringUtils.equalsIgnoreCase(redisConfigBean.getMode(), "sentinel")) {
            jedisConnectionFactory = new JedisConnectionFactory(redisConfigBean.getRedisSentinelConfiguration(), redisConfigBean.getJedisPoolConfig());
            jedisConnectionFactory.setDatabase(Integer.valueOf(redisConfigBean.getDatabase()));
        } else {
            jedisConnectionFactory = new JedisConnectionFactory(redisConfigBean.getJedisPoolConfig());
            jedisConnectionFactory.setHostName(redisConfigBean.getHostName());
            jedisConnectionFactory.setPort(Integer.valueOf(redisConfigBean.getPort()));
            jedisConnectionFactory.setDatabase(Integer.valueOf(redisConfigBean.getDatabase()));
        }

        jedisConnectionFactory.setPassword(redisConfigBean.getPassword());
        jedisConnectionFactory.setTimeout(Integer.valueOf(redisConfigBean.getTimeout()));
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
