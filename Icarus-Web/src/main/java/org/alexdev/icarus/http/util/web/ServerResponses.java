package org.alexdev.icarus.http.util.web;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.alexdev.duckhttpd.response.DefaultWebResponse;
import org.alexdev.duckhttpd.response.ResponseBuilder;
import org.alexdev.duckhttpd.response.WebResponses;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.icarus.http.log.Log;

public class ServerResponses implements WebResponses {

    @Override
    public FullHttpResponse getForbiddenResponse(WebConnection client) {
        return new DefaultWebResponse().getForbiddenResponse(client);
    }

    @Override
    public FullHttpResponse getNotFoundResponse(WebConnection client) {
        return new DefaultWebResponse().getNotFoundResponse(client);
    }

    @Override
    public FullHttpResponse getInternalServerErrorResponse(WebConnection client, Throwable cause) {
        Log.getErrorLogger().error("Netty error occurred: ", cause);
        return ResponseBuilder.create(HttpResponseStatus.INTERNAL_SERVER_ERROR, "\n" + "<html>\n" + "<head>\n" + "</head>\n" + "<body>\n" + "   <h1>Internal Server Error</h1>\n" + "<body>\n" + "</html>");
    }

    @Override
    public FullHttpResponse getErrorResponse(WebConnection client, String header, String message) {
        Template tpl = client.template("error");
        tpl.set("errorTitle", header);
        tpl.set("errorMessage", message);
        tpl.render();

        return client.response();
    }
}
