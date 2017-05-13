package org.alexdev.icarus.messages.outgoing.navigator;

import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorCategory;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class NavigatorCategories extends OutgoingMessageComposer {
    
    private List<NavigatorCategory> categories;

    public NavigatorCategories(List<NavigatorCategory> list) {
        this.categories = list;
    }

    @Override
    public void write() {
        response.init(Outgoing.NavigatorCategories);
        response.writeInt(4 + this.categories.size());

          for (NavigatorCategory category : this.categories) {
            response.writeString("category__" + category.getName());
        }

        response.writeString("recommended");
        response.writeString("new_ads");
        response.writeString("staffpicks");
        response.writeString("official");
    }
}
