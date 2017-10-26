package org.alexdev.icarus.web.server.response;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import org.alexdev.icarus.web.IcarusWeb;
import org.alexdev.icarus.web.util.MimeType;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Files;
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

        byte[] fileData = readFile(file);

        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.copiedBuffer(fileData)
        );

        if (HttpUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        }

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, WebResponse.getMimeType(file));
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileData.length);
        return response;
    }

    static String getMimeType(File file) {
        return MimeType.valueOf(FilenameUtils.getExtension(file.getName())).contentType;
    }

    public static byte[] readFile(String relativePath) {

        try {
            return Files.readAllBytes(Paths.get(IcarusWeb.getContentDirectory(), relativePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    static byte[] readFile(File file) {

        try {
             return Files.readAllBytes(Paths.get(file.getCanonicalPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
