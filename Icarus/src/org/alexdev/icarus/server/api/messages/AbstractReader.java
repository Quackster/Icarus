package org.alexdev.icarus.server.api.messages;

public interface AbstractReader {

	public int readShort();
	public Integer readInt();
	public boolean readIntAsBool();
	public boolean readBoolean();
	public String readString();
	public byte[] readBytes(int len);
	public String getMessageBody();
	public short getMessageId();
}
