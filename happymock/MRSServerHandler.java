package com.phei.netty.protocol.happymock;

import com.phei.netty.protocol.happymock.entity.Request;
import com.phei.netty.protocol.happymock.entity.Response;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by jicui on 10/12/14.
 */
public class MRSServerHandler extends SimpleChannelInboundHandler<Request> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Request request) throws Exception {
        System.out.println("Received request from clients");
        String content="";
        if(request.getUri().equals("/test")){
            content="test";
        }
        if(request.getUri().equals("/compile")){
            content="compile";
        }
        System.out.println("send mock response");
        boolean isKeepAlive=isKeepAlive(request.getHttpRequest());
        ChannelFuture future = ctx.writeAndFlush(new Response(content,isKeepAlive));
       if (!isKeepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendError(ChannelHandlerContext ctx,
                                  HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                status, Unpooled.copiedBuffer("失败: " + status.toString()
                + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
