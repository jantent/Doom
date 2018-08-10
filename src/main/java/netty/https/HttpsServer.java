package netty.https;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import netty.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLEngine;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * 证书同步server
 *
 * @author tangj
 */
public class HttpsServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpsServer.class);

    private Thread thread = null;

    private int portNumber;

    private String host;

    public HttpsServer(int portNumber, String host) {
        this.portNumber = portNumber;
        this.host = host;
    }

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;


    public static void main(String args[]) throws Exception{
        new HttpsServer(8780,"*").run();
    }

    /**
     * 通道的PipeLine
     */
    private class CertSyncServerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            SSLEngine sslEngine = SSLContextFac.createSSlContent().createSSLEngine();
            sslEngine.setUseClientMode(false);
            pipeline.addLast(new SslHandler(sslEngine));
            // A combination of {@link HttpRequestDecoder} and {@link HttpResponseEncoder}
            // 拼接一个完整的http请求
            pipeline.addLast("codec", new HttpServerCodec());
            // 大容量写响应数据
            pipeline.addLast("writeHandler", new ChunkedWriteHandler());
            // aggregates HttpMessage to httpFullRequest or httpFullResponse
            // 参数the maximum length of the aggregated content in bytes
            pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024 * 5));
            pipeline.addLast("handler",new HttpsHandler());
        }
    }

    public void run() throws Exception {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        InetSocketAddress addr = null;

        if ("*".equalsIgnoreCase(host) || "0.0.0.0".equalsIgnoreCase(host)) {
            addr = new InetSocketAddress(portNumber);
        } else {
            addr = new InetSocketAddress(InetAddress.getByName(host),
                    portNumber);
        }
        final InetSocketAddress addr1 = addr;
        final String adInfo = host + ":" + portNumber;


        thread = new Thread() {
            @Override
            public void run() {
                ServerBootstrap boot = new ServerBootstrap();
                boot.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new CertSyncServerInitializer())
                        .option(ChannelOption.SO_REUSEADDR, true)
                        .option(EpollChannelOption.SO_REUSEPORT, true)
                        .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
                try {
                    ChannelFuture f = boot.bind(addr1).sync();
                    LOGGER.info(" Service start success!{" + adInfo + "}");
                    // Wait until the server socket is closed.
                    f.channel().closeFuture().sync();
                } catch (Exception e) {
                    LOGGER.error(" Service start fail!{" + adInfo + "}");
                    LOGGER.error(e.getMessage(), e);
                } finally {
                    bossGroup.shutdownGracefully();
                    workerGroup.shutdownGracefully();
                }
            }
        };

        thread.start();
    }

    public void stop() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            thread.interrupt();
        }
    }
}
