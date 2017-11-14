package org.alexdev.icarus.http;

import org.alexdev.duckhttpd.routes.RouteManager;
import org.alexdev.icarus.http.controllers.*;
import org.alexdev.icarus.http.controllers.account.AccountController;
import org.alexdev.icarus.http.controllers.account.RegisterController;
import org.alexdev.icarus.http.controllers.housekeeping.HousekeepingController;
import org.alexdev.icarus.http.controllers.housekeeping.HousekeepingNewsController;
import org.alexdev.icarus.http.controllers.housekeeping.HousekeepingSettingsController;
import org.alexdev.icarus.http.controllers.housekeeping.HousekeepingUsersController;
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
        RouteManager.addRoute("/article", HomeController::article);
        RouteManager.addRoute("/account/register", RegisterController::register);
        RouteManager.addRoute("/account/login", AccountController::login);
        RouteManager.addRoute("/account/logout", AccountController::logout);
        RouteManager.addRoute("/hotel", ClientController::hotel);

        RouteManager.addRoute("/housekeeping", HousekeepingController::dashboard);
        RouteManager.addRoute("/housekeeping/login", HousekeepingController::login);
        RouteManager.addRoute("/housekeeping/logout", HousekeepingController::logout);
        RouteManager.addRoute("/housekeeping/users/search", HousekeepingUsersController::search);
        RouteManager.addRoute("/housekeeping/users/create", HousekeepingUsersController::create);
        RouteManager.addRoute("/housekeeping/users/edit", HousekeepingUsersController::edit);
        RouteManager.addRoute("/housekeeping/articles", HousekeepingNewsController::articles);
        RouteManager.addRoute("/housekeeping/articles/create", HousekeepingNewsController::create);
        RouteManager.addRoute("/housekeeping/articles/delete", HousekeepingNewsController::delete);
        RouteManager.addRoute("/housekeeping/articles/edit", HousekeepingNewsController::edit);
        RouteManager.addRoute("/housekeeping/settings/scheduler", HousekeepingSettingsController::scheduler);
        RouteManager.addRoute("/housekeeping/settings/camera", HousekeepingSettingsController::camera);
        RouteManager.addRoute("/housekeeping/settings/thumbnail", HousekeepingSettingsController::thumbnail);

        RouteManager.addRoute("/api/newuser/name/check", ApiController::nameCheck);
        RouteManager.addRoute("/api/newuser/name/select", ApiController::nameSelect);
        RouteManager.addRoute("/api/user/look/save", ApiController::saveLook);
        RouteManager.addRoute("/api/user/room/select", ApiController::roomSelect);
        RouteManager.addRoute("/api/log/loginstep", ApiController::loginStep);

    }
}
