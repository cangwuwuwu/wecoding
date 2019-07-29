package work.niter.wecoding.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * @Author: Cangwu
 * @Date: 2019/7/12 20:36
 * @Description: WebSocket
 */
@Component
public class WebSocketServer {

    private static class SingletonWebSocketServer {
        static final WebSocketServer instance = new WebSocketServer();
    }

    public static WebSocketServer getInstance() {
        return SingletonWebSocketServer.instance;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap bootstrap;
    private ChannelFuture channelFuture;

    public WebSocketServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        bootstrap.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketInitializer());
    }

    public void start() {
        this.channelFuture = bootstrap.bind(8088);
        System.out.println("netty server start finished...");
    }
}
