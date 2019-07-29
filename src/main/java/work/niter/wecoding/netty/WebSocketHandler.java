package work.niter.wecoding.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: Cangwu
 * @Date: 2019/7/12 21:25
 * @Description:
 */
public class WebSocketHandler
        extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("[Server] " + msg.text());
        Channel channel = ctx.channel();

        for (Channel channel1 : channels) {
            // 不给自己发消息
            if (!channel1.equals(channel)) {
                channel1.writeAndFlush(
                        new TextWebSocketFrame(msg.text())
                );
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
        ConcurrentMap<String, Object> map = new ConcurrentHashMap<>(4);
        map.put("count", channels.size());

        List<String> namelist = new ArrayList<>();
        for (Channel channel1 : channels) {
            namelist.add(channel1.remoteAddress().toString());
        }
        map.put("list", namelist);
        channels.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(map)));
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
        System.out.println("[Server] " + ctx.channel().id().asShortText() + "断开了连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
