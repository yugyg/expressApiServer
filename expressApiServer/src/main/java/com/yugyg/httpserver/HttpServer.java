
package com.yugyg.httpserver;



import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.yugyg.util.ConfigUtil;
import com.yugyg.util.Constants;
import com.yugyg.util.RedisUtil;
import com.yugyg.util.Util;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * An HTTP server that sends back the content of the received HTTP request in a
 * pretty plaintext form.
 */
public final class HttpServer {

	private static final Logger logger =LoggerFactory.getLogger(HttpServer.class);
	
	public static void main(String[] args) throws Exception {

		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
		    Config conf = ConfigFactory.load();
		    logger.info("redisIp======{}",conf.getString("redisIp"));
		    logger.info("redisIp======{}",conf.getString("redisPort"));
			//设置快递鸟剩余次数
			String kdniaotimes = RedisUtil.getJedisPara("kdniaotimes");
			if (kdniaotimes == "" || kdniaotimes == null) {
				RedisUtil.setJedisPara("kdniaotimes","3000");
				RedisUtil.setJedisPara("kdniaotimesdate",Util.dateFormat(new Date(), "yyyy-MM-dd"));
			}
			
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO)).childHandler(new HttpServerInitializer());

			int port = ConfigUtil.getIntConfigProperties(Constants.PORT);
			Channel ch = b.bind(port).sync().channel();
            logger.info("http server start  success ,bind port:<{}>",port);
			ch.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
