package com.phei.netty.protocol.happymock;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.InetAddress;
import java.nio.charset.Charset;

/**
 * User: jicui
 * Date: 14-10-11
 */
public class MRSClientHandler extends SimpleChannelInboundHandler<DefaultFullHttpResponse> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<10;i++){
            sendMockRequest(ctx);
        }

    }

    private void sendMockRequest(ChannelHandlerContext ctx) {
        DefaultFullHttpRequest defaultFullHttpRequest=new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,"/test");
        HttpHeaders headers = defaultFullHttpRequest.headers();
        headers.set(HttpHeaders.Names.HOST, "i.wshang.com");
        headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        headers.set(HttpHeaders.Names.ACCEPT_ENCODING,
                HttpHeaders.Values.GZIP.toString() + ','
                        + HttpHeaders.Values.DEFLATE.toString());
        headers.set(HttpHeaders.Names.ACCEPT_CHARSET,
                "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        headers.set(HttpHeaders.Names.ACCEPT_LANGUAGE, "zh");
        headers.set(HttpHeaders.Names.USER_AGENT,
                "Netty xml Http Client side");
        headers.set(HttpHeaders.Names.ACCEPT,
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        //headers.set(HttpHeaders.Names.Keep)
        //defaultFullHttpRequest.headers().
        System.out.println("send mock request to server");
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
                + defaultFullHttpResponse.content().toString(Charset.forName("UTF-8")));
        //channelHandlerContext.close();
    }
}
