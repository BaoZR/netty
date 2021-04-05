package com.jcj.helloworld;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

//表示是线程安全的
@ChannelHandler.Sharable
public class HandleClientHello extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf)  {
        System.out.println("收到的信息为："+byteBuf.toString(CharsetUtil.UTF_8));
    }

/**

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        HttpContent httpcontent = (HttpContent) msg;
        ByteBuf buf = httpcontent.content();
        System.out.println("收到的信息为："+buf.toString(CharsetUtil.UTF_8));
    }
**/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)  {
        cause.printStackTrace();
        ctx.close();
    }


}
