package com.redis.demo;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;


/**
 * RedisKeyJava
 *
 * @create 2018-09-05 15:48
 *
 * @copyright huxiaolei1997@gmail.com
 */
public class RedisKeyJava {
    public static void main(String[] args) {
        Jedis jedis = RedisConnection.getRedisConnection();
        Set<String> keys = jedis.keys("*");
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println(key);
        }
        RedisConnection.closeRedisConnection();
    }
}
