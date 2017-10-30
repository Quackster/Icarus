package org.alexdev.icarus.web;

import org.alexdev.icarus.duckhttpd.routes.RouteManager;
import org.alexdev.icarus.web.controllers.home.HomeController;

public class Routes {
    public static void register() {
        RouteManager.addRoute("/", HomeController::homepage);
        RouteManager.addRoute("/homepage", HomeController::homepage);
        RouteManager.addRoute("/register", HomeController::register);
        RouteManager.addRoute("/me", HomeController::me);
        RouteManager.addRoute("/client", HomeController::client);
        RouteManager.addRoute("/hotel", HomeController::hotel);
        RouteManager.addRoute("/api/client/clienturl", HomeController::clienturl);
    }
}
