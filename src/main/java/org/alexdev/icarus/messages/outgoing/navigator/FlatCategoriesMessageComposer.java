package org.alexdev.icarus.messages.outgoing.navigator;

import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorCategory;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FlatCategoriesMessageComposer extends MessageComposer {

    private List<NavigatorCategory> categories;

    public FlatCategoriesMessageComposer(List<NavigatorCategory> list) {
        this.categories = list;
    }

    @Override
    public void compose(Response response) {

        //response.init(Outgoing.FlatCategoriesMessageComposer);
        response.writeInt(this.categories.size());

        for (NavigatorCategory category : this.categories) {
            response.writeInt(category.getId());
            response.writeString(category.getName());
            response.writeBool(true); // show category?
            response.writeBool(false); // no idea
            response.writeString("NONE");
            response.writeString("");
            response.writeBool(false);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.FlatCategoriesMessageComposer;
    }
}