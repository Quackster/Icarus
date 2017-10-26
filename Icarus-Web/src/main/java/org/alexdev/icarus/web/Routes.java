package org.alexdev.icarus.web;

import org.alexdev.icarus.web.controllers.home.HomeController;
import org.alexdev.icarus.web.controllers.home.TestController;
import org.alexdev.icarus.web.routes.manager.RouteManager;

public class Routes {
    public static void register() {
        RouteManager.addRoute("/", HomeController::index);
        RouteManager.addRoute("/index", HomeController::index);
        RouteManager.addRoute("/me", HomeController::client);
        RouteManager.addRoute("/test", TestController::test);
    }
}
