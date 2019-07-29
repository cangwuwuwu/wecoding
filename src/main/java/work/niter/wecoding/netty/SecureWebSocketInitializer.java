package work.niter.wecoding.netty;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @Author: Cangwu
 * @Date: 2019/7/18 23:17
 * @Description:
 */
public class SecureWebSocketInitializer extends WebSocketInitializer{
    private final SslContext context;

    public SecureWebSocketInitializer(SslContext context) {
        this.context = context;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        SSLEngine engine = context.newEngine(ch.alloc());
        engine.setUseClientMode(false);
        ch.pipeline().addFirst("ssl", new SslHandler(engine));
    }
}
