package org.alexdev.icarus.messages.outgoing.navigator;

import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorCategory;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class NavigatorCategories extends MessageComposer {
    
    private List<NavigatorCategory> categories;

    public NavigatorCategories(List<NavigatorCategory> list) {
        this.categories = list;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.NavigatorCategories);
        response.writeInt(4 + this.categories.size());

        for (NavigatorCategory category : this.categories) {
            response.writeString("category__" + category.getName());
        }

        response.writeString("recommended");
        response.writeString("new_ads");
        response.writeString("staffpicks");
        response.writeString("official");
    }

    @Override
    public short getHeader() {
        return Outgoing.NavigatorCategories;
    }
}