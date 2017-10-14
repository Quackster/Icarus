package org.alexdev.icarus.server.netty;

import io.netty.channel.Channel;
import org.alexdev.icarus.encryption.RC4;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.PlayerNetwork;
import org.alexdev.icarus.server.netty.codec.EncryptionDecoder;

public class NettyPlayerNetwork extends PlayerNetwork {

    private Channel channel;

    public NettyPlayerNetwork(Channel channel, int connectionId) {
        super(connectionId);
        this.channel = channel;
    }

    @Override
    public void addPipelineStage(Object object) {

        if (object instanceof RC4) {
            this.channel.pipeline().addBefore("gameDecoder", "gameCrypto", new EncryptionDecoder((RC4)object));
        }
    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public void send(MessageComposer response) {
        channel.writeAndFlush(response);
    }

    @Override
    public void sendQueued(MessageComposer response) {
        channel.write(response);
    }
}
