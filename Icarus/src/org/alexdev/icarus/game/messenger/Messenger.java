package org.alexdev.icarus.game.messenger;

import java.util.List;
import java.util.Optional;

import org.alexdev.icarus.dao.mysql.player.MessengerDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerUpdateMessageComposer;

public class Messenger {

    private boolean initalised;
    private Player player;

    private List<MessengerUser> friends;
    private List<MessengerUser> requests;

    public Messenger(Player player) {
        this.player = player;
        this.initalised = false;
    }

    public void init() {
        this.friends = MessengerDao.getFriends(this.player.getDetails().getId());
        this.requests = MessengerDao.getRequests(this.player.getDetails().getId());
    }

    public boolean hasRequest(int id) {
        return this.getRequest(id) != null;
    }

    public boolean isFriend(int id) {
        return this.getFriend(id) != null;
    }

    public MessengerUser getFriend(int id) {

        Optional<MessengerUser> friend = this.friends.stream().filter(f -> f.getDetails().getId() == id).findFirst();

        if (friend.isPresent()) {
            return friend.get();
        } else {
            return null;
        }
    }

    public MessengerUser getRequest(int id) {

        Optional<MessengerUser> request = this.requests.stream().filter(f -> f.getDetails().getId() == id).findFirst();

        if (request.isPresent()) {
            return request.get();
        } else {
            return null;
        }
    }


    public void removeFriend(int id) {
        MessengerUser user = this.getFriend(id);
        this.friends.remove(user);
    }

    public void sendStatus(boolean forceOffline) {

        MessengerUpdateMessageComposer message = new MessengerUpdateMessageComposer(new MessengerUser(this.player.getDetails().getId()), forceOffline);

        for (MessengerUser friend : this.friends) {
            if (friend.isUserOnline()) {
                if (friend.getPlayer().getMessenger().hasInitalised()) {
                    friend.getPlayer().send(message);
                }
            }
        }
    }

    public void dispose() {
        this.sendStatus(false);
        this.destroyObjects();
    }

    private void destroyObjects() {
        this.friends = null;
        this.requests = null;
        this.player = null;
    }

    public List<MessengerUser> getFriends() {
        return friends;
    }

    public List<MessengerUser> getRequests() {
        return requests;
    }

    public boolean hasInitalised() {
        return initalised;
    }

    public void setInitalised(boolean initalised) {
        this.initalised = initalised;
    }
}
