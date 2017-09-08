command_handlers = {
    ["ha"] = "cmdHotelAlert"
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