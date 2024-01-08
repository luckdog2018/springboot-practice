package com.lyon.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @title WebSocketServer
 * @author lyon
 * @createTime 2022年09月29日 10:49
 * @updateTime
 * @version 1.0.0
 */
@Slf4j
public class WebSocketServer {

    private final int serverPort = 5555;

    public void start() {
        // 服务端对启动辅助类,用于设置TCP相关参数, 根据需要可以分别设置主线程池和从线程池参数，来优化服务端性能;
        // 主线程池使用option方法来设置，从线程池使用childOption方法设置;
        ServerBootstrap boot = new ServerBootstrap();
        // 主线程组
        EventLoopGroup boss = new NioEventLoopGroup();
        // 工作线程组
        EventLoopGroup worker = new NioEventLoopGroup();
        // 设置主从线程模型
        boot.group(boss, worker)
                // 设置服务端NIO通信类型
                .channel(NioServerSocketChannel.class)
                // 设置ChannelPipeline，也就是业务职责链，由处理的Handler串联而成，由从线程池处理
                .childHandler(new WebSocketChannelInitializer())
                // backlog表示主线程池中在套接口排队的最大数量，队列由未连接队列（三次握手未完成的）和已连接队列
                .option(ChannelOption.SO_BACKLOG, 5)
                // 连接保活，相当于心跳机制，默认为7200s
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            Channel channel = boot.bind(serverPort).sync().channel();
            log.info("server start success, channel:{}", channel);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("server start failed! msg:{}",e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            log.info("server stop success");
        }

    }

}
