package org.alexdev.icarus.http.util.web;

import org.alexdev.duckhttpd.routes.RouteManager;
import org.alexdev.icarus.http.controllers.*;
import org.alexdev.icarus.http.controllers.account.AccountController;
import org.alexdev.icarus.http.controllers.account.RegisterController;
import org.alexdev.icarus.http.controllers.pages.ClientController;
import org.alexdev.icarus.http.controllers.pages.HomeController;

public class Routes {
    public static void register() {
        RouteManager.addRoute("", RootController::root);
        RouteManager.addRoute("/", HomeController::homepage);
        RouteManager.addRoute("/index", HomeController::homepage);
        RouteManager.addRoute("/home", HomeController::homepage);
        RouteManager.addRoute("/register", HomeController::register);
        RouteManager.addRoute("/me", HomeController::me);
        RouteManager.addRoute("/disconnected", HomeController::disconnected);
        RouteManager.addRoute("/account/register", RegisterController::register);
        RouteManager.addRoute("/account/login", AccountController::login);
        RouteManager.addRoute("/account/logout", AccountController::logout);
        RouteManager.addRoute("/hotel", ClientController::hotel);
        RouteManager.addRoute("/api/newuser/name/check", ApiController::nameCheck);
        RouteManager.addRoute("/api/newuser/name/select", ApiController::nameSelect);
        RouteManager.addRoute("/api/user/look/save", ApiController::saveLook);
        RouteManager.addRoute("/api/user/room/select", ApiController::roomSelect);
        RouteManager.addRoute("/api/log/loginstep", ApiController::loginStep);

    }
}
