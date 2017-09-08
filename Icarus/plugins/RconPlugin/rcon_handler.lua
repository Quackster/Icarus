--[[
    The server socket handler for incoming MUS connections
    
    @author: Quackster
--]]
function listenServer() 

    local rcon_port = 30001
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
        log:println(string.format("Incoming data: %s", incoming_data))
        
        handleRconCommands(incoming_data)
        
        socket:close()
    end
end

function handleRconCommands(incoming_data) 


end

