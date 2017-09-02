
--[[
	Login event called when the user has successfully authenticated and
	hotel packets were sent
	
	param: Player 	- person who logged in
	return: Boolean - event cancelled state
--]]
function onPlayerLoginEvent(player)

	player:sendMessage("Welcome to Icarus Hotel!\r\rThis is a alpha!");
	return false
end

--[[
	Room enter event called when the user has entered a room
	
	param: 
			Player 	- person who logged in
			Room 	- the room they entered
			
	return: Boolean - event cancelled state
--]]
function onRoomEnterEvent(player, room)

	return false
end