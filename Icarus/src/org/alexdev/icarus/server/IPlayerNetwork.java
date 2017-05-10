package org.alexdev.icarus.server;

import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public abstract class IPlayerNetwork {

	private int connectionId;
	
	public IPlayerNetwork(int connectionId) {
		this.connectionId = connectionId;
	}
	
	public abstract void send(OutgoingMessageComposer response);
	public abstract void close();
	
	public int getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}
}
