--[[
	Messenger message enter event called when the user privately messages a friend
	
	param: 
			Player 				- person who messaged
			MessengerMessage 	- the message instance to edit
			
	return: Boolean - event cancelled state
--]]
function onMessengerTalkEvent(player, msg)
	log:println("Messenger message event called")

	-- Override message
	-- 		msg:setMessage("this message has been overridden")
	
	-- Send a fake message
	-- 		scheduler:runTaskLater(20, sendFakeMessage, { msg:getFromId(), msg:getFriend() })
	
	return false
end


--[[function sendFakeMessage(from_id, friend) 
	
	local message = "Hello, this message has been delayed by 20 seconds"
	local composer = util:getComposer("MessengerMessageComposer")
	local packet = luajava.newInstance(composer, from_id, message)
	
	friend:send(packet)
end--]