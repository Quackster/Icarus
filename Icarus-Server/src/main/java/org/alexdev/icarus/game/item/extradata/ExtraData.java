package org.alexdev.icarus.game.item.extradata;

public abstract class ExtraData {
    
    private ExtraDataPerspective perspective;

    public ExtraData(ExtraDataPerspective setting) {
        this.perspective = setting;
    }

    public abstract ExtraDataType getType();
    
    /**
     * Gets the setting.
     *
     * @return the setting
     */
    public ExtraDataPerspective getPerspective() {
        return perspective;
    }
}
