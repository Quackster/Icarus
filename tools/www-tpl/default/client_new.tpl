<!DOCTYPE HTML>
<html>
<title>{{ site.name }}: Hotel</title>
<head>
    <link rel="stylesheet" type="text/css" href="{{ site.url }}/api/hotel.731d1960.css">
    <script type="text/javascript" src="{{ site.url }}/api/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="{{ site.url }}/api/swfobject.js"></script>
    <script type="text/javascript">
        var flashvars = {
            "new.user.flow.enabled":"true",
            "new.user.flow.onboarding.choose.your.room":"Choose your room",
            "new.user.flow.figure.ok":"Figure change ok!",
            "external.texts.txt":"{{ client.externalFlashTexts }}",
            "new.user.flow.onboarding.what.is.hc":"Habbo Club is designed for you to express yourself better than ever! It also has other useful benefits: outfits, rewards, room layouts, a special Shop, extended friends lists, preferential access in queues and room, and extended room limits.",
            "new.user.flow.onboarding.button.select.room":"I want this room",
            "url.prefix":"https:\/\/www.habbo.com",
            "new.user.flow.rename.submit":"Done",
            "client.starting":"Please wait! Habbo is starting up.",
            "new.user.flow.onboarding.button.ready":"I\'m ready",
            "new.user.flow.room.name.12":"Sunshine Lounge",
            "new.user.flow.room.name.11":"Penumbra Penthouse",
            "new.user.flow.room.name.10":"Home Shiny Home",
            "has.identity":"1",
            "new.user.flow.onboarding.this.is.your.habbo":"This is your Habbo",
            "new.user.flow.onboarding.room.information":"Choose your first room to get you started! You can create more rooms for free later.",
            "new.user.flow.gender":"Gender",
            "new.user.flow.onboarding.your.colour":"Choose colour",
            "new.user.flow.loader.waiting":"is warmed up!",
            "new.user.flow.bodyparts":"Body parts",
            "client.starting.revolving":"For science, you monster\/Loading funny message\u2026please wait.\/Would you like fries with that?\/Follow the yellow duck.\/Time is just an illusion.\/Are we there yet?!\/I like your t-shirt.\/Look left. Look right. Blink twice. Ta da!\/It\'s not you, it\'s me.\/Shhh! I\'m trying to think here.\/Loading pixel universe.",
            "new.user.flow.rename.subtitle":"4-15 characters, letters and numbers accepted",
            "spaweb":"1",
            "new.user.flow.header":"For choosing Habbo!",
            "new.user.flow.room.select":"Select",
            "new.user.flow.intro3":"Just one more thing! Tell us what kind of room you want to start with. It\'s not for life, so don\'t overthink it!",
            "new.user.flow.intro2":"Looking good! Next, give your Habbo a name. (Or skip and think of a good one later)",
            "supersonic_application_key":"2abb40ad",
            "connection.info.host":"{{ client.ip }}",
            "new.user.onboarding.hc.flow.enabled":"true",
            "client.notify.cross.domain":"0",
            "new.user.flow.onboarding.choose.your.name":"Choose your name",
            "new.user.flow.colors":"Colours",
            "new.user.flow.onboarding.creative.tip":"There are tons of Habbos created each day, so get creative!",
            "new.user.flow.onboarding.cant.decide":"Can\'t decide? Don\'t worry, you can change your clothes later!",
            "unique_habbo_id":"hhus-f08ca2d99178b9506c0dba322bee9f05",
            "new.user.flow.onboarding.get.hc.button":"Get Habbo Club!",
            "new.user.flow.onboard.what.is.hc.header":"Much more inside...",
            "new.user.flow.gender.girl":"Girl",
            "new.user.flow.loader":"is starting up...",
            "new.user.flow.onboarding.info.hc":"\"What is Habbo Club?<br>Habbo Club is a special club you can join to get access to more clothing styles, exclusive room designs, more space on your friends list and lots more.",
            "new.user.flow.onboarding.button.remove.items":"Remove items",
            "new.user.flow.onboarding.your.looks":"Choose looks",
            "new.user.flow.note.header":"For choosing Habbo!",
            "new.user.flow.save":"I\'ll wear this!",
            "connection.info.port":"{{ client.port }}",
            "furnidata.load.url":"{{ client.furnidata }}",
            "new.user.flow.onboarding.hint.hc":"You\'ve selected Habbo club items but you\'ll have to purchase it to wear them!",
            "external.variables.txt":"{{ client.externalVariables }}",
            "client.allow.cross.domain":"1",
            "nux.lobbies.enabled":"true",
            "external.override.texts.txt":"{{ client.externalOverrideTexts }}",
            "supersonic_custom_css":"{{ site.url }}/api/hotel.731d1960.css",
            "external.figurepartlist.txt":"{{ client.figuredata }}",
            "flash.client.origin":"popup",
            "new.user.flow.onboard.what.is.hc.description":"What is Habbo Club?\nHabbo Club is a special club you can join to get access to more clothing styles, exclusive room designs, more space on your friends list and lots more",
            "new.user.flow.galleryUrl":"{{ site.url }}/c_images/nux/",
            "new.user.reception.minLength":"2",
            "processlog.enabled":"1",
            "new.user.flow.page":"1",
            "new.user.flow.title":"Thank You",
            "new.user.flow.roomTypes":"10,11,12",
            "avatareditor.promohabbos":"{{ site.url }}/api/public/lists/hotlooks.xml",
            "new.user.onboarding.show.hc.items":"false",
            "new.user.flow.name":"{{ newname }}",
            "new.user.reception.maxLength":"15",
            "new.user.flow.onboarding.characters.tip":"TIP: 3-15 characters, letters, numbers and underscores are accepted.",
            "productdata.load.url":"{{ client.productdata }}",
            "new.user.flow.rename.warning":"TIP: There are tons of Habbos created every day, and your name must be unique, so be creative! You can also use these special characters: _-",
            "new.user.flow.rename.title":"Name your Habbo:",
            "new.user.flow.intro":"While we\'re preparing your check-in, please choose your first looks from this selection:",
            "external.override.variables.txt":"{{ client.externalOverrideVariables }}",
            "new.user.flow.onboarding.button.skip":"Skip",
            "sso.ticket":"{{ ticket }}",
            "new.user.flow.onboarding.choose.your.style":"Choose your style",
            "new.user.flow.onboarding.hint.hc.header":"Wait a second!",
            "new.user.flow.clothes":"Clothes",
            "new.user.flow.gender.boy":"Boy",
            "account_id":"63876736",
            "new.user.flow.figure.error":"Figure change error!",
            "flash.client.url":"{{ client.path }}",
            "new.user.flow.note.title":"Thank You",
            "new.user.flow.rename.skip":"Skip for now",
            "new.user.flow.room.description.12":"Ambient retro Lava Lamp glow included",
            "new.user.flow.room.description.11":"Ain\'t no party like a Penumbra party!",
            "new.user.flow.room.description.10":"For the Habbo who really likes shiny things",
        };
    </script>
    <script type="text/javascript" src="{{ site.url }}/api/habboapi.js"></script>
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
<!-- <div id="client-ui">
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
</div>-->
<body id="client" class="flashclient">
    <div id="overlay"></div>
    <img src="/client/page_loader.gif" style="position:absolute; margin: -1500px;" />
    <div id="client-ui">
        <div id="flash-wrapper">
            <div id="flash-container">
                <div id="content" style="width: 400px; margin: 20px auto 0 auto;">
                    <div class="cbb clearfix">
						<h2 class="title">Please update your Flash Player to the latest version.</h2>
						<div class="box-content">
							<p>You can install and download Adobe Flash Player here: <a href="http://www.adobe.com/go/getflashplayer" target="_blank">Install flash player</a>. More instructions for installation can be found here: <a href="http://www.adobe.com/products/flashplayer/productinfo/instructions/">More information</a></p>
							<p><a href="http://www.adobe.com/go/getflashplayer" target="_blank"><img src="/client/get_flash_player.gif" alt="Get Adobe Flash player" /></a></p>
						</div>
						
                    </div>
                </div>
                <noscript>
                    <div style="width: 400px; margin: 20px auto 0 auto; text-align: center">
                        <p>If you are not automatically redirected, please <a href="/client/nojs">click here</a></p>
                    </div>
                </noscript>
            </div>
        </div>
        <div id="content" class="client-content"></div>
        <iframe id="game-content" class="hidden" allowtransparency="true" frameBorder="0" src="about:blank"></iframe>
        <iframe id="page-content" class="hidden" allowtransparency="true" frameBorder="0" src="about:blank"></iframe>
        <script type="text/javascript">
            RightClick.init("flash-wrapper", "flash-container");
            if (window.opener && window.opener != window && window.opener.location.href == "/") {
                window.opener.location.replace("/me");
            }
            $(document.body).addClassName("js");
            HabboClient.startPingListener();
            Pinger.start(true);
            HabboClient.resizeToFitScreenIfNeeded();
        </script>
        <script type="text/javascript">
            HabboView.run();
        </script>
</body>
</html>
