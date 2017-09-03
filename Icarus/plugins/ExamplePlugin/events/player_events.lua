
--[[
	Login event called when the user has successfully authenticated and
	hotel packets were sent
	
	param: Player 	- person who logged in
	return: Boolean - event cancelled state
--]]
function onPlayerLoginEvent(player)
	player:sendMessage("Welcome to Icarus Hotel!\r\rThis is a alpha!");
	log:println("Player login event called")
	
	log:println(playerManager:getPlayers():get(0):toString())
	
	return false -- will do nothing for logged in event, see PlayerTryLoginEvent instead
end

--[[
	Login event called when the user has successfully disconnnected
	hotel packets were sent
	
	param: Player 	- person who disconnected
--]]
function onPlayerDisconnectEvent(player)
	log:println("Player disconnect event called")
	return false -- will do nothing upon disconnection
end