package org.alexdev.icarus.web.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.alexdev.icarus.web.routes.manager.Route;
import org.alexdev.icarus.web.routes.manager.RouteManager;
import org.alexdev.icarus.web.util.response.TextResponses;
import org.alexdev.icarus.web.util.response.WebResponse;
import org.alexdev.icarus.web.server.session.WebSession;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;

public class NettyChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof FullHttpRequest) {

            final FullHttpRequest request = (FullHttpRequest) msg;
            final FullHttpResponse fileResponse = WebResponse.handleFileResponse(request);
            final Route route = RouteManager.getRoute(request.uri());

            if (route != null) {

                WebSession session = new WebSession(ctx.channel(), request);
                FullHttpResponse response = route.handleRoute(session);

                if (response == null) {
                    exceptionCaught(ctx, new Exception("Could not handle request: " + request.uri()));
                    return;
                }

                ctx.channel().writeAndFlush(response);
                return;
            }

            if (fileResponse != null) {
                ctx.channel().writeAndFlush(fileResponse);
                return;
            } else {
                ctx.channel().writeAndFlush(WebResponse.getHtmlResponse(HttpResponseStatus.NOT_FOUND, TextResponses.getNotFoundText()));
            }

        } else {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        StringWriter sw = new StringWriter();
        cause.printStackTrace(new PrintWriter(sw));

        ctx.channel().writeAndFlush(WebResponse.getHtmlResponse(TextResponses.getInternalServerError(cause.getMessage(), sw.toString())));
    }
}