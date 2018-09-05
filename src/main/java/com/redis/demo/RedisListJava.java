package com.redis.demo;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * RedisListJava
 *
 * @create 2018-09-05 15:35
 *
 * @copyright huxiaolei1997@gmail.com
 */
public class RedisListJava {
    public static void main(String[] args) {
        // 设置IP地址和端口
        Jedis jedis = new Jedis("139.196.140.168", 6379);
        // 输入redis密码
        jedis.auth("hxl2580");
        // 存储数据到列表中
        jedis.lpush("site-list", "Runoob");
        jedis.lpush("site-list", "Google");
        jedis.lpush("site-list", "Taobao");
        // 获取存储的数据
        List<String> list = jedis.lrange("site-list", 0 ,2);
        System.out.println(list.toString());
        // 释放资源
        jedis.close();
    }
}
