package com.phei.netty.protocol.happymock;

import com.phei.netty.protocol.happymock.entity.Response;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jicui on 10/12/14.
 */
public class HappyMockResponseEncoder extends MessageToMessageEncoder<Response> {
    final static String CHARSET_NAME = "UTF-8";
    final static Charset UTF_8 = Charset.forName(CHARSET_NAME);
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Response response, List<Object> objects) throws Exception {
        ByteBuf encodeBuf = Unpooled.copiedBuffer(response.getContent(), UTF_8);
        DefaultFullHttpResponse defaultFullHttpResponse=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,encodeBuf);
        if(response.isKeepAlive()){
           defaultFullHttpResponse.headers().set(HttpHeaders.Names.CONTENT_LENGTH,encodeBuf.readableBytes());
           //defaultFullHttpResponse.headers().set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.KEEP_ALIVE);
        }
        objects.add(defaultFullHttpResponse);
    }
}
