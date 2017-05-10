package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class SendPerkAllowancesMessageComposer implements OutgoingMessageComposer {
	
	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.SendPerkAllowancesMessageComposer);
		response.appendInt32(11);
		response.appendString("BUILDER_AT_WORK");
		response.appendString("");
		response.appendBoolean(false);
		response.appendString("VOTE_IN_COMPETITIONS");
		response.appendString("requirement.unfulfilled.helper_level_2");
		response.appendBoolean(false);
		response.appendString("USE_GUIDE_TOOL");
		response.appendString("requirement.unfulfilled.helper_level_4");
		response.appendBoolean(true);
		response.appendString("JUDGE_CHAT_REVIEWS");
		response.appendString("requirement.unfulfilled.helper_level_6");
		response.appendBoolean(false);
		response.appendString("NAVIGATOR_ROOM_THUMBNAIL_CAMERA");
		response.appendString("");
		response.appendBoolean(true);
		response.appendString("CALL_ON_HELPERS");
		response.appendString("");
		response.appendBoolean(true);
		response.appendString("CITIZEN");
		response.appendString("");
		response.appendBoolean(true);//fff
		response.appendString("MOUSE_ZOOM");
		response.appendString("");
		response.appendBoolean(false);
		response.appendString("TRADE");
		response.appendString("requirement.unfulfilled.no_trade_lock");
		response.appendBoolean(false);
		response.appendString("CAMERA");
		response.appendString("");
		response.appendBoolean(false);
		response.appendString("NAVIGATOR_PHASE_TWO_2014");
		response.appendString("");
		response.appendBoolean(true);
		
	}
}
