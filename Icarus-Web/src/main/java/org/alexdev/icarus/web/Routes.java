package org.alexdev.icarus.web;

import org.alexdev.duckhttpd.routes.RouteManager;
import org.alexdev.icarus.web.controllers.home.AccountController;
import org.alexdev.icarus.web.controllers.home.HomeController;

public class Routes {
    public static void register() {
        RouteManager.addRoute("/", HomeController::homepage);
        RouteManager.addRoute("/index", HomeController::homepage);
        RouteManager.addRoute("/homepage", HomeController::homepage);
        RouteManager.addRoute("/account/login", AccountController::login);
    }
}
