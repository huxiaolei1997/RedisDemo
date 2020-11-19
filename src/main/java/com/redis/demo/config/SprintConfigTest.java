package com.redis.demo.config;

import com.alibaba.fastjson.JSON;
import com.redis.demo.util.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * 用途描述
 *
 * @author 胡晓磊
 * @company xxx
 * @date 2020年11月18日 20:56 胡晓磊 Exp $
 */
public class SprintConfigTest {

    private static Logger LOGGER = LoggerFactory.getLogger(SprintConfigTest.class);


    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

        RedisConfigBean redisConfigBean = ((RedisConfigBean) applicationContext.getBean("redisConfigBean"));
        RedisClient redisClient = ((RedisClient) applicationContext.getBean("redisClient"));

        List<String> list = redisClient.mget(Arrays.asList("key1", "key2"));

        String value1 = redisClient.get("key1");

        List<Object> objects = redisClient.batchGet(Arrays.asList("key1", "key2"));

        LOGGER.info(JSON.toJSONString(objects));
//        System.out.println(list);
    }
}
