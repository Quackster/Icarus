package org.alexdev.icarus.game.messenger;

import java.util.List;
import java.util.Optional;

import org.alexdev.icarus.dao.mysql.player.MessengerDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerUpdateMessageComposer;

public class Messenger {

    private Player player;
    private List<MessengerUser> friends;
    private List<MessengerUser> requests;

    public Messenger(Player player) {
        this.player = player;
    }

    public void init() {
        this.friends = MessengerDao.getFriends(this.player.getDetails().getId());
        this.requests = MessengerDao.getRequests(this.player.getDetails().getId());
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

        Optional<MessengerUser> friend = this.friends.stream().filter(f -> f.getDetails().getId() == id).findFirst();

        if (friend.isPresent()) {
            return friend.get();
        } else {
            return null;
        }
    }

    /**
     * Gets the request.
     *
     * @param id the id
     * @return the request
     */
    public MessengerUser getRequest(int id) {

        Optional<MessengerUser> request = this.requests.stream().filter(f -> f.getDetails().getId() == id).findFirst();

        if (request.isPresent()) {
            return request.get();
        } else {
            return null;
        }
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

        MessengerUpdateMessageComposer message = new MessengerUpdateMessageComposer(new MessengerUser(this.player.getDetails().getId()), forceOffline);

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
