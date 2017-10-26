package org.alexdev.icarus.web.server.session;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import org.alexdev.icarus.web.template.Template;

import java.util.HashMap;
import java.util.Map;

public class WebSession {

    private Channel channel;
    private FullHttpRequest httpRequest;

    private WebQuery postData;
    private WebQuery getData;
    private WebQuery sessionData;

    public static final AttributeKey<Map<String, String>> SESSION_DATA = AttributeKey.valueOf("SessionDataMap");

    public WebSession(Channel channel, FullHttpRequest httpRequest) {
        this.channel = channel;
        this.httpRequest = httpRequest;

        if (!this.channel.hasAttr(SESSION_DATA)) {
            this.channel.attr(SESSION_DATA).set(new HashMap<>());
        }

        this.getData = new WebQuery(this.httpRequest.uri());
        this.postData = new WebQuery("?" + this.httpRequest.content().toString(CharsetUtil.UTF_8));
        this.sessionData = new WebQuery(this.channel.attr(SESSION_DATA).get());
    }

    public WebQuery post() {
        return postData;
    }

    public WebQuery get() {
        return getData;
    }

    public WebQuery session() {
        return sessionData;
    }

    public Channel channel() {
        return channel;
    }

    public FullHttpRequest request() {
        return httpRequest;
    }

    public Template template() {
        return new Template(this);
    }
}
