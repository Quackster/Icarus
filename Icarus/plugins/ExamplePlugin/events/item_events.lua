--[[
	Item interact event called when the user interacts with a wall item
	
	param: 
			Player 	- person who entered room
			Item 	- the item they interacted
			
	return: Boolean - event cancelled state
--]]
function onInteractWallItemEvent(player, item)
	log:println("Interact wall item event called")
	return false
end


--[[
	Item interact event called when the user interacts with a floor item
	
	param: 
			Player 	- person who entered room
			Item 	- the item they interacted
			
	return: Boolean - event cancelled state
--]]
function onInteractFloorItemEvent(player, item)
	log:println("Interact floor item event called")
	return false
end
