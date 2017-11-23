package org.alexdev.icarus.http.controllers;

import org.alexdev.duckhttpd.response.ResponseBuilder;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.icarus.http.game.GameSettings;

import java.io.File;
import java.nio.file.Paths;

public class CameraController {

    /**
     * Handle the /habbo-stories URI request
     * @param client the connection
     */
    public static void camera(WebConnection client) throws Exception {
        File file = Paths.get(GameSettings.CAMERA_PATH, client.get().get("image").replace("_small.png",".png")).toFile();
        ResponseBuilder.create(file, client);
    }
}
