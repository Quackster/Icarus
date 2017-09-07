package org.alexdev.icarus.messages.outgoing.navigator;

import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorCategory;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class NavigatorCategories extends MessageComposer {
    
    private List<NavigatorCategory> categories;

    public NavigatorCategories(List<NavigatorCategory> list) {
        this.categories = list;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.NavigatorCategories);
        this.response.writeInt(4 + this.categories.size());

          for (NavigatorCategory category : this.categories) {
            this.response.writeString("category__" + category.getName());
        }

        this.response.writeString("recommended");
        this.response.writeString("new_ads");
        this.response.writeString("staffpicks");
        this.response.writeString("official");
    }
}
