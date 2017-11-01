package org.alexdev.icarus.web;

import org.alexdev.duckhttpd.routes.RouteManager;
import org.alexdev.icarus.web.controllers.home.AccountController;
import org.alexdev.icarus.web.controllers.home.HomeController;

public class Routes {
    public static void register() {
        RouteManager.addRoute("/", HomeController::homepage);
        RouteManager.addRoute("/index", HomeController::homepage);
        RouteManager.addRoute("/home", HomeController::homepage);
        RouteManager.addRoute("/account/login", AccountController::login);
        RouteManager.addRoute("/account/logout", AccountController::logout);
        RouteManager.addRoute("/me", AccountController::me);
    }
}
