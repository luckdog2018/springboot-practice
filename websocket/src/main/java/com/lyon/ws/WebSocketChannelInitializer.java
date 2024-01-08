package com.lyon.ws;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 *
 * @title WebSocketChannelInitializer
 * @author lyon
 * @createTime 2022年09月29日 11:32
 * @updateTime
 * @version 1.0.0
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //设置log监听器，并且日志级别为debug，方便观察运行流程
        socketChannel.pipeline()
                .addLast("logging", new LoggingHandler("DEBUG"))
                //设置解码器,websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                .addLast("http-codec", new HttpServerCodec())
                //聚合器，使用websocket会用到
                .addLast("aggregator", new HttpObjectAggregator(65536))
                //用于大数据的分区传输,以块的方式来写的处理器
                .addLast("http-chunked", new ChunkedWriteHandler())
                //自定义的业务handler
                .addLast("handler", new WebSocketHandler());
    }
}
