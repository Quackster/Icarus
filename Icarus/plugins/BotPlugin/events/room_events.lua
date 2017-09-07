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
        -- for i = 0, 1000 - 1 do    
        --    local bot = createBot(room)
        --    randomWalkEntity(bot)
        --    randomDance(bot)
        -- end
	end
	
	return false
end

function createBot(room) 

	local bot = luajava.newInstance("org.alexdev.icarus.game.bot.Bot");
	bot:getDetails():setName("Cuthbert")
	bot:getDetails():setMotto("To greet all the new peoples!")
	bot:getDetails():setFigure("lg-285-64.hr-831-61.hd-180-7.ea-3170-63.ch-3077-1408-90.sh-3089-1335")
	room:addEntity(bot)
	
	local startPosition = room:getMapping():getRandomWalkableTile()
	bot:getRoomUser():warpTo(startPosition:getX(), startPosition:getY())

	return bot
end

function randomWalkEntity(entity)

	if entity:isDisposed() then
		log:println("entity removed")
		-- entity:getRoomUser():getRoom():removeEntity(entity)
	else

		local randomX = math.random(0, 25)
		local randomY = math.random(0, 25)
		
		entity:getRoomUser():walkTo(randomX, randomY)
		
		plugin:runTaskLater(1, randomWalkEntity, { entity })
	
	end
	
end

function randomDance(entity)
	entity:getRoomUser():startDancing(math.random(0, 4))
	plugin:runTaskLater(20, randomDance, { entity })
end
