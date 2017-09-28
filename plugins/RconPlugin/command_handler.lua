command_handlers = {
    ["ha"] = "cmdHotelAlert",
    ["reloadoffers"] = "cmdReloadOffers"
}

--[[
    This is to send hotel alerts remotely.
    
    @parameters: 
        rcon_data - split by ';' delimeter

    Handler for RCON command    : ha
    Syntax                      : password;command;message
    Example                     : password;ha;Hotel alert test!
--]]
function cmdHotelAlert(rcon_data) 

    local message = rcon_data:get(2)
    local players = playerManager:getPlayers()

    for i = 0, players:size() - 1 do
        local player = players:get(i)
        player:sendMessage(message)
    end
end

--[[
    Reload offers when being edited by housekeeping.
    
    @parameters: 
        rcon_data - split by ';' delimeter

    Handler for RCON command    : reloadoffers
    Syntax                      : password;command;
    Example                     : password;reloadoffers;
--]]
function cmdReloadOffers(rcon_data)
    
    log:info(" [Rcon] Catalogue offers reloaded")

    catalogueManager:reloadOffers()
    
    local players = playerManager:getPlayers()
    
    for i = 0, players:size() - 1 do
        local player = players:get(i)
        
        if player:getDetails():getID() >= 5 then
            player:sendMessage(" [Rcon] Catalogue offers reloaded")
        end
    end
end