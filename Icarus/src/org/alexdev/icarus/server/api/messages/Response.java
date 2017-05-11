package org.alexdev.icarus.server.api.messages;

public interface Response {

	public void init(int id);
	public void writeString(Object obj);
	public void writeInt(Integer obj);
	public void writeInt(Boolean obj);
	public void writeShort(int obj);
	public void writeBool(Boolean obj);
	public String getBodyString();
	public Object get();
	public int getHeader();

}
