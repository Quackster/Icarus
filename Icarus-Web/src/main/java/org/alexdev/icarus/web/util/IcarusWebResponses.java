package org.alexdev.icarus.web.util;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.duckhttpd.util.response.DefaultWebResponse;
import org.alexdev.duckhttpd.util.response.ResponseBuilder;
import org.alexdev.duckhttpd.util.response.WebResponses;

public class IcarusWebResponses implements WebResponses {
    @Override
    public FullHttpResponse getForbiddenResponse() {
        return new DefaultWebResponse().getForbiddenResponse();
    }

    @Override
    public FullHttpResponse getNotFoundResponse() {
        return new DefaultWebResponse().getNotFoundResponse();
    }

    @Override
    public FullHttpResponse getInternalServerErrorResponse(Throwable cause) {
        cause.printStackTrace();
        return ResponseBuilder.getHtmlResponse("\n" + "<html>\n" + "<head>\n" + "</head>\n" + "<body>\n" + "   <h1>Internal Server Error</h1>\n" + "<body>\n" + "</html>");
    }
}
