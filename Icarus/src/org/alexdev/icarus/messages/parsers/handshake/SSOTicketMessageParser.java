package org.alexdev.icarus.messages.parsers.handshake;

import org.alexdev.icarus.messages.parsers.IncomingMessageParser;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SSOTicketMessageParser implements IncomingMessageParser {

    private String ssoTicket;

    @Override
    public void read(ClientMessage reader) {
        this.ssoTicket = reader.readString();

    }

    public String getSsoTicket() {
        return ssoTicket;
    }

}
