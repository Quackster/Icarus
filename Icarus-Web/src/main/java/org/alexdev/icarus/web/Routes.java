package org.alexdev.icarus.web;

import org.alexdev.icarus.duckhttpd.routes.manager.RouteManager;
import org.alexdev.icarus.web.controllers.home.HomeController;

public class Routes {
    public static void register() {
        RouteManager.addRoute("/", HomeController::index);
        RouteManager.addRoute("/index", HomeController::index);;
    }
}
