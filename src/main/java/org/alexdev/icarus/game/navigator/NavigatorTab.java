package org.alexdev.icarus.game.navigator;

import java.util.List;
import java.util.stream.Collectors;

import org.alexdev.icarus.log.Log;

public class NavigatorTab {

    private int id;
    private int childId;
    private String tabName;
    private String title;
    private byte buttonType;
    private boolean closed;
    private boolean thumbnail;
    private NavigatorRoomPopulator roomPopulator;

    /**
     * Fill the {@link NavigatorTab} data.
     *
     * @param id the id
     * @param childId the child id
     * @param tabName the tab name
     * @param title the title
     * @param buttonType the button type
     * @param closed the closed
     * @param thumbnail the thumbnail
     * @param roomPopulator the room populator
     */
    public void fill(int id, int childId, String tabName, String title, byte buttonType, boolean closed, boolean thumbnail, String roomPopulator) {
        this.id = id;
        this.childId = childId;
        this.tabName = tabName;
        this.title = title;
        this.buttonType = buttonType;
        this.closed = closed;
        this.thumbnail = thumbnail;

        String roomPopulatorClass = roomPopulator;

        if (roomPopulator.length() > 0) {
            this.roomPopulator = getPopulator(roomPopulatorClass);
            this.roomPopulator.setNavigatorTab(this);
        }
    }

    /**
     * Gets the populator.
     *
     * @param roomPopulatorClass the room populator class
     * @return the populator
     */
    public static NavigatorRoomPopulator getPopulator(String roomPopulatorClass) {

        try {

            Class<? extends NavigatorRoomPopulator> clazz = Class.forName("org.alexdev.icarus.game.navigator.populator." + roomPopulatorClass).asSubclass(NavigatorRoomPopulator.class);
            return clazz.getDeclaredConstructor().newInstance();

        } catch (Exception e) {
        	Log.getErrorLogger().error("Could not load room populator {}", roomPopulatorClass);
        }

        return null;
    }

    /**
     * Gets the child tabs.
     *
     * @return the child tabs
     */
    public List<NavigatorTab> getChildTabs() {

        try {
            return NavigatorManager.getAllTabs().stream().filter(t -> t.childId == this.id).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the child id.
     *
     * @return the child id
     */
    public int getChildId() {
        return childId;
    }

    /**
     * Gets the tab name.
     *
     * @return the tab name
     */
    public String getTabName() {
        return tabName;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the button type.
     *
     * @return the button type
     */
    public byte getButtonType() {
        return buttonType;
    }

    /**
     * Checks if is closed.
     *
     * @return true, if is closed
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Checks if is thumbnail.
     *
     * @return true, if is thumbnail
     */
    public boolean isThumbnail() {
        return thumbnail;
    }

    /**
     * Gets the room populator.
     *
     * @return the room populator
     */
    public NavigatorRoomPopulator getRoomPopulator() {
        return roomPopulator;
    }

}
