package com.redis.demo.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * 用途描述
 *
 * @author 胡晓磊
 * @company xxx
 * @date 2020年11月18日 21:02 胡晓磊 Exp $
 */
@Data
@Configuration
public class RedisConfigBean {
    private String mode = "single";
    private String hostName;
    private String port;
    private String password;
    private String database;
    private String timeout;
    private String clusterNodes;
    private String maxRedirectsac;
    private String master;
    private String sentinelNodes;
    private String maxIdle;
    private String maxTotal;
    private String minIdle;
    private String maxWaitMillis;
    private String testOnBorrow;
    private String testOnReturn;
    private String testWhileIdle;
    private String timeBetweenEvictionRunsMillis;
    private String numTestsPerEvictionRun;
    private String minEvictableIdleTimeMillis;
    private JedisPoolConfig jedisPoolConfig;
    private RedisClusterConfiguration redisClusterConfiguration;
    private RedisSentinelConfiguration redisSentinelConfiguration;

    public RedisConfigBean() {
    }

    public JedisPoolConfig getJedisPoolConfig() {
        this.jedisPoolConfig = new JedisPoolConfig();
        if (StringUtils.isNotBlank(this.maxIdle)) {
            this.jedisPoolConfig.setMaxIdle(Integer.valueOf(this.maxIdle));
        }

        if (StringUtils.isNotBlank(this.minIdle)) {
            this.jedisPoolConfig.setMinIdle(Integer.valueOf(this.minIdle));
        }

        if (StringUtils.isNotBlank(this.maxWaitMillis)) {
            this.jedisPoolConfig.setMaxWaitMillis((long) Integer.valueOf(this.maxWaitMillis));
        }

        if (StringUtils.isNotBlank(this.testOnBorrow)) {
            this.jedisPoolConfig.setTestOnBorrow(Boolean.valueOf(this.testOnBorrow));
        }

        if (StringUtils.isNotBlank(this.testWhileIdle)) {
            this.jedisPoolConfig.setTestWhileIdle(Boolean.valueOf(this.testWhileIdle));
        }

        if (StringUtils.isNotBlank(this.testOnReturn)) {
            this.jedisPoolConfig.setTestOnReturn(Boolean.valueOf(this.testOnReturn));
        }

        if (StringUtils.isNotBlank(this.timeBetweenEvictionRunsMillis)) {
            this.jedisPoolConfig.setTimeBetweenEvictionRunsMillis(Long.valueOf(this.timeBetweenEvictionRunsMillis));
        }

        if (StringUtils.isNotBlank(this.numTestsPerEvictionRun)) {
            this.jedisPoolConfig.setNumTestsPerEvictionRun(Integer.valueOf(this.numTestsPerEvictionRun));
        }

        if (StringUtils.isNotBlank(this.minEvictableIdleTimeMillis)) {
            this.jedisPoolConfig.setMinEvictableIdleTimeMillis(Long.valueOf(this.minEvictableIdleTimeMillis));
        }

        return this.jedisPoolConfig;
    }

    public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
        this.jedisPoolConfig = jedisPoolConfig;
    }

    public RedisClusterConfiguration getRedisClusterConfiguration() {
        if (!StringUtils.isBlank(this.mode) && StringUtils.equalsIgnoreCase(this.mode, "cluster")) {
            if (this.redisClusterConfiguration == null) {
                this.redisClusterConfiguration = new RedisClusterConfiguration();
            }

            String[] serverArray = this.clusterNodes.split(",");
            Set<RedisNode> nodes = new HashSet();
            String[] var3 = serverArray;
            int var4 = serverArray.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String ipPort = var3[var5];
                String[] ipAndPort = ipPort.split(":");
                nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
            }

            this.redisClusterConfiguration.setClusterNodes(nodes);
            this.redisClusterConfiguration.setMaxRedirects(Integer.valueOf(this.maxRedirectsac));
            return this.redisClusterConfiguration;
        } else {
            return null;
        }
    }

    public void setRedisClusterConfiguration(RedisClusterConfiguration redisClusterConfiguration) {
        this.redisClusterConfiguration = redisClusterConfiguration;
    }

    public RedisSentinelConfiguration getRedisSentinelConfiguration() {
        if (!StringUtils.isBlank(this.mode) && StringUtils.equalsIgnoreCase(this.mode, "sentinel")) {
            if (this.redisSentinelConfiguration == null) {
                this.redisSentinelConfiguration = new RedisSentinelConfiguration();
            }

            String[] serverArray = this.sentinelNodes.split(",");
            Set<RedisNode> nodes = new HashSet();
            String[] var3 = serverArray;
            int var4 = serverArray.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String ipPort = var3[var5];
                String[] ipAndPort = ipPort.split(":");
                nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
            }

            this.redisSentinelConfiguration.master(this.master);
            this.redisSentinelConfiguration.setSentinels(nodes);
            return this.redisSentinelConfiguration;
        } else {
            return null;
        }
    }

    public void setRedisSentinelConfiguration(RedisSentinelConfiguration redisSentinelConfiguration) {
        this.redisSentinelConfiguration = redisSentinelConfiguration;
    }

}
