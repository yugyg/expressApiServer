
package com.yugyg.httpserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
/**
 *  参见：HttpObjectAggregator
 * @author sunning
 *
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

 

    public HttpServerInitializer() {}

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
   
        p.addLast("decoder", new HttpRequestDecoder());
        p.addLast("encoder", new HttpResponseEncoder());
        p.addLast("aggregator", new HttpObjectAggregator(1048576));
        
        p.addLast(new HttpServerHandler());
    }
}
