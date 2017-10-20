package org.alexdev.icarus.messages.outgoing.user;

import java.util.Map;
import java.util.Map.Entry;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class ActivityPointsMessageComposer extends MessageComposer {

    private Map<Integer, Integer> currencies;

    public ActivityPointsMessageComposer(Map<Integer, Integer> currencies) {
        this.currencies = currencies;
    }

    @Override
    public void compose(Response response) {

        //response.init(Outgoing.ActivityPointsMessageComposer);
        response.writeInt(this.currencies.size());

        for (Entry<Integer, Integer> set : this.currencies.entrySet()) {
            response.writeInt(set.getKey());
            response.writeInt(set.getValue());
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.ActivityPointsMessageComposer;
    }
}