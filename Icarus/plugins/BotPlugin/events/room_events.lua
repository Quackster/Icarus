--[[
	Room enter event called when the user has entered a room
	Called when a user has truly entered the room
	
	param: 
			Player 	- person who entered room
			Room 	- the room they entered
			
	return: Boolean - event cancelled state
--]]
function onRoomFirstEntryEvent(player, room)
	log:println("Room enter event called")

	if room:getData():getId() == 5 then
		local bot = createBot(room)
		randomWalkEntity(bot)
		randomDance(bot)
	end
	
	return false
end

function createBot(room) 

	local bot = luajava.newInstance("org.alexdev.icarus.game.bot.Bot");
	bot:getDetails():setName(string.format("RandomAlexBot%s", math.random(1000)))
	bot:getDetails():setMotto("")
	room:addEntity(bot)
	
	local startPosition = room:getModel():getDoorLocation():getSquareInFront()
	bot:getRoomUser():warpTo(startPosition:getX(), squareInFront:getY())

	return bot
end

function randomWalkEntity(entity)

	if entity:isDisposed() then 
		do return end
	end

	local randomX = math.random(0, 25)
	local randomY = math.random(0, 25)
	
	entity:getRoomUser():walkTo(randomX, randomY)
	
	plugin:runTaskLater(1, randomWalkEntity, { entity })
	
end

function randomDance(entity)
	entity:getRoomUser():startDancing(math.random(0, 4))
	plugin:runTaskLater(20, randomDance, { entity })
end