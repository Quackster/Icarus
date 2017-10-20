package org.alexdev.icarus.messages.outgoing.groups;

import java.util.List;
import java.util.Map;

import org.alexdev.icarus.game.groups.types.GroupBackgroundColour;
import org.alexdev.icarus.game.groups.types.GroupBase;
import org.alexdev.icarus.game.groups.types.GroupBaseColour;
import org.alexdev.icarus.game.groups.types.GroupSymbol;
import org.alexdev.icarus.game.groups.types.GroupSymbolColour;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;

public class GroupBadgeDialogComposer extends MessageComposer {

    private List<GroupBase> bases;
    private List<GroupSymbol> symbols;
    private List<GroupBaseColour> baseColours;
    private Map<Integer, GroupSymbolColour> symbolColours;
    private Map<Integer, GroupBackgroundColour> backgroundColours;

    public GroupBadgeDialogComposer(List<GroupBase> bases, List<GroupSymbol> symbols, List<GroupBaseColour> baseColours, Map<Integer, GroupSymbolColour> symbolColours, Map<Integer, GroupBackgroundColour> backgroundColours) {
        this.bases = bases;
        this.symbols = symbols;
        this. baseColours = baseColours;
        this.symbolColours = symbolColours;
        this.backgroundColours = backgroundColours;
    }

    @Override
    public void compose(Response response) {

        response.writeInt(this.bases.size());
        for (GroupBase base : bases) {
            response.writeInt(base.getId());
            response.writeString(base.getValueA());
            response.writeString(base.getValueB());
        }

        response.writeInt(symbols.size());
        for (GroupSymbol symbol : symbols) {
            response.writeInt(symbol.getId());
            response.writeString(symbol.getValueA());
            response.writeString(symbol.getValueB());
        }

        response.writeInt(baseColours.size());
        for (GroupBaseColour colour : baseColours) {
            response.writeInt(colour.getId());
            response.writeString(colour.getColour());
        }

        response.writeInt(symbolColours.size());
        for (GroupSymbolColour colour : symbolColours.values()) {
            response.writeInt(colour.getId());
            response.writeString(colour.getColour());
        }

        response.writeInt(backgroundColours.size());
        for (GroupBackgroundColour colour : backgroundColours.values()) {
            response.writeInt(colour.getId());
            response.writeString(colour.getColour());
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.GroupBadgeDialogComposer;
    }
}
