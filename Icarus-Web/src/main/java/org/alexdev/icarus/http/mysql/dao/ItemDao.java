package org.alexdev.icarus.http.mysql.dao;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.alexdev.icarus.http.mysql.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {

    public static List<String> getRecentPhotos() {

        List<String> jsonData = new ArrayList<String>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT id, extra_data FROM items WHERE item_id = (SELECT id FROM item_definitions WHERE item_name = 'external_image_wallitem_poster_small') ORDER BY id DESC LIMIT 24", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                JsonElement element = new JsonParser().parse(resultSet.getString("extra_data"));
                JsonObject object = element.getAsJsonObject();
                String photoName = object.get("w").getAsString();
                jsonData.add(photoName);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return jsonData;
    }
}
