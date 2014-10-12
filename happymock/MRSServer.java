package com.phei.netty.protocol.happymock;

import com.phei.netty.protocol.http.xml.codec.HttpXmlRequestDecoder;
import com.phei.netty.protocol.http.xml.codec.HttpXmlResponseEncoder;
import com.phei.netty.protocol.http.xml.pojo.Order;
import com.phei.netty.protocol.http.xml.server.HttpXmlServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;

/**
 * Created by jicui on 10/12/14.
 */
public class MRSServer {

    private void run(int port) throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 1024)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                    ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                    ch.pipeline().addLast("happy-mock-decoder", new HappyMockRequestDecoder());
                    ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                    ch.pipeline().addLast("happy-mock-encoder", new HappyMockResponseEncoder());
                    ch.pipeline().addLast("mrs-ServerHandler", new MRSServerHandler());
                }
            });
            ChannelFuture future = serverBootstrap.bind(new InetSocketAddress("127.0.0.1", port)).sync();
            System.out.println("Happy Mock HTTP MRS 服务器启动，网址是 : " + "http://localhost:"
                    + port);
            future.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new MRSServer().run(port);
    }
}
