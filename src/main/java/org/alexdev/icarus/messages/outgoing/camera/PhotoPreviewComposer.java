package org.alexdev.icarus.messages.outgoing.camera;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class PhotoPreviewComposer extends MessageComposer {

    private String fileName;

    public PhotoPreviewComposer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.PhotoPreviewComposer);
        this.response.writeString(this.fileName);
    }

}
