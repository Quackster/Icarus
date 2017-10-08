package org.alexdev.icarus.game.item.extradata.types;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraDataReader;
import org.alexdev.icarus.game.item.toner.TonerData;

public class TonerDataReader extends ExtraDataReader<TonerData> {
    
    public TonerData getTonerData(Item item) {
        return super.getJsonData(item, TonerData.class);
    }
}
