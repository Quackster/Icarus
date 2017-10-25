package org.alexdev.icarus.game.room.user;

import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.messages.headers.Outgoing;

public enum ChatType {
    
    CHAT,
    SHOUT,
    WHISPER;

    /**
     * Gets the chat header.
     *
     * @return the chat header
     */
    public short getHeader() {
        
        if (this == CHAT) {
            return Outgoing.ChatMessageComposer;
        } else if (this == SHOUT) {
            return Outgoing.ShoutMessageComposer;
        } else if (this == WHISPER) {
            return Outgoing.WhisperMessageComposer;
        }
        
        return -1;
    }

    /**
     * Gets the event.
     *
     * @return the event
     */
    public PluginEvent getEvent() {
        
        if (this == CHAT) {
            return PluginEvent.ROOM_PLAYER_CHAT_EVENT;
        } else if (this == SHOUT) {
            return PluginEvent.ROOM_PLAYER_SHOUT_EVENT;
        }else if (this == WHISPER) {
            return PluginEvent.ROOM_PLAYER_WHISPER_EVENT;
        }
        
        return null;
    }
}
