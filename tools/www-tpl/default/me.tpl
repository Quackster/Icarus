<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>{{ site.name }}: Home</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="Description" lang="en" content="ADD SITE DESCRIPTION">
		<meta name="author" content="ADD AUTHOR INFORMATION">
		<meta name="robots" content="index, follow">

		<link rel="apple-touch-icon" href="assets/img/apple-touch-icon.png">
		<link rel="shortcut icon" href="favicon.ico">
		
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
					<div class="player-appearance" id="hotel-status-online">
					</div>
					<div class="player-info">
						<h3>HOTEL STATUS</h3>
						The hotel is online, enter the hotel<br>
						to meet other users, trade, build<br>
						your room and play games!
					</div>

					{% include "base/news.tpl" %}
					
				</div>
				{% include "base/sidebar_logged_in.tpl" %}
			</div>
		</div>
		{% include "base/footer.tpl" %}
	</body>
</html>