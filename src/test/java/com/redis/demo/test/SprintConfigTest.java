package com.redis.demo.test;

import com.redis.demo.config.RedisConfigBean;
import com.redis.demo.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 用途描述
 *
 * @author 胡晓磊
 * @company xxx
 * @date 2020年11月18日 20:56 胡晓磊 Exp $
 */
public class SprintConfigTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

        RedisConfigBean redisConfigBean = ((RedisConfigBean) applicationContext.getBean("redisConfigBean"));

        System.out.println(redisConfigBean.getHostName());
    }
}
