<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>{$site->name}: Home</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="Description" lang="en" content="ADD SITE DESCRIPTION">
		<meta name="author" content="ADD AUTHOR INFORMATION">
		<meta name="robots" content="index, follow">

		<!-- icons -->
		<link rel="apple-touch-icon" href="assets/img/apple-touch-icon.png">
		<link rel="shortcut icon" href="favicon.ico">

		<!-- Original CSS -->
		{% include "base/style.tpl" %}

		<link rel="stylesheet" href="{{ site.url }}/public/css/me.css">
		
		<script>
			function openLink() {
				window.open('hotel', '_client', 'height=' + (parseInt(window.innerHeight) * 0.95) + ',width=' + (parseInt(window.innerWidth) *0.95) + ',resizable=yes,scrollbars=no,toolbar=no,directories=no,status=no,menubar=no');
				return false;
			}
		</script>
		
	</head>
	<body>
		<div class="header">
			<div class="container">
				<div class="header-content">
					<div class="header-logo">
					</div>
				</div>			
			</div>
		</div>
		{% include "base/links.tpl" %}
		<div class="content">
			<div class="container" id="text-main">
				{% include "base/message.tpl" %}
				<div class="main" id="habbo-plate">
				
					<!-- seperator -->
					<div id="div-header-top"><p>About Me</p></div>
					<div class="player-appearance">
						<img src="http://www.habbo.com.tr/habbo-imaging/avatarimage?figure={{ player.figure }}&size=b&direction=2&head_direction=3&gesture=sml">
					</div>
					<div class="player-info">
						<b>Player name</b>&nbsp;&nbsp;{{ player.name }}
						<br>
						<br>						
						<b>Motto</b>&nbsp;&nbsp{{ player.getMission() }}
						<br>
						<br>
						<b>Last online</b>&nbsp;&nbsp;{{ player.getReadableLastOnline() }}
						
					</div>
					
					<!-- seperator -->
					<div class="player-appearance" id="hotel-status-online">
					</div>
					<div class="player-info">
						<h3>HOTEL STATUS</h3>
						The hotel is online, enter the hotel<br>
						to meet other users, trade, build<br>
						your room and play games!
					</div>
					<!-- seperator -->
					
					{% include "base/news.tpl" %}
					
				</div>
				<div class="aside">
				
					<!-- seperator -->
					<div id="div-header-top"><p>Enter Hotel</p></div>
					<br>
					<p>Can't wait to join the hotel? Click the button below!</p>
					{% if site.popupClient %}
						<p><a onClick="openLink()" href="#"><center><button class="enter-hotel" id="text-main" type="submit">ENTER NOW</button></center></a></p>
					{% else %}
						<p><a target="_blank" href="hotel"><center><button class="enter-hotel" id="text-main" type="submit">ENTER NOW</button></center></a></p>
					{% endif %}
					<!-- seperator -->
					<div id="div-header-middle" style="margin-top: -4px"><p>Extra Links</p></div>
					<br>
					<p>Visit the official server development thread</p>
					<p><a href="http://forum.ragezone.com/f331/icarus-emulator-mysql-multi-platform-1116076/"><center><img src="http://i.imgur.com/fBvEwKg.png" height="40%" width="40%"></center></a></p>
					<!-- seperator -->
					
					
				</div>
			</div>
		</div>
		{% include "base/footer.tpl" %}
	</body>
</html>