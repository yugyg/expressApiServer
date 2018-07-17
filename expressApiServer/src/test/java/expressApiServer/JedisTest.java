package expressApiServer;

import com.yugyg.util.RedisUtil;

import redis.clients.jedis.Jedis;

public class JedisTest {
	public static void main(String[] args) {

		Jedis jedis = RedisUtil.getJedis();
		try {

			String count = jedis.get("KdniaoCount");
			System.out.println("当前使用次数:" + count);
			jedis.incrBy("KdniaoCount", 1);
			System.out.println("次数增加1");
		} finally {

			if (jedis != null) {
				jedis.close();
			}
		}
	}
}
