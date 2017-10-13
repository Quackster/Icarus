package org.alexdev.icarus.game.item.extradata.types;

import java.util.Map;

import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.ExtraDataType;
import org.alexdev.icarus.game.item.extradata.ExtraData;

public class KeyValueExtraData extends ExtraData {

    private Map<String, String> keyValues;

    public KeyValueExtraData(ExtraDataPerspective setting, Map<String, String> keyValues) {
        super(setting);
        this.keyValues = keyValues;
    }

    @Override
    public ExtraDataType getType() {
        return ExtraDataType.KEY_VALUE;
    }

    public Map<String, String> getKeyValues() {
        return keyValues;
    }
}
