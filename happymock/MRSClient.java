package com.phei.netty.protocol.happymock;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.net.InetSocketAddress;

/**
 * User: jicui
 * Date: 14-10-11
 */
public class MRSClient {

    private void connect(String host,int port) throws Exception {
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try {
        b.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>(){

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                //decode comes from up to bottom
                ch.pipeline().addLast("http-decoder",
                        new HttpResponseDecoder());
                ch.pipeline().addLast("http-aggregator",
                        new HttpObjectAggregator(65536));
                //encode comes from bottom to up
                ch.pipeline().addLast("http-encoder",
                        new HttpRequestEncoder());
                ch.pipeline().addLast("http-mock-request-handler",
                        new MRSClientHandler());
            }
        });
        // 发起异步连接操作
         ChannelFuture  f = b.connect(new InetSocketAddress(host,port)).sync();
         f.channel().closeFuture().sync();
        }finally {
            // 优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args){
        try {
            new MRSClient().connect("127.0.0.1",8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
