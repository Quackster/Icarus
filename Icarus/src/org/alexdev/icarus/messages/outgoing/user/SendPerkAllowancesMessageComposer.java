package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class SendPerkAllowancesMessageComposer extends OutgoingMessageComposer {
	
	@Override
	public void write() {
		response.init(Outgoing.SendPerkAllowancesMessageComposer);
		response.writeInt(11);
		response.writeString("BUILDER_AT_WORK");
		response.writeString("");
		response.writeBool(false);
		response.writeString("VOTE_IN_COMPETITIONS");
		response.writeString("requirement.unfulfilled.helper_level_2");
		response.writeBool(false);
		response.writeString("USE_GUIDE_TOOL");
		response.writeString("requirement.unfulfilled.helper_level_4");
		response.writeBool(true);
		response.writeString("JUDGE_CHAT_REVIEWS");
		response.writeString("requirement.unfulfilled.helper_level_6");
		response.writeBool(false);
		response.writeString("NAVIGATOR_ROOM_THUMBNAIL_CAMERA");
		response.writeString("");
		response.writeBool(true);
		response.writeString("CALL_ON_HELPERS");
		response.writeString("");
		response.writeBool(true);
		response.writeString("CITIZEN");
		response.writeString("");
		response.writeBool(true);//fff
		response.writeString("MOUSE_ZOOM");
		response.writeString("");
		response.writeBool(false);
		response.writeString("TRADE");
		response.writeString("requirement.unfulfilled.no_trade_lock");
		response.writeBool(false);
		response.writeString("CAMERA");
		response.writeString("");
		response.writeBool(false);
		response.writeString("NAVIGATOR_PHASE_TWO_2014");
		response.writeString("");
		response.writeBool(true);
		
	}
}
