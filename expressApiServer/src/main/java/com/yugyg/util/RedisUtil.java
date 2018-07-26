package com.yugyg.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

	private static JedisPool pool = null;
	static {

		pool = new JedisPool(new JedisPoolConfig(), ConfigUtil.getRedisIp(), ConfigUtil.getRedisPort());
	}

	public static Jedis getJedis() {
		return pool.getResource();
	}
	
	public static String  getJedisPara(String key) {
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
	
	public static String  setJedisPara(String key,String value) {
		Jedis jedis = null;
		String para = "";
		try {
			jedis = getJedis();
			para = jedis.set(key,value);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return para;
	}
	
}
