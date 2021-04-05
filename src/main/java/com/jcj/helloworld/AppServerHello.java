package com.jcj.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class AppServerHello {

    private final int port;

    private AppServerHello( int port) {

        this.port = port;
    }

    private void run() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();//I/O线程池
        try {
            ServerBootstrap bs = new ServerBootstrap();//**
            bs.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                @Override
                                protected void initChannel(SocketChannel socketChannel)  {
                                    socketChannel.pipeline().addLast(new HandleServerHello());
                                }
                            });
            ChannelFuture future = bs.bind().sync();//同步
            System.out.println("在"+ future.channel().localAddress()+"开启监听");
            //阻塞操作
            future.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception {
        {
            new AppServerHello(9999).run();
        }
    }

}
