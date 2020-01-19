package org.alexdev.icarus.game.messenger;

import java.util.List;

import org.alexdev.icarus.dao.mysql.messenger.MessengerDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerUpdateMessageComposer;
import org.alexdev.icarus.messages.types.MessageComposer;

public class Messenger {

    private Player player;
    private List<MessengerUser> friends;
    private List<MessengerUser> requests;

    public Messenger(Player player) {
        this.player = player;
    }

    public void init() {
        this.friends = MessengerDao.getFriends(this.player.getEntityId());
        this.requests = MessengerDao.getRequests(this.player.getEntityId());
    }

    /**
     * Checks for request.
     *
     * @param id the id
     * @return true, if successful
     */
    public boolean hasRequest(int id) {
        return this.getRequest(id) != null;
    }

    /**
     * Checks if is friend.
     *
     * @param id the id
     * @return true, if is friend
     */
    public boolean isFriend(int id) {
        return this.getFriend(id) != null;
    }

    /**
     * Gets the friend.
     *
     * @param id the id
     * @return the friend
     */
    public MessengerUser getFriend(int id) {
        return this.friends.stream().filter(f -> f.getDetails().getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the request.
     *
     * @param id the id
     * @return the request
     */
    public MessengerUser getRequest(int id) {
        return this.requests.stream().filter(f -> f.getDetails().getId() == id)
                .findFirst()
                .orElse(null);
    }


    /**
     * Removes the friend.
     *
     * @param id the id
     */
    public void removeFriend(int id) {
        MessengerUser user = this.getFriend(id);
        this.friends.remove(user);
    }

    /**
     * Send status, this includes if they're in room and/or logged off.
     *
     * @param forceOffline whether or not we force ourselves to have offline status
     */
    public void sendStatus(boolean forceOffline) {

        MessageComposer message = new MessengerUpdateMessageComposer(new MessengerUser(this.player.getEntityId()), forceOffline);

        for (MessengerUser friend : this.friends) {
            if (friend.isUserOnline() ) {
                friend.getPlayer().send(message);
            }
        }
    }

    /**
     * Dispose.
     */
    public void dispose() {
        this.sendStatus(false);
        this.destroyObjects();
    }

    /**
     * Destroy objects.
     */
    private void destroyObjects() {
        this.friends = null;
        this.requests = null;
        this.player = null;
    }

    /**
     * Gets the friends.
     *
     * @return the friends
     */
    public List<MessengerUser> getFriends() {
        return friends;
    }

    /**
     * Gets the requests.
     *
     * @return the requests
     */
    public List<MessengerUser> getRequests() {
        return requests;
    }
}
