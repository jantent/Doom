package netty.httpfile.anotherfile;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;


public class HttpFileServer {

private static final String DEFAULT_URL = "/src/";
    
    public void run(final int port, final String url)throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                	//它负责把字节解码成Http请求。
                    ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                    //它负责把多个HttpMessage组装成一个完整的Http请求或者响应
                    ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                    //当Server处理完消息后，需要向Client发送响应。那么需要把响应编码成字节，再发送出去
                    ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                    //该通道处理器主要是为了处理大文件传输的情形。大文件传输时，需要复杂的状态管理，而ChunkedWriteHandler实现这个功能。
                    ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                    ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                }
            });
            
            ChannelFuture f = b.bind("localhost", port).sync();
            System.out.println("HTTP 文件服务器启动, 地址是： " + "http://localhost:" + port + url);
            f.channel().closeFuture().sync();
            
        }finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    
    public static void main(String[] args)throws Exception {
        int port = 8080;
        if(args.length > 0)
        {
            try{
                port = Integer.parseInt(args[0]);
            }catch(NumberFormatException e){
                port = 8080;
            }
        }
        
        String url = DEFAULT_URL;
        if(args.length > 1)
            url = args[1];
        new HttpFileServer().run(port, url);
    }

}
