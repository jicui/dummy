package com.phei.netty.protocol.happymock;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

/**
 * User: jicui
 * Date: 14-10-11
 */
public class MRSClientHandler extends SimpleChannelInboundHandler<DefaultFullHttpResponse> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DefaultFullHttpRequest defaultFullHttpRequest=new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,"/fulfillment/window/v1");
        ctx.writeAndFlush(defaultFullHttpRequest);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if(ctx!=null){
            ctx.close();
        }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DefaultFullHttpResponse defaultFullHttpResponse) throws Exception {
        System.out.println("The client receive response of http header is : "
                + defaultFullHttpResponse.headers().names());
        System.out.println("The client receive response of http body is : "
                + defaultFullHttpResponse.content());
    }
}
