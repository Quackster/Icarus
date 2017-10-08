package org.alexdev.icarus.game.item.extradata.types;

import java.util.List;

import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.ExtraDataType;
import org.alexdev.icarus.game.item.extradata.ExtraData;

public class IntArrayExtraData extends ExtraData {

    private Integer[] array;

    public IntArrayExtraData(ExtraDataPerspective setting, List<Integer> objects) {
        super(setting);
        this.array = objects.parallelStream().toArray(Integer[]::new);
    }

    @Override
    public ExtraDataType getType() {
        return ExtraDataType.INT_ARRAY;
    }

    public Integer[] getArray() {
        return array;
    }
}

