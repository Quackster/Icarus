package org.alexdev.icarus.game.item.extradata.types;

import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.ExtraDataType;
import org.alexdev.icarus.game.item.extradata.ExtraData;

public class StringExtraData extends ExtraData {

    private String data;

    public StringExtraData(ExtraDataPerspective setting, String str) {
        super(setting);
        this.data = str;
    }

    @Override
    public ExtraDataType getType() {
        return ExtraDataType.STRING;
    }

    public String getData() {
        return data;
    }
}
