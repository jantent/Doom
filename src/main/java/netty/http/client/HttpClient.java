package netty.http.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;

public class HttpClient {

	public void connect(String host, int port)throws Exception{
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new HttpRequestEncoder());
					ch.pipeline().addLast(new HttpResponseDecoder());
					ch.pipeline().addLast(new HttpClientInboundHandler());
				}
				
			});
			
			ChannelFuture f = b.connect(host, port).sync();
			
			URI uri = new URI("http://www.koal.com");
			String msg = "";
			DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
					uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes()));

			//构建HTTP请求
			request.headers().set(HttpHeaders.Names.HOST, host);
			request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			request.headers().set(HttpHeaders.Names.CONTENT_LENGTH,request.content().readableBytes());
			request.headers().set("messageType", "normal");
			request.headers().set("businessType", "testServerState");
			//发送http请求
			f.channel().write(request);
			f.channel().flush();
			f.channel().closeFuture().sync();
					
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String args[]) throws Exception{
		HttpClient client = new HttpClient();
		client.connect("www.koal.com", 80);
	}
}
