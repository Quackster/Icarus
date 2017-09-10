package org.alexdev.icarus.messages.incoming.catalogue;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.catalogue.CataloguePromotionRoomsComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PromotableRoomsMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        player.send(new CataloguePromotionRoomsComposer(player.getRooms()));
        
        /*{id:265}{i:739902}{i:-1}{s:NORMAL}
--------------------
[8cd57bb7ca6f920991554ea36ab615cf]
Incoming[1574, _-6Vt, CatalogPageMessageParser] <- [0][0][0]y[6]&[0][11]J>[0][6]NORMAL[0][7]roomads[0][0][0][2][0][13]events_header[0][0][0][0][0][2][0][0][0][0][0][0][0][1][0][0]-Û[0]room_ad_plus_badge[0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][0][1][0][1]b[0][5]RADZZ[0][0][0][0][0][0][0][0]ÿÿÿÿ[0]
--------------------
[feb84f7e1f131b6a85a963eeb7b9b400]
Outgoing[371, _-WS] -> [0][0][0][2][1]s
--------------------
[d282e1ddb0ae82079771fb7a6a50f3d3]
Outgoing[2403, _-5lh] -> [0][0][0][2][9]c
--------------------
[222904914469f62bf89b993342eaa532]
Incoming[1736, _-1jM, _-3AF] <- 


// len + header
[0][0][0]O[6]È

[0] // false?
[0][0][0][4] // room count

---
[1]E16                         - room Id
[0]winterpartys's  room     - room name
[1]                         - boolean
---
[3]~\ç
[0][10]Testing123
[0]
---
[4]m!Ä
[0][6]111111
[0]
---
[4]cð®
[0][8]testtttt
[0]
--------------------
[3646d612b564f2227b63c12700ae8847]*/     
    }
}
