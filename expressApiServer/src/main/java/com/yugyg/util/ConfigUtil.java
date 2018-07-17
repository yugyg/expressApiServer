package com.yugyg.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * 读取application.conf 配置文件,application.conf需要在classpath路径下面
 * 
 * @author sunning
 *
 */
public class ConfigUtil {

	private static final String redisIp = "redisIp";
	private static final String redisPort = "redisPort";

	private static Config conf;

	static {
		conf = ConfigFactory.load();
	}

	public static String getStringConfigProperties(String name) {
		return conf.getString(name);
	}

	public static int getIntConfigProperties(String name) {
		return conf.getInt(name);
	}

	public static String getRedisIp() {
		return conf.getString(redisIp);
	}

	public static int getRedisPort() {
		return conf.getInt(redisPort);
	}

}
