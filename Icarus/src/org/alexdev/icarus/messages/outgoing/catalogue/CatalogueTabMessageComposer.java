package org.alexdev.icarus.messages.outgoing.catalogue;

import java.util.List;

import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.CatalogueTab;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class CatalogueTabMessageComposer implements OutgoingMessageComposer {

	private String type;
	private List<CatalogueTab> parentTabs;
	private int rank;

	public CatalogueTabMessageComposer(String type, List<CatalogueTab> parentTabs, int parentId, int rank) {
		this.type = type;
		this.parentTabs = parentTabs;
		this.rank = rank;
	}
	
	@Override
	public void write(Response response) {
		
		response.init(Outgoing.CatalogueTabMessageComposer);
		response.writeBool(true);
		response.writeInt(0);
		response.writeInt(-1);
		response.writeString("root");
		response.writeString("");
		response.writeInt(0);
		response.writeInt(this.parentTabs.size());
		
		for (CatalogueTab parentTab : this.parentTabs) {
			
			response.writeBool(parentTab.isEnabled());
			response.writeInt(parentTab.getIconImage());
			response.writeInt(parentTab.getId());
			response.writeString(parentTab.getCaption().toLowerCase().replace(" ", "_"));
			response.writeString(parentTab.getCaption());
			response.writeInt(0); // TODO: flat offers
			
			List<CatalogueTab> childTabs = CatalogueManager.getChildTabs(parentTab.getId(), this.rank);
			
			response.writeInt(childTabs.size());
			
			for (CatalogueTab childTab : childTabs) {
				
				response.writeBool(childTab.isEnabled());
				response.writeInt(childTab.getIconImage());
				response.writeInt(childTab.getId());
				response.writeString(childTab.getCaption().toLowerCase().replace(" ", "_"));
				response.writeString(childTab.getCaption());
				response.writeInt(0);
				response.writeInt(0);
			}
		}
		
		response.writeBool(false);
		response.writeString(type);
	}
}
