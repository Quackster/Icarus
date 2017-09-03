plugin_details = {
	name = "ExamplePlugin",
	author = "Quackster",
	path = "plugins/ExamplePlugin"
}

event_register = {
	-- Player events
	"PLAYER_LOGIN_EVENT",
	"PLAYER_DISCONNECT_EVENT",
	
	-- Room events
	"ROOM_REQUEST_ENTER_EVENT",
	"ROOM_ENTER_EVENT",
	"ROOM_LEAVE_EVENT",
	
	-- Item events
	"WALL_ITEM_INTERACT_EVENT",
	"FLOOR_ITEM_INTERACT_EVENT"
}

event_files = {
	"player_events.lua",
	"room_events.lua",
	"item_events.lua"
}

--[[
	Called when the plugin first starts up, so the plugin can load data if needed
	so when the event is called the plugin is ready
	
	param: plugin instance
	return: none
--]]
function onEnable(plugin)
	
	-- If you want, use log.println() to show everyone this method being called
	log:println(string.format('[Lua] Initialising plugin %s by %s', plugin:getName(), plugin:getAuthor()))
	
end


-- scheduler:runTaskLater(5, test)


-- Load all event .lua files
-- If you delete this code, ABSOLUTELY NO events will work

for i, file in ipairs(event_files) do
	dofile (string.format('%s/events/%s', plugin_details.path, file))
end