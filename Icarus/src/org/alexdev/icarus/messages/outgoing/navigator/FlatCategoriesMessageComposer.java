package org.alexdev.icarus.messages.outgoing.navigator;

import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorCategory;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class FlatCategoriesMessageComposer extends MessageComposer {

    private List<NavigatorCategory> categories;

    public FlatCategoriesMessageComposer(List<NavigatorCategory> list) {
        this.categories = list;
    }

    @Override
    public void write() {
        
        this.response.init(Outgoing.FlatCategoriesMessageComposer);
        this.response.writeInt(this.categories.size());

        for (NavigatorCategory category : this.categories) {
            this.response.writeInt(category.getId());
            this.response.writeString(category.getName());
            this.response.writeBool(true); // show category?
            this.response.writeBool(false); // no idea
            this.response.writeString("NONE");
            this.response.writeString("");
            this.response.writeBool(false);
        }
    }
}
