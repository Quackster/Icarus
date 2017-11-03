<!DOCTYPE HTML>
<html>
<title>{{ site.name }}: Hotel</title>
<head>
    <link rel="stylesheet" type="text/css" href="{{ site.url }}/api/hotel.731d1960.css">
    <script type="text/javascript" src="{{ site.url }}/api/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="{{ site.url }}/api/swfobject.js"></script>
    <script type="text/javascript">
        var flashvars = {
            "client.allow.cross.domain": "1",
            "client.notify.cross.domain": "0",
            "connection.info.host": "{{ client.ip }}",
            "connection.info.port": "{{ client.port }}",
            "site.url": "{{ site.url }}/",
            "url.prefix": "{{ site.url }}",
            "client.reload.url": "/disconnected",
            "client.fatal.error.url": "{{ site.url }}/disconnected",
            "client.connection.failed.url": "{{ site.url }}/disconnected",
            "external.variables.txt": "{{ client.externalVariables }}",
            "external.texts.txt": "{{ client.externalFlashTexts }}",
            "external.override.texts.txt": "{{ client.externalOverrideTexts }}",
            "external.override.variables.txt": "{{ client.externalOverrideVariables }}",
            "productdata.load.url": "{{ client.productdata }}",
            "furnidata.load.url": "{{ client.furnidata }}",
            "use.sso.ticket": "1",
            "sso.ticket": "{{ ticket }}",
            "processlog.enabled": "0",
            "account_id": "1",
            "client.starting": "Hotel is loading, please wait.",
			"client.starting.revolving":"For science, you monster\/Loading funny message\u2026please wait.\/Would you like fries with that?\/Follow the yellow duck.\/Time is just an illusion.\/Are we there yet?!\/I like your t-shirt.\/Look left. Look right. Blink twice. Ta da!\/It\'s not you, it\'s me.\/Shhh! I\'m trying to think here.\/Loading pixel universe.",
            "flash.client.url": "{{ client.path }}",
            "user.hash": "ticket",
            "has.identity": "0",
            "flash.client.origin": "popup",
            "nux.lobbies.enabled": "false",
            "country_code": "US"
        };
    </script>
    <script type="text/javascript">
        var params = {
            "base" : "{{ client.path }}",
            "allowScriptAccess" : "always",
            "menu" : "false",
            "wmode": "opaque"
        };
        swfobject.embedSWF("{{ client.swf }}", 'flash-container', '100%', '100%', '11.1.0', '//icarus.dev/habboweb/63_1d5d8853040f30be0cc82355679bba7c/10751/web-gallery/flash/expressInstall.swf', flashvars, params, null, null);
    </script>
</head>
<body>
<div id="client-ui">
    <div id="flash-wrapper">
        <div id="flash-container">
            <div id="content" style="width: 400px; margin: 20px auto 0 auto; display: none">
                <p>FLASH NOT INSTALLED</p>
                <p><a href="http://www.adobe.com/go/getflashplayer"><img src="//icarus.dev/habboweb/63_1d5d8853040f30be0cc82355679bba7c/10751/web-gallery/v2/images/client/get_flash_player.gif" alt="Get Adobe Flash player" /></a></p>
            </div>
        </div>
    </div>
    <div id="content" class="client-content"></div>
    <iframe id="page-content" class="hidden" allowtransparency="true" frameBorder="0" src="about:blank"></iframe>
</div>
</body>
</html>