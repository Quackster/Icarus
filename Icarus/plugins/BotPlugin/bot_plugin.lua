plugin_details = {
	name = "BotPlugin",
	author = "Quackster",
	path = "plugins/BotPlugin"
}

event_register = {
	"ROOM_FIRST_ENTRY_EVENT"
}

event_files = {
	"room_events.lua"
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

-- Load all event .lua files
-- If you delete this code, ABSOLUTELY NO events will work

for i, file in ipairs(event_files) do
	dofile (string.format('%s/events/%s', plugin_details.path, file))
end