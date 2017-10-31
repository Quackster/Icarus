package org.alexdev.icarus.web.responses;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.duckhttpd.response.DefaultWebResponse;
import org.alexdev.duckhttpd.response.ResponseBuilder;
import org.alexdev.duckhttpd.response.WebResponses;

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

    @Override
    public FullHttpResponse getErrorResponse(String header, String message) {
        return new DefaultWebResponse().getErrorResponse(header, message);
    }
}
