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

    public static NavigatorRoomPopulator getPopulator(String roomPopulatorClass) {

        try {

            Class<? extends NavigatorRoomPopulator> clazz = Class.forName("org.alexdev.icarus.game.navigator.populator." + roomPopulatorClass).asSubclass(NavigatorRoomPopulator.class);
            return clazz.newInstance();

        } catch (Exception e) {
            Log.exception(e);
        }

        return null;
    }

    public List<NavigatorTab> getChildTabs() {

        try {
            return NavigatorManager.getAllTabs().stream().filter(t -> t.childId == this.id).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public int getChildId() {
        return childId;
    }

    public String getTabName() {
        return tabName;
    }

    public String getTitle() {
        return title;
    }

    public byte getButtonType() {
        return buttonType;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isThumbnail() {
        return thumbnail;
    }

    public NavigatorRoomPopulator getRoomPopulator() {
        return roomPopulator;
    }

}
