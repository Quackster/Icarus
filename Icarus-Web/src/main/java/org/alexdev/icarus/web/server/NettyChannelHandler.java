package org.alexdev.icarus.web.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.alexdev.icarus.web.IcarusWeb;
import org.alexdev.icarus.web.routes.manager.Route;
import org.alexdev.icarus.web.routes.manager.RouteManager;
import org.alexdev.icarus.web.server.response.TextResponses;
import org.alexdev.icarus.web.server.response.WebResponse;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;

public class NettyChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof FullHttpRequest) {

            final FullHttpRequest request = (FullHttpRequest) msg;
            final String responseMessage = "Hello from Netty!";

            File file = Paths.get(IcarusWeb.getContentDirectory(), request.uri()).toFile();

            if (file != null && file.exists()) {
                if (file.isFile()) {
                    ctx.channel().writeAndFlush(WebResponse.getFileResponse(file, request));
                    return;
                }

                File indexFile = Paths.get(IcarusWeb.getContentDirectory(), request.uri(), "index.html").toFile();

                if (indexFile.exists() && indexFile.isFile()) {
                    ctx.channel().writeAndFlush(WebResponse.getFileResponse(indexFile, request));
                    return;
                }

                ctx.channel().writeAndFlush(WebResponse.getHtmlResponse(TextResponses.getForbiddenText()));
                return;
            }

            Route route = RouteManager.getRoute(request.uri());

            if (route != null) {
                FullHttpResponse response = route.handleRoute(request, ctx.channel());

                if (response == null) {
                    exceptionCaught(ctx, new Exception("Could not handle request: " + request.uri()));
                    return;
                }

                ctx.channel().writeAndFlush(response);
            } else {
                ctx.channel().writeAndFlush(WebResponse.getHtmlResponse(TextResponses.getNotFoundText()));
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