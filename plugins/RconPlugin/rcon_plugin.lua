plugin_details = {
    name = "RconPlugin",
    author = "Quackster",
    path = "plugins/RconPlugin"
}

event_files = {
    "rcon_handler.lua",
    "command_handler.lua"
}

rcon_port = 30001
rcon_password = "jellybaby"

--[[
	Called when the plugin first starts up, so the plugin can load data if needed
	so when the event is called the plugin is ready
	
	param: plugin instance
	return: none
--]]
function onEnable()

    -- If you want, use log.println() to show everyone this method being called
    log:info(string.format('[Lua] Initialising plugin %s by %s', plugin:getName(), plugin:getAuthor()))
    listenServer()

end

-- Load all event .lua files
-- If you delete this code, ABSOLUTELY NO events will work
for i, file in ipairs(event_files) do
    dofile (string.format('%s/%s', plugin_details.path, file))
end