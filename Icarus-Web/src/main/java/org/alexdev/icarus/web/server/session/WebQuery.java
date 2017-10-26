package org.alexdev.icarus.web.server.session;

import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebQuery {

    private Map<String, String> queries;

    public WebQuery(Map<String, String> sessionData) {
        this.queries = sessionData;
    }

    public WebQuery(String queryData) {
        this.queries = new HashMap<String, String>();

        QueryStringDecoder decoder = new QueryStringDecoder(queryData);

        for (Map.Entry<String, List<String>> set : decoder.parameters().entrySet()) {

            if (set.getKey().isEmpty()) {
                continue;
            }

            if (set.getValue().isEmpty()) {
                this.queries.put(set.getKey(), null);
            } else {
                this.queries.put(set.getKey(), set.getValue().get(0));
            }
        }
    }

    public String get(String key) {
        if (this.queries.containsKey(key)) {
            return this.queries.get(key);
        }

        return null;
    }

    public boolean contains(String key) {
        return this.queries.containsKey(key);
    }

    public void set(String key, String value) {
        this.queries.put(key, value);
    }

    public void delete(String key) {
        this.queries.remove(key);
    }
}
