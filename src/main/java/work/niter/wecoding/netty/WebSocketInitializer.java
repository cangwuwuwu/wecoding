package work.niter.wecoding.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Cangwu
 * @Date: 2019/7/12 20:48
 * @Description:
 */
public class WebSocketInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline
                // 压缩数据
                .addLast("compressor", new HttpContentDecompressor())
                // http编码器
                .addLast("serverCodec", new HttpServerCodec())
                // 对大数据流的支持
                .addLast("bigData", new ChunkedWriteHandler())
                // 对HttpMessage聚合成FullHttpRequest或FullHttpResponse
                .addLast("aggregator", new HttpObjectAggregator(1024*64))
                // 指定给客户端连接的路由
                .addLast("ws", new WebSocketServerProtocolHandler("/ws"))
                // 十分钟内未收发消息,  自动断开连接
                .addLast("timeout", new IdleStateHandler(0, 0, 10, TimeUnit.MINUTES))
                .addLast("heartbeat", new HeartBeatHandler())
                // 自定义处理器
                .addLast("myHandler", new WebSocketHandler());
    }
}
