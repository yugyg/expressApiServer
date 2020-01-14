package com.yugyg.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

    private static JedisPool pool = null;

    static {
        System.out.println(ConfigUtil.getRedisPassword());
        if (ConfigUtil.getRedisPassword() != null && !ConfigUtil.getRedisPassword().isEmpty()) {
            pool = new JedisPool(new JedisPoolConfig(), ConfigUtil.getRedisIp(), ConfigUtil.getRedisPort(), 0,
                ConfigUtil.getRedisPassword(),0,ConfigUtil.getRedisUserName());
        } else {
            pool = new JedisPool(new JedisPoolConfig(), ConfigUtil.getRedisIp(), ConfigUtil.getRedisPort());
        }

    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static String getJedisPara(String key) {
        Jedis jedis = null;
        String para = "";
        try {
            jedis = getJedis();
            para = jedis.get(key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return para;
    }

    public static String setJedisPara(String key, String value) {
        Jedis jedis = null;
        String para = "";
        try {
            jedis = getJedis();
            para = jedis.set(key, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return para;
    }

    public static void main(String[] args) {
//        pool = new JedisPool(new JedisPoolConfig(), "prod-cn-rds.redis.rds.aliyuncs.com", 6379, 0, "12yH35JY!vvx","");

        setJedisPara("t_1","test");
        System.out.println(getJedisPara("t_1"));;
    }
}
