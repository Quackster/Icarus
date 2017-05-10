package org.alexdev.icarus.messages.parsers.handshake;

import org.alexdev.icarus.messages.parsers.IncomingMessageParser;
import org.alexdev.icarus.server.messages.AbstractReader;

public class SSOTicketMessageParser implements IncomingMessageParser {

	private String ssoTicket;

	@Override
	public void read(AbstractReader reader) {
		this.ssoTicket = reader.readString();

	}

	public String getSsoTicket() {
		return ssoTicket;
	}

}
