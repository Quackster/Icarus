--[[
    The server socket handler for incoming MUS connections
    
    @author: Quackster
--]]
function listenServer() 

    local server_socket = nil

    log:println(string.format("[Rcon] Attempting to create RCON server on port %s", rcon_port))
    server_socket = luajava.newInstance("java.net.ServerSocket", rcon_port);
    
    log:println(string.format("[Rcon] RCON server listening on port %s", rcon_port))
    log:println()
    
    plugin:runTaskAsynchronously(waitForConnections, { server_socket })
end

--[[
    The function where the socket waits for incoming socket connections
    and listens for data.
    
    @author: Quackster
--]]
function waitForConnections(server_socket)

    while (plugin:isClosed() == false) do
        
        local socket = server_socket:accept()
        log:println(string.format("Accepted connection from %s", socket:toString()))

        local incoming_data = util:readToEnd(socket)
        handleRconCommands(incoming_data)
        
        socket:close()
    end
end

--[[
    RCON command handler where it's possible to remote control
    the server
    
    @author: Quackster
--]]
function handleRconCommands(incoming_data) 

    local rcon_data = util:split(incoming_data, ";")
    
    local rcon_password = rcon_data:get(0)
    local rcon_command = rcon_data:get(1)
    local rcon_data = rcon_data:get(2)
    
    log:println("RCON command: " .. rcon_command)
    
    -- Hotel alert command
    if rcon_command == "ha" then
    
        local players = playerManager:getPlayers()

        for i = 0, players:size() - 1 do

            local existingPlayer = players:get(i)
            existingPlayer:sendMessage(rcon_data)
            
        end
    end
end

