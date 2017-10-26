package org.alexdev.icarus.web.util.response;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import org.alexdev.icarus.web.IcarusWeb;
import org.alexdev.icarus.web.util.Util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebResponse {

    public static FullHttpResponse getHtmlResponse(String text) {
        return getHtmlResponse(HttpResponseStatus.OK, text);
    }

    public static FullHttpResponse getHtmlResponse(HttpResponseStatus status, String text) {

        byte[] data = text.getBytes();

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                status,
                Unpooled.copiedBuffer(data)
        );

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, data.length);
        return response;
    }

    public static FullHttpResponse getFileResponse(File file, FullHttpRequest request) {

        byte[] fileData = Util.readFile(file);

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(fileData)
        );

        if (HttpUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        }

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, Util.getMimeType(file));
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileData.length);
        return response;
    }

    public static FullHttpResponse handleFileResponse(FullHttpRequest request) {

        Path path = Paths.get(IcarusWeb.getSiteDirectory(), request.uri().split("\\?")[0]);
        final File file = path.toFile();

        if (file != null && file.exists()) {
            if (file.isFile()) {
                return WebResponse.getFileResponse(file, request);
            }

            File indexFile = Paths.get(IcarusWeb.getSiteDirectory(), request.uri(), "home.html").toFile();

            if (indexFile.exists() && indexFile.isFile()) {
                return WebResponse.getFileResponse(indexFile, request);
            }

            return WebResponse.getHtmlResponse(HttpResponseStatus.FORBIDDEN, TextResponses.getForbiddenText());
        }

        return null;
    }
}
