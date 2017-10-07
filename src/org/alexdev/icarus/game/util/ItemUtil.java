package org.alexdev.icarus.game.util;

import java.lang.reflect.Type;
import java.util.HashMap;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.item.moodlight.MoodlightData;
import org.alexdev.icarus.game.item.moodlight.MoodlightPreset;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.api.messages.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ItemUtil {

    /**
     * Generate extra data depending on the type of item.
     *
     * @param item the item
     * @param response the response
     */
    public static void generateExtraData(Item item, Response response) {

        if (item.getDefinition().getInteractionType() == InteractionType.WALLPAPER) {
            response.writeInt(2);
            response.writeInt(0);
            response.writeString(item.getExtraData());
            return;
        }

        if (item.getDefinition().getInteractionType() == InteractionType.FLOOR) {
            response.writeInt(3);
            response.writeInt(0);
            response.writeString(item.getExtraData());
            return;
        }

        if (item.getDefinition().getInteractionType() == InteractionType.LANDSCAPE) {
            response.writeInt(4);
            response.writeInt(0);
            response.writeString(item.getExtraData());
            return;
        }

        if (item.getDefinition().getInteractionType() == InteractionType.BACKGROUND) {
            response.writeInt(0);
            response.writeInt(1);

            if (item.getExtraData().length() > 0) {

                String[] adsData = item.getExtraData().split(Character.toString((char) 9));
                int count = adsData.length;

                response.writeInt(count / 2);

                for (int i = 0; i <= count - 1; i++) {
                    response.writeString(adsData[i]);
                }

            } else {
                response.writeInt(0);
            }

            return;
        }

        if (item.getDefinition().getInteractionType() == InteractionType.MANNEQUIN) {

            response.writeInt(0);
            response.writeInt(1);
            response.writeInt(3);

            if (item.getExtraData().contains(Character.toString((char)5))) {
                String[] mannequinData = item.getExtraData().split(Character.toString((char)5));
                response.writeString("GENDER");
                response.writeString(mannequinData[0]);
                response.writeString("FIGURE");
                response.writeString(mannequinData[1]);
                response.writeString("OUTFIT_NAME");
                response.writeString(mannequinData[2]);
            } else {
                response.writeString("GENDER");
                response.writeString("");
                response.writeString("FIGURE");
                response.writeString("");
                response.writeString("OUTFIT_NAME");
                response.writeString("");
            }
            return;
        }

        if (item.getDefinition().getInteractionType() == InteractionType.ROOMBG) {
            if (item.getRoom() != null && item.getExtraData().length() > 0) {

                Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
                HashMap<String, Integer> settings = new Gson().fromJson(item.getExtraData(), type);

                response.writeInt(0);
                response.writeInt(5);
                response.writeInt(4);
                response.writeInt(settings.get("enabled"));
                response.writeInt(settings.get("hue"));
                response.writeInt(settings.get("saturation"));
                response.writeInt(settings.get("brightness"));

            } else {
                response.writeInt(0);
                response.writeInt(0);
                response.writeString("");
            }

            return;
        }

        response.writeInt(1);
        response.writeInt(0);
        response.writeString(item.getExtraData());
    }
    
    /**
     * Generate extra data for wall items depending on the type of item.
     *
     * @param item the item
     * @param response the response
     */
    public static void generateWallExtraData(Item item, Response response) {

        if (item.getDefinition().getInteractionType() == InteractionType.DIMMER) {

            StringBuilder builder = new StringBuilder();

            MoodlightData data = null;
            MoodlightPreset preset = null;

            if (item.getExtraData().length() > 0) {
                data = new Gson().fromJson(item.getExtraData(), new TypeToken<MoodlightData>(){}.getType());
                preset = data.getPresets().get(data.getCurrentPreset() - 1);
            } else {

                data = new MoodlightData();
                preset = new MoodlightPreset();
            }

            builder.append(data.isEnabled() ? 2 : 1);
            builder.append(",");
            builder.append(data.getCurrentPreset());
            builder.append(",");
            builder.append(preset.isBackgroundOnly() ? 2 : 1);
            builder.append(",");
            builder.append(preset.getColorCode());
            builder.append(",");
            builder.append(preset.getColorIntensity());
            
            Log.info("EXTRADATA ITEMUTIL: " + builder.toString());
            
            response.writeString(builder.toString());
            return;
        }
        
        response.writeString(item.getExtraData());
    }
}
