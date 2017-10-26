package org.alexdev.icarus.web.routes;

import org.alexdev.icarus.web.controllers.cookie.SetCookieController;
import org.alexdev.icarus.web.controllers.index.IndexController;
import org.alexdev.icarus.web.routes.manager.RouteManager;

public class Routes {
    public static void register() {
        RouteManager.addRoute("/index", new IndexController());
        RouteManager.addRoute("/setcookie", new SetCookieController());
    }
}
