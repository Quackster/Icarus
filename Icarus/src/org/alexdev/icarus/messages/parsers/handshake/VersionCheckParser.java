package org.alexdev.icarus.messages.parsers.handshake;

import org.alexdev.icarus.messages.parsers.IncomingMessageParser;
import org.alexdev.icarus.server.api.messages.AbstractReader;

public class VersionCheckParser implements IncomingMessageParser {

	private String version;
	
	@Override
	public void read(AbstractReader reader) {
		this.version = reader.readString();
	}

	public String getVersion() {
		return version;
	}
}
