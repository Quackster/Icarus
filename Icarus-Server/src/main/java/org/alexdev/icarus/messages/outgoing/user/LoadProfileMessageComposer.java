package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.headers.Incoming;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.util.date.DateUtil;

import java.util.List;

public class LoadProfileMessageComposer extends MessageComposer {
    private PlayerDetails details;
    private List<Group> memberGroups;
    private boolean isFriend;
    private boolean sentRequest;
    private int friends;

    public LoadProfileMessageComposer(PlayerDetails details, List<Group> memberGroups, boolean isFriend, boolean sentRequest, int friends) {
       this.details = details;
       this.memberGroups = memberGroups;
       this.isFriend = isFriend;
       this.sentRequest = sentRequest;
       this.friends = friends;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(this.details.getId());
        response.writeString(this.details.getName());
        response.writeString(this.details.getFigure());
        response.writeString(this.details.getMission());
        response.writeString(DateUtil.getDateAsString(this.details.getRegisterDate()));
        response.writeInt(this.details.getAchievementPoints());
        response.writeInt(this.friends);
        response.writeBool(this.isFriend);
        response.writeBool(this.sentRequest);
        response.writeBool(PlayerManager.getInstance().hasPlayer(this.details.getId()));
        
        response.writeInt(this.memberGroups.size());
        for (Group group : this.memberGroups) {
            response.writeInt(group.getId());
            response.writeString(group.getTitle());
            response.writeString(group.getBadge());
            response.writeString(group.getColourA());
            response.writeString(group.getColourB());
            response.writeBool(details.getFavouriteGroup() == group.getId());
            response.writeInt(-1);
            response.writeBool(group.hasForum());
        }

        response.writeInt(DateUtil.getCurrentTimeSeconds() - this.details.getLastOnlineDate());
        response.writeBool(true);
    }

    @Override
    public short getHeader() {
        return Incoming.LoadProfileMessageComposer;
    }
}
