package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class SendPerkAllowancesMessageComposer extends MessageComposer {
    
    @Override
    public void write() {
        this.response.init(Outgoing.SendPerkAllowancesMessageComposer);
        this.response.writeInt(15); // Count
        this.response.writeString("USE_GUIDE_TOOL");
        this.response.writeString("");
        this.response.writeBool(false);
        this.response.writeString("GIVE_GUIDE_TOURS");
        this.response.writeString("requirement.unfulfilled.helper_le");
        this.response.writeBool(false);
        this.response.writeString("JUDGE_CHAT_REVIEWS");
        this.response.writeString(""); // ??
        this.response.writeBool(true);
        this.response.writeString("VOTE_IN_COMPETITIONS");
        this.response.writeString(""); // ??
        this.response.writeBool(true);
        this.response.writeString("CALL_ON_HELPERS");
        this.response.writeString(""); // ??
        this.response.writeBool(false);
        this.response.writeString("CITIZEN");
        this.response.writeString(""); // ??
        this.response.writeBool(true);
        this.response.writeString("TRADE");
        this.response.writeString(""); // ??
        this.response.writeBool(true);
        this.response.writeString("HEIGHTMAP_EDITOR_BETA");
        this.response.writeString(""); // ??
        this.response.writeBool(false);
        this.response.writeString("EXPERIMENTAL_CHAT_BETA");
        this.response.writeString("requirement.unfulfilled.helper_level_2");
        this.response.writeBool(true);
        this.response.writeString("EXPERIMENTAL_TOOLBAR");
        this.response.writeString(""); // ??
        this.response.writeBool(true);
        this.response.writeString("BUILDER_AT_WORK");
        this.response.writeString(""); // ??
        this.response.writeBool(true);
        this.response.writeString("NAVIGATOR_PHASE_ONE_2014");
        this.response.writeString(""); // ??
        this.response.writeBool(false);
        this.response.writeString("CAMERA");
        this.response.writeString(""); // ??
        this.response.writeBool(true);
        this.response.writeString("NAVIGATOR_PHASE_TWO_2014");
        this.response.writeString(""); // ??
        this.response.writeBool(true);
        this.response.writeString("MOUSE_ZOOM");
        this.response.writeString(""); // ??
        this.response.writeBool(true);
        this.response.writeString("NAVIGATOR_ROOM_THUMBNAIL_CAMERA");
        this.response.writeString(""); // ??
        this.response.writeBool(false);
        
    }
}
