package com.redis.demo.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用途描述
 *
 * @author 胡晓磊
 * @company xxx
 * @date 2020年11月18日 21:36 胡晓磊 Exp $
 */
@Component
public class RedisClient {
    @Resource
    private RedisTemplate<String, ?> redisTemplate;

    public RedisClient() {
    }

    public boolean set(final String key, final String value, final long expire) {
        boolean result = (Boolean) this.redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisClient.this.redisTemplate.getStringSerializer();
                connection.setEx(serializer.serialize(key), expire, serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    public String get(final String key) {
        String result = (String) this.redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisClient.this.redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return (String) serializer.deserialize(value);
            }
        });
        return result;
    }

    public void delete(String... redisKeyStr) {
        for (int i = 0; i < redisKeyStr.length; ++i) {
            this.delete(redisKeyStr[i]);
        }

    }

    public boolean delete(final String key) {
        boolean result = (Boolean) this.redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisClient.this.redisTemplate.getStringSerializer();
                connection.del(new byte[][]{serializer.serialize(key)});
                return true;
            }
        });
        return result;
    }


    /**
     * 批量获取key 值
     *
     * @param keys
     * @return
     */
    public List<String> mget(List<String> keys) {
//        List<String> result = (List<String>) this.redisTemplate.execute(new RedisCallback<List<String>>() {
//            @Override
//            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = RedisClient.this.redisTemplate.getStringSerializer();
//                List<byte[]> bytes = new ArrayList<>();
//                for (int i = 0, size = keys.size(); i < size; i++) {
//                    bytes.add(serializer.serialize(keys.get(i)));
//                }
//                List<byte[]> values = connection.mGet(bytes.toArray(new byte[keys.size()][]));
//                List<String> valueList = new ArrayList<>();
//                for (byte[] value : values) {
//                    valueList.add(serializer.deserialize(value));
//                }
//                return valueList;
//            }
//        });
//        return result;

        return ((List<String>) redisTemplate.opsForValue().multiGet(keys));
    }
//    public boolean deleteByPrex(final String prex) {
//        boolean result = (Boolean)this.redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = RedisClient.this.redisTemplate.getStringSerializer();
//                Set<byte[]> byteKeySet = connection.keys(serializer.serialize(prex));
//                if (CollectionUtil.isEmpty(byteKeySet)) {
//                    return false;
//                } else {
//                    Iterator var4 = byteKeySet.iterator();
//
//                    while(var4.hasNext()) {
//                        byte[] byteKey = (byte[])var4.next();
//                        connection.del(new byte[][]{byteKey});
//                    }
//
//                    return true;
//                }
//            }
//        });
//        return result;
//    }

    public void deleteByPrex(String... redisPrexStr) {
        for (int i = 0; i < redisPrexStr.length; ++i) {
            this.deleteByPrex(redisPrexStr[i]);
        }

    }

//    public boolean tryLock(String lockKey, String requestId, long expire) {
//        Boolean result = (Boolean)this.redisTemplate.execute((connection) -> {
//            JedisCommands commands = (JedisCommands)connection.getNativeConnection();
//            return StringUtils.isNotBlank(commands.set(lockKey, requestId, "NX", "PX", expire));
//        });
//        return result;
//    }

//    public boolean unLock(String lockKey, String requestId) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
//        sb.append("then ");
//        sb.append("    return redis.call(\"del\",KEYS[1]) ");
//        sb.append("else ");
//        sb.append("    return 0 ");
//        sb.append("end ");
//        String unLockLua = sb.toString();
//        List<String> keys = new ArrayList();
//        keys.add(lockKey);
//        List<String> args = new ArrayList();
//        args.add(requestId);
//        Long result = (Long)this.redisTemplate.execute((connection) -> {
//            Object nativeConnection = connection.getNativeConnection();
//            if (nativeConnection instanceof JedisCluster) {
//                return (Long)((JedisCluster)nativeConnection).eval(unLockLua, keys, args);
//            } else {
//                return nativeConnection instanceof Jedis ? (Long)((Jedis)nativeConnection).eval(unLockLua, keys, args) : 0L;
//            }
//        });
//        return result != null && result > 0L;
//    }

    public long incr(final String key) {
        return (Long) this.redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisClient.this.redisTemplate.getStringSerializer();
                return connection.incr(serializer.serialize(key));
            }
        });
    }

    public Double incrBy(final String key, final Double incr) {
        Double result = (Double) this.redisTemplate.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisClient.this.redisTemplate.getStringSerializer();
                return connection.incrBy(serializer.serialize(key), incr);
            }
        });
        return result;
    }

    public Long incrBy(final String key, final long incr) {
        return (Long) this.redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = RedisClient.this.redisTemplate.getStringSerializer();
                return connection.incrBy(serializer.serialize(key), incr);
            }
        });
    }

    public boolean expire(String key, long expire) {
        return this.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

//    public <T> boolean setList(String key, List<T> list, long expire) {
//        String value = JSONUtil.toJson(list);
//        return this.set(key, value, expire);
//    }
//
//    public <T> List<T> getList(String key, Class<T> clz) {
//        String json = this.get(key);
//        if (json != null) {
//            List<T> list = JSONUtil.toList(json, clz);
//            return list;
//        } else {
//            return null;
//        }
//    }
//
//    public <T> boolean setBean(String key, T t, long expire) {
//        String value = JSONUtil.toJson(t);
//        return this.set(key, value, expire);
//    }
//
//    public <T> T getBean(String key, Class<T> clz) {
//        String json = this.get(key);
//        if (json != null) {
//            T t = JSONUtil.toBean(json, clz);
//            return t;
//        } else {
//            return null;
//        }
//    }
//
//    public long lpush(final String key, Object obj) {
//        final String value = JSONUtil.toJson(obj);
//        long result = (Long)this.redisTemplate.execute(new RedisCallback<Long>() {
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = RedisClient.this.redisTemplate.getStringSerializer();
//                long count = connection.lPush(serializer.serialize(key), new byte[][]{serializer.serialize(value)});
//                return count;
//            }
//        });
//        return result;
//    }
//
//    public long rpush(final String key, Object obj) {
//        final String value = JSONUtil.toJson(obj);
//        long result = (Long)this.redisTemplate.execute(new RedisCallback<Long>() {
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = RedisClient.this.redisTemplate.getStringSerializer();
//                long count = connection.rPush(serializer.serialize(key), new byte[][]{serializer.serialize(value)});
//                return count;
//            }
//        });
//        return result;
//    }
//
//    public String lpop(final String key) {
//        String result = (String)this.redisTemplate.execute(new RedisCallback<String>() {
//            public String doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer<String> serializer = RedisClient.this.redisTemplate.getStringSerializer();
//                byte[] res = connection.lPop(serializer.serialize(key));
//                return (String)serializer.deserialize(res);
//            }
//        });
//        return result;
//    }
}
