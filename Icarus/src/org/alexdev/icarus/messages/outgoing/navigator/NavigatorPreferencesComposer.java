package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class NavigatorPreferencesComposer extends OutgoingMessageComposer {

	@Override
	public void write() {
		this.response.init(Outgoing.NavigatorPreferencesComposer);
		this.response.writeInt(50);//pref.NewnaviX);
        this.response.writeInt(50);//pref.NewnaviY);
        this.response.writeInt(580);//pref.NewnaviWidth);
        this.response.writeInt(600);//pref.NewnaviHeight);
        this.response.writeInt(true);
        this.response.writeInt(1);

	}

}
