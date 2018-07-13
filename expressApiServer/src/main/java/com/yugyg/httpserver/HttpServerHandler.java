
package com.yugyg.httpserver;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSONObject;
import com.yugyg.ExpressApiCenter;
import com.yugyg.express.KdniaoExpressApi;
import com.yugyg.message.ExpressRequest;
import com.yugyg.message.ExpressResponse;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.AsciiString;

/**
 * 参见：https://my.oschina.net/xinxingegeya/blog/295408
 * 
 * @author sunning
 *
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

	private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
	private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
	private static final AsciiString CONNECTION = AsciiString.cached("Connection");
	private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}



	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (msg instanceof FullHttpRequest) {
			FullHttpRequest req = (FullHttpRequest) msg;

			ByteBuf cttByteBuf = req.content();

			byte[] cttBytes = ByteBufUtil.getBytes(cttByteBuf);

			// 把字节序按照GBK格式 转换成字符串
			String postBody = new String(cttBytes, Charset.forName("utf-8"));

			ExpressRequest request = (ExpressRequest) JSONObject.parseObject(postBody, ExpressRequest.class);
			//执行策略
			ExpressResponse response = ExpressApiCenter.traceExpress(request);
			
			writeAndClose(ctx, JSONObject.toJSONString(response));
		}
	}

	/**
	 * 写入消息并关闭
	 * 
	 * @param ctx
	 * @param msg
	 */
	public static void writeAndClose(ChannelHandlerContext ctx, Object msg) {
		// 把对象Object 转换成json字符串
		String jsonString = JSONObject.toJSONString(msg);
		// 把json字符串 转换成二进制字节码
		byte[] content = jsonString.getBytes(Charset.forName("utf-8"));
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content));
		response.headers().set(CONTENT_TYPE, "application/json");
		response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
		ctx.write(response).addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
