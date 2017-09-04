--[[
	Room enter event called when the user has entered a room
	Called when a user has truly entered the room
	
	param: 
			Player 	- person who entered room
			Room 	- the room they entered
			
	return: Boolean - event cancelled state
--]]
function onRoomEnterEvent(player, room)
	log:println("Room enter event called")

	for i = 0, 10 - 1 do
		local bot = createBot(room)
		randomWalkEntity(bot)
	end
	
	return false
end

function createBot(room) 

	local bot = luajava.newInstance("org.alexdev.icarus.game.bot.Bot");
	bot:getDetails():setName("RandomAlexBot")
	bot:getDetails():setMotto("")
	room:addEntity(bot)
	
	local squareInFront = room:getModel():getDoorLocation():getSquareInFront()
	bot:getRoomUser():warpTo(squareInFront:getX(), squareInFront:getY())

	return bot
end

function randomWalkEntity(entity)
	
	-- local walkableTile = entity:getRoom():getMapping(0):getRandomWalkableTile()
	local randomX = math.random(0, 25) -- walkableTile:getX()
	local randomY = math.random(0, 25)-- walkableTile:getY()
	
	entity:getRoomUser():walkTo(randomX, randomY)
	
	plugin:runTaskLater(1, randomWalkEntity, { entity })
	
end