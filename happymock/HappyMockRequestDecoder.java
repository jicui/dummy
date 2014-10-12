package com.phei.netty.protocol.happymock;

import com.phei.netty.protocol.happymock.entity.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

/**
 * Created by jicui on 10/12/14.
 */
public class HappyMockRequestDecoder extends
        MessageToMessageDecoder<FullHttpRequest> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest, List<Object> objects) throws Exception {
        Request request=new Request();
        request.setHttpRequest(fullHttpRequest);
        request.setUri(fullHttpRequest.getUri());
        objects.add(request);
    }
}
