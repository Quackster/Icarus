package org.alexdev.icarus.messages.outgoing.groups;

import java.util.List;
import java.util.Map;

import org.alexdev.icarus.game.groups.types.GroupBackgroundColour;
import org.alexdev.icarus.game.groups.types.GroupBase;
import org.alexdev.icarus.game.groups.types.GroupBaseColour;
import org.alexdev.icarus.game.groups.types.GroupSymbol;
import org.alexdev.icarus.game.groups.types.GroupSymbolColour;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class GroupBadgeDialogComposer extends MessageComposer {

    private List<GroupBase> bases;
    private List<GroupSymbol> symbols;
    private List<GroupBaseColour> baseColours;
    private Map<Integer, GroupSymbolColour> symbolColours;
    private Map<Integer, GroupBackgroundColour> backgroundColours;
    
    public GroupBadgeDialogComposer(List<GroupBase> bases, List<GroupSymbol> symbols, List<GroupBaseColour> baseColours, Map<Integer, GroupSymbolColour> symbolColours, Map<Integer, GroupBackgroundColour> backgroundColours) {
        this.bases = bases;
        this.symbols = symbols;
        this.baseColours = baseColours;
        this.symbolColours = symbolColours;
        this.backgroundColours = backgroundColours;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.GroupBadgeDialogComposer);
        this.response.writeInt(this.bases.size());

        for (GroupBase base : this.bases) {
            this.response.writeInt(base.getId());
            this.response.writeString(base.getValueA());
            this.response.writeString(base.getValueB());
        }

        this.response.writeInt(this.symbols.size());

        for (GroupSymbol symbol : this.symbols) {
            this.response.writeInt(symbol.getId());
            this.response.writeString(symbol.getValueA());
            this.response.writeString(symbol.getValueB());
        }

        this.response.writeInt(this.baseColours.size());

        for (GroupBaseColour colour : this.baseColours) {
            this.response.writeInt(colour.getId());
            this.response.writeString(colour.getColour());
        }

        this.response.writeInt(this.symbolColours.size());

        for (GroupSymbolColour colour : this.symbolColours.values()) {
            this.response.writeInt(colour.getId());
            this.response.writeString(colour.getColour());
        }

        this.response.writeInt(this.backgroundColours.size());

        for (GroupBackgroundColour colour : this.backgroundColours.values()) {
            this.response.writeInt(colour.getId());
            this.response.writeString(colour.getColour());
        }
    }

}
