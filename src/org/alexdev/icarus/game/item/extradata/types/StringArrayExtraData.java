package org.alexdev.icarus.game.item.extradata.types;

import java.util.List;

import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.ExtraDataType;
import org.alexdev.icarus.game.item.extradata.ExtraData;

public class StringArrayExtraData extends ExtraData {

    private String[] array;

    public StringArrayExtraData(ExtraDataPerspective setting, List<String> objects) {
        super(setting);
        this.array = objects.parallelStream().toArray(String[]::new);
    }

    @Override
    public ExtraDataType getType() {
        return ExtraDataType.STRING_ARRAY;
    }

    public String[] getArray() {
        return array;
    }
}
