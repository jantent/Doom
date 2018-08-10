package netty.https;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.apache.coyote.http11.Constants.CONNECTION;
import static org.apache.http.HttpHeaders.CONTENT_LENGTH;

public class HttpsHandler extends SimpleChannelInboundHandler<FullHttpRequest>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		System.out.println("收到http请求为："+msg.headers().toString());
		HttpResponseStatus respStatus = new HttpResponseStatus(200, "OK");

		String rspStr = "responsss";
		ByteBuf byteBuf = Unpooled.wrappedBuffer(rspStr.getBytes());
		writeResponse(msg,respStatus,ctx,byteBuf);
	}

	/**
	 * 从请求中获取 content bytes
	 *
	 * @param request http请求
	 * @return 请求中的content bytes，如果请求中content为空，则返回null
	 */
	private byte[] getContentBytes(FullHttpRequest request) {
		// 请求的content部分的bytes
		byte[] reqBytes = null;
		// 请求的content部分 ByteBuf
		ByteBuf bufReq = request.content();
		if (bufReq.readableBytes() > 0) {
			// 如果有内容
			int length = bufReq.readableBytes();
			reqBytes = new byte[length];
			if ((length != 0) && (bufReq.isReadable()))
				bufReq.getBytes(0, reqBytes, 0, length);
		}

		return reqBytes;
	}

	/**
	 * 返回消息
	 *
	 * @param request
	 * @param respStatus
	 * @param ctx
	 * @param bufRep
	 */
	private void writeResponse(FullHttpRequest request, HttpResponseStatus respStatus, ChannelHandlerContext ctx,
							   ByteBuf bufRep) {
		if (request == null || bufRep == null) {
			// 该消息不需要响应
			ctx.close();
		} else {
			boolean keepAlive = HttpUtil.isKeepAlive(request);
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, respStatus, bufRep);
			if (keepAlive) {
				// Add 'Content-Length' header only for a keep-alive connection.
				response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
				// Add keep alive header
				response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
				ctx.write(response);
				ctx.flush();
			} else {
				ctx.write(response).addListener(ChannelFutureListener.CLOSE);
				ctx.flush();
			}
		}
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("一个从CP已连接,IP为：" + ctx.channel().remoteAddress().toString());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("一个从CP断开连接,IP为：" + ctx.channel().remoteAddress().toString());
	}
}
