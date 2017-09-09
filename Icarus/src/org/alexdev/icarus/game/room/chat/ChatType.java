package org.alexdev.icarus.game.room.chat;

import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.messages.headers.Outgoing;

public enum ChatType {
    CHAT,
    SHOUT,
    WHISPER;

    public int getHeader() {
        
        if (this == CHAT) {
            return Outgoing.ChatMessageComposer;
        }
        
        if (this == SHOUT) {
            return Outgoing.ShoutMessageComposer;
        }
        
        if (this == WHISPER) {
            return Outgoing.WhisperMessageComposer;
        }
        
        return -1;
    }

    public PluginEvent getEvent() {

        if (this == CHAT) {
            return PluginEvent.ROOM_PLAYER_CHAT_EVENT;
        }
        
        if (this == SHOUT) {
            return PluginEvent.ROOM_PLAYER_SHOUT_EVENT;
        }
        
        if (this == WHISPER) {
            return PluginEvent.ROOM_PLAYER_WHISPER_EVENT;
        }
        
        return null;
    }
}
