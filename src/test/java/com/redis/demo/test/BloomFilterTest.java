package com.redis.demo.test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.redis.demo.config.SpringConfig;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
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
public class BloomFilterTest {

    private static final int size = 1000000;

    /**
     * 误判率
     *
     * @param args
     */
    private static final double fpp = 0.01;

    private static final BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

    public static void main(String[] args) {

        for (int i = 0; i < 1000000; i++) {
            bloomFilter.put(i);
        }

        int count = 0;
        for (int i = 1000000; i < 2000000; i++) {
            if (bloomFilter.mightContain(i)) {
                count++;
            }
        }
        System.out.println("总共的误判数：" + count);

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

//        RedisConfigBean redisConfigBean = ((RedisConfigBean) applicationContext.getBean("redisConfigBean"));

        RedissonClient redissonClient = ((RedissonClient) applicationContext.getBean("redissonClient"));

        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("bloom-filter", StringCodec.INSTANCE);


        bloomFilter.tryInit(1000, 0.03);

        for (int i = 0; i < 1000; i++) {
            bloomFilter.add("测试bloomFilter" + i);
        }

        bloomFilter.add("10086");
        System.out.println("测试bloomFilter 1是否存在："+bloomFilter.contains("测试bloomFilter" + 1));
        System.out.println("测试 10086 是否存在："+bloomFilter.contains("10086"));
        System.out.println("测试 10087 是否存在："+bloomFilter.contains("10087"));
        System.out.println("海贼王是否存在："+bloomFilter.contains("海贼王"));
        System.out.println("预计插入数量："+bloomFilter.getExpectedInsertions());
        System.out.println("容错率："+bloomFilter.getFalseProbability());
        System.out.println("hash函数的个数："+bloomFilter.getHashIterations());
        System.out.println("插入对象的个数："+bloomFilter.count());
//
////        Long result = redissonClient.getScript().eval(RScript.Mode.READ_WRITE, "if redis.call('get', KEYS[1]) == ARGV[1] " +
////                "then " +
////                "  return redis.call('del', KEYS[1]) " +
////                "else " +
////                "  return 0 " +
////                "end ", RScript.ReturnType.INTEGER, Collections.singletonList("foo"), "bar");
//
//        // 使用redisson需要注意序列化方式，redisson默认的是jackson序列化方式，如果使用jackson对string进行序列化，存在redis里面会变成 "string"，前后多个两个双引号
//        Long result = redissonClient.getScript().eval(RScript.Mode.READ_WRITE, "if redis.call('get', KEYS[1]) == ARGV[1] " +
//                "  then " +
//                "    return redis.call('del', KEYS[1]) " +
//                "  else " +
//                "    return 0 " +
//                "end", RScript.ReturnType.INTEGER, Collections.singletonList("foo"), "bar");
//
//        if (result == 1) {
////            return true;
//        }



//        return false;
//        System.out.println(redisConfigBean.getHostName());
    }
}
