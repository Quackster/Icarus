package org.alexdev.icarus.web.routes.manager;

import java.util.HashMap;
import java.util.Map;

public class RouteManager {

    private static Map<String, Route> routes;

    static {
        routes = new HashMap<String, Route>();
    }

    public static void addRoute(String uri, Route route) {
        routes.put(uri, route);
    }

    public static Route getRoute(String uri) {

        uri = uri.split("\\?")[0]; // remove get parameters for lookup

        if (routes.containsKey(uri)) {
            return routes.get(uri);
        }

        return null;
    }

    public static Map<String, Route> getRoutes() {
        return routes;
    }
}
