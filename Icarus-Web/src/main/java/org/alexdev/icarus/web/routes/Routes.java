package org.alexdev.icarus.web.routes;

import org.alexdev.icarus.web.controllers.index.HomeController;
import org.alexdev.icarus.web.routes.manager.RouteManager;

public class Routes {
    public static void register() {
        RouteManager.addRoute("/index", HomeController::index);
        RouteManager.addRoute("/index_test", HomeController::index_test);
    }
}
