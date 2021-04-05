package com.jcj.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import javafx.application.Application;

public class AppClientHello {
    private final  String host;
    private final int port;

    private AppClientHello(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void run() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();//I/O线程池
        try {
            Bootstrap bs = new Bootstrap();
            bs.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host,port)
                    .handler(new ChannelInitializer<SocketChannel>() {//channel初始化配置
                        @Override
                        protected void initChannel(SocketChannel socketChannel)  {
                            socketChannel.pipeline().addLast(new HandleClientHello());
                        }
                    });
            ChannelFuture future = bs.connect().sync();//同步

            future.channel().writeAndFlush(Unpooled.copiedBuffer("Hello World 您好", CharsetUtil.UTF_8));

            future.channel().closeFuture().sync();//阻塞


        }finally {
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception {
        {
            new AppClientHello("localhost",9999).run();
        }
    }

}
