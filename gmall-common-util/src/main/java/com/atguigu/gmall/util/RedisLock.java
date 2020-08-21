package com.atguigu.gmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author river
 * @title: RedisLock
 * @projectName gmall0105-2020
 * @description: TODO
 * @date 2020/6/120:23
 */
public class RedisLock {

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private static RedisTemplate redisTemplate;

    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    //锁的路径
    private String lockKey;

    //锁超时时间，防止线程在入锁以后，无限的执行等待
    private int expireMsecs = 60 * 1000;

    //锁等待时间，防止线程饥饿
    private int timeoutMsecs = 10 * 1000;

    private volatile boolean locked = false;

    public RedisLock(RedisTemplate redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs) {
        this(redisTemplate, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs) {
        this(redisTemplate, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    public String getLockKey() {
        return lockKey;
    }

    private String get(final String key) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] data = redisConnection.get(serializer.serialize(key));
                    redisConnection.close();
                    if (data == null) {
                        return null;
                    }
                    return serializer.deserialize(data);
                }
            });
        } catch (Exception e) {
            logger.error("获取redis错误,key:{}", key);
        }
        return obj != null ? obj.toString() : null;
    }

    public boolean setNx(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Boolean success = redisConnection.setNX(serializer.serialize(key), serializer.serialize(value));
                    redisConnection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            logger.error("setNx redis error,key:{}",key);
        }
        return  obj !=null ? (Boolean)obj : false;
    }

    private  String getSet(final  String key , final  String value){
          Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] ret = redisConnection.getSet(serializer.serialize(key), serializer.serialize(value));
                    redisConnection.close();
                    return  serializer.deserialize(ret);
                }
            });
        } catch (Exception e) {
            logger.error("getNX redis error,key:{}",key);
        }
        return  obj !=null ? (String)obj : null;
    }

    public  synchronized  boolean lock() throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >=0){
            long expires = System.currentTimeMillis()+expireMsecs+ 1;
            //锁的到期时间
            String expiresStr = String.valueOf(expires);
            if (this.setNx(lockKey,expiresStr)){
                locked = true;
                return  true;
            }
            //获取redis的时间，通过key来获取时间
            String currentValueStr = this.get(lockKey);
            //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
            if (currentValueStr != null && Long.parseLong(currentValueStr)<System.currentTimeMillis()){
                String oldValueStr = this.getSet(lockKey, expiresStr);
                //获取上一个锁到期时间，并设置现在的锁到期时间，
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)){
                     locked = true;
                     return  true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
              /*
                延迟100 毫秒,  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                使用随机的等待时间可以一定程度上保证公平性
             */
            Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
        }

         return  false;
    }

    //请求锁的释放
    public  synchronized  void  unlock(){
        if (locked){
             redisTemplate.delete(lockKey);
             locked = false;
        }
    }

    public static void main(String[] args) {
        RedisLock lock = new RedisLock(redisTemplate, "333", 10000, 20000);
        try {
            if (lock.lock()){

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}






































































