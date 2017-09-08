package org.alexdev.icarus.messages.outgoing.user;

import java.util.Map;
import java.util.Map.Entry;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class ActivityPointsComposer extends MessageComposer {

    private Map<Integer, Integer> currencies;
    
    public ActivityPointsComposer(Map<Integer, Integer> currencies) {
        this.currencies = currencies;
    }

    @Override
    public void write() {
        
        this.response.init(Outgoing.ActivityPointsComposer);
        this.response.writeInt(this.currencies.size());
        
        for (Entry<Integer, Integer> set : this.currencies.entrySet()) {
            this.response.writeInt(set.getKey());
            this.response.writeInt(set.getValue());
        }
    }

}
