package org.alexdev.icarus.http.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.alexdev.duckhttpd.response.ResponseBuilder;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.util.MimeType;
import org.alexdev.icarus.http.mysql.dao.PlayerDao;
import org.apache.commons.lang3.StringUtils;

public class ApiController {

    public static void nameCheck(WebConnection client) {

        JsonElement jelement = new JsonParser().parse(client.getRequestContent());
        JsonObject jobject = jelement.getAsJsonObject();

        String name = jobject.get("name").getAsString();
        client.session().delete("regname");

        JsonObject json = new JsonObject();
        if (PlayerDao.nameExists(name) && name.length() > 0) {

            json.addProperty("code", "NAME_IN_USE");
            json.addProperty("validationResult", "null");
            json.add("suggestions", new JsonArray());

        } else if (name.length() < 3) {

            JsonObject validationResult = new JsonObject();
            validationResult.addProperty("resultType", "VALIDATION_ERROR_NAME_TOO_SHORT");
            validationResult.addProperty("additionalInfo", name);
            validationResult.addProperty("vald", false);
            json.addProperty("code", "INVALID_NAME");
            json.add("validationResult", validationResult);
            json.add("suggestions", new JsonArray());

        } else if (name.length() > 16) {

            JsonObject validationResult = new JsonObject();
            validationResult.addProperty("resultType", "VALIDATION_ERROR_NAME_TOO_LONG");
            validationResult.addProperty("additionalInfo", name);
            validationResult.addProperty("vald", false);
            json.addProperty("code", "INVALID_NAME");
            json.add("validationResult", validationResult);
            json.add("suggestions", new JsonArray());

        } else if (!StringUtils.isAlphanumeric(name)) {

            JsonObject validationResult = new JsonObject();
            validationResult.addProperty("resultType", "VALIDATION_ERROR_ILLEGAL_CHARS");
            validationResult.addProperty("additionalInfo", name);
            validationResult.addProperty("vald", false);
            json.addProperty("code", "INVALID_NAME");
            json.add("validationResult", validationResult);
            json.add("suggestions", new JsonArray());

        } else {

            json.addProperty("code", "OK");
            json.addProperty("validationResult", "null");
            json.add("suggestions", new JsonArray());
            client.session().set("regname", name);
        }

        client.setResponse(ResponseBuilder.create(MimeType.json, json.toString()));
    }

    public static void saveLook(WebConnection client) {

        if (!client.session().contains("regname")) {
            return;
        }

        JsonElement element = new JsonParser().parse(client.getRequestContent());
        JsonObject object = element.getAsJsonObject();

        String look = object.get("figure").getAsString();

        JsonObject json = new JsonObject();
        json.addProperty("uniqueId", "0");
        json.addProperty("name", client.session().getString("regname"));
        json.addProperty("figureString", look);
        json.addProperty("motto", "");

        PlayerDao.updateFigure(client.session().getInt("userId"), look);
        client.setResponse(ResponseBuilder.create(MimeType.json, json.toString()));
    }

    public static void nameSelect(WebConnection client) {

        JsonElement jelement = new JsonParser().parse(client.getRequestContent());
        JsonObject jobject = jelement.getAsJsonObject();

        String name = jobject.get("name").getAsString();
        client.session().delete("regname");

        JsonObject json = new JsonObject();
        if (PlayerDao.nameExists(name) && name.length() > 0) {

            json.addProperty("code", "NAME_IN_USE");
            json.addProperty("validationResult", "null");
            json.add("suggestions", new JsonArray());

        } else if (name.length() < 3) {

            JsonObject validationResult = new JsonObject();
            validationResult.addProperty("resultType", "VALIDATION_ERROR_NAME_TOO_SHORT");
            validationResult.addProperty("additionalInfo", name);
            validationResult.addProperty("vald", false);
            json.addProperty("code", "INVALID_NAME");
            json.add("validationResult", validationResult);
            json.add("suggestions", new JsonArray());

        } else if (name.length() > 16) {

            JsonObject validationResult = new JsonObject();
            validationResult.addProperty("resultType", "VALIDATION_ERROR_NAME_TOO_LONG");
            validationResult.addProperty("additionalInfo", name);
            validationResult.addProperty("vald", false);
            json.addProperty("code", "INVALID_NAME");
            json.add("validationResult", validationResult);
            json.add("suggestions", new JsonArray());

        } else if (!StringUtils.isAlphanumeric(name)) {

            JsonObject validationResult = new JsonObject();
            validationResult.addProperty("resultType", "VALIDATION_ERROR_ILLEGAL_CHARS");
            validationResult.addProperty("additionalInfo", name);
            validationResult.addProperty("vald", false);
            json.addProperty("code", "INVALID_NAME");
            json.add("validationResult", validationResult);
            json.add("suggestions", new JsonArray());

        } else {

            json.addProperty("code", "OK");
            json.addProperty("validationResult", "null");
            json.add("suggestions", new JsonArray());
            client.session().set("regname", name);

            PlayerDao.updateName(client.session().getInt("userId"), name);
        }

        client.setResponse(ResponseBuilder.create(MimeType.json, json.toString()));

    }

    public static void roomSelect(WebConnection client) {
        client.setResponse(ResponseBuilder.create(MimeType.json, ""));
    }

    public static void loginStep(WebConnection client) {
        client.setResponse(ResponseBuilder.create(MimeType.json, "{}"));
    }
}
