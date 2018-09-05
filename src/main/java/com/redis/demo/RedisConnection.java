package com.redis.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnection {

    private static final Jedis jedis;

    static {
        // 设置IP地址和端口
        jedis = new Jedis("139.196.140.168", 6379);
        // 输入redis密码
        jedis.auth("hxl2580");
    }

    // 单实例的方式
    public void demo1() {
        // 设置IP地址和端口
        Jedis jedis = new Jedis("139.196.140.168", 6379);
        // 输入redis密码
        jedis.auth("hxl2580");
        // 保存数据
        jedis.set("name", "imooc");
        // 获取数据
        String name = jedis.get("name");
        System.out.println(name);
        // 释放资源
        jedis.close();
    }

    // 连接池的方式
    public void demo2() {
        // 获取连接池的配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 设置最大的连接数
        jedisPoolConfig.setMaxTotal(30);
        // 设置最大空闲连接数
        jedisPoolConfig.setMaxIdle(10);

        // 获取连接池
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "139.196.140.168", 6379);
        // 获得核心对象
        Jedis jedis = null;
        try {
            // 通过连接池获取连接
            jedis = jedisPool.getResource();
            // 输入redis密码
            jedis.auth("hxl2580");
            // 设置数据
            jedis.set("name", "张三");
            // 获取数据
            String name = jedis.get("name");
            System.out.println(name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (jedis != null) {
                jedis.close();
            }
            if (jedisPool != null) {
                jedisPool.close();
            }
        }
    }

    public static Jedis getRedisConnection() {
        return jedis;
    }

    public static void closeRedisConnection() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
