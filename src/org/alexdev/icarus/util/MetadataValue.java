package org.alexdev.icarus.util;

public class MetadataValue {

    private Object value;
    private boolean save;

    public MetadataValue(Object value, boolean save) {
        this.value = value;
        this.save = save;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Object getObject() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Allow save.
     *
     * @return true, if successful
     */
    public boolean allowSave() {
        return save;
    }

    /**
     * Sets the allow save.
     *
     * @param save the new allow save
     */
    public void setAllowSave(boolean save) {
        this.save = save;
    }
}
