package com.redis.demo.test;

import com.redis.demo.config.SpringConfig;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;

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

//        RedisConfigBean redisConfigBean = ((RedisConfigBean) applicationContext.getBean("redisConfigBean"));

        RedissonClient redissonClient = ((RedissonClient) applicationContext.getBean("redissonClient"));

//        Long result = redissonClient.getScript().eval(RScript.Mode.READ_WRITE, "if redis.call('get', KEYS[1]) == ARGV[1] " +
//                "then " +
//                "  return redis.call('del', KEYS[1]) " +
//                "else " +
//                "  return 0 " +
//                "end ", RScript.ReturnType.INTEGER, Collections.singletonList("foo"), "bar");

        // 使用redisson需要注意序列化方式，redisson默认的是jackson序列化方式，如果使用jackson对string进行序列化，存在redis里面会变成 "string"，前后多个两个双引号
        Long result = redissonClient.getScript().eval(RScript.Mode.READ_WRITE, "if redis.call('get', KEYS[1]) == ARGV[1] " +
                "  then " +
                "    return redis.call('del', KEYS[1]) " +
                "  else " +
                "    return 0 " +
                "end", RScript.ReturnType.INTEGER, Collections.singletonList("foo"), "bar");

        if (result == 1) {
//            return true;
        }



//        return false;
//        System.out.println(redisConfigBean.getHostName());
    }
}
