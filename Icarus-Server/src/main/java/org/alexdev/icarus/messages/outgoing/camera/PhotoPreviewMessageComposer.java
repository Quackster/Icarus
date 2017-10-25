package org.alexdev.icarus.messages.outgoing.camera;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class PhotoPreviewMessageComposer extends MessageComposer {

    private String fileName;

    public PhotoPreviewMessageComposer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.PhotoPreviewMessageComposer);
        response.writeString(this.fileName);
    }

    @Override
    public short getHeader() {
        return Outgoing.PhotoPreviewMessageComposer;
    }
}
