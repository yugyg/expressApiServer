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

	
}
