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

		<!-- icons -->
		<link rel="apple-touch-icon" href="assets/img/apple-touch-icon.png">
		<link rel="shortcut icon" href="favicon.ico">

		<!-- Original CSS -->
		{% include "base/style.tpl" %}
		
		<!-- Override CSS here -->
		<link rel="stylesheet" href="{{ site.url }}/public/css/index.css">
		
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
		<div class="content" id="text-main">
			<div class="container">
				{% include "base/message.tpl" %}
				<div class="main" id="register-main">
					<div id="div-header-top"><p>Homepage</p></div>
					<p></p>
					<div id="register-text">
						<h3>MAKE FRIENDS & CHAT WITH MILLIONS IN A VIRTUAL WORLD</h3>
						<p>There are currently {$users-online} players online!</p>
						<a href="/register"><center><button class="register-button" id="text-main" type="submit">JOIN NOW</button></center></a>
					</div>
					<br>
					<p></p>
					<p></p>
					{% include "base/news.tpl" %}
					
					
				</div>
				<div class="aside">
					<div id="div-header-top"><p>Login</p></div>
					<br>
					<p>Already have an account? Log in here!</p>
					<form action="account/login" method="POST">
						<p><input class="login-border" type="text" placeholder="Email" name="email" required></p>
						<p><input class="login-border" type="password" placeholder="Password" name="password" required></p>
						<center><button class="login-button" id="text-main" type="submit">LOGIN</button></center>
					</form>	
				</div>
			</div>
		</div>
		{% include "base/footer.tpl" %}
	</body>
</html>