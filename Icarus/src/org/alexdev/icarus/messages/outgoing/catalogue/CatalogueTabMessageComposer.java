package org.alexdev.icarus.messages.outgoing.catalogue;

import java.util.List;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.CatalogueTab;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class CatalogueTabMessageComposer implements OutgoingMessageComposer {

	private String type;
	private List<CatalogueTab> parentTabs;
	//private int parentId;
	private int rank;

	public CatalogueTabMessageComposer(String type, List<CatalogueTab> parentTabs, int parentId, int rank) {
		this.type = type;
		this.parentTabs = parentTabs;
		//this.parentId = parentId;
		this.rank = rank;
	}
	
	@Override
	public void write (AbstractResponse response) {
		
		response.init(Outgoing.CatalogueTabMessageComposer);
		response.appendBoolean(true);
		response.appendInt32(0);
		response.appendInt32(-1);
		response.appendString("root");
		response.appendString("");
		response.appendInt32(0);
		response.appendInt32(this.parentTabs.size());
		
		for (CatalogueTab parentTab : this.parentTabs) {
			
			response.appendBoolean(parentTab.isEnabled());
			response.appendInt32(parentTab.getIconImage());
			response.appendInt32(parentTab.getId());
			response.appendString(parentTab.getCaption().toLowerCase().replace(" ", "_"));
			response.appendString(parentTab.getCaption());
			response.appendInt32(0); // TODO: flat offers
			
			List<CatalogueTab> childTabs = CatalogueManager.getChildTabs(parentTab.getId(), this.rank);
			
			response.appendInt32(childTabs.size());
			
			for (CatalogueTab childTab : childTabs) {
				
				response.appendBoolean(childTab.isEnabled());
				response.appendInt32(childTab.getIconImage());
				response.appendInt32(childTab.getId());
				response.appendString(childTab.getCaption().toLowerCase().replace(" ", "_"));
				response.appendString(childTab.getCaption());
				response.appendInt32(0);
				response.appendInt32(0);
			}
		}
		
		response.appendBoolean(false);
		response.appendString(type);
	}
}
