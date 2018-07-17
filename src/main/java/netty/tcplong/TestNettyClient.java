package netty.tcplong;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.tcplong.handler.ClientInitializer;

public class TestNettyClient {

    public static void main(String args[]) throws Exception {
        String serverIp = "127.0.0.1";
        int tcpPort  = 8090;
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ClientInitializer());
        ChannelFuture future = bootstrap.connect(serverIp, tcpPort).sync();
        Channel channel = future.channel();
        if (!future.isSuccess()) {
            future.cause().printStackTrace();
        }

        for (int i=0;i<10;i++){
            String msg = "client msg"+i;
            ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
            channel.writeAndFlush(msg);
            Thread.sleep(1000);
        }
    }
}
