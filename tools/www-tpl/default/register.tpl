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
		<script src='https://www.google.com/recaptcha/api.js'></script>
		<link rel="stylesheet" href="{{ site.url }}/public/css/index.css">
		<link rel="stylesheet" href="{{ site.url }}/public/css/register.css">
		
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
		<div class="content" id="background-register">
			<div class="container">
			
				{% include "base/message.tpl" %}
				
				<div class="main" id="register-div">
				
					<form action="account/register" method="POST">
					
						<h2 id="text-main">Welcome to {{ site.name }} Hotel!</h2>
						<!-- 
						<p class="register-text">
							You'll need to use this username to log in to Icarus in the future. Please choose carefully.
							<br>
							<br>
							<input class="register-border" type="text" placeholder="Username" name="regusername" required>
						</p>
						-->
						<p class="register-text">
							You'll need to use this email address to login and in the case that you have forgotten your password.<br>Please pick a valid email and remember it.
							<br>
							<br>
							<input class="register-border" type="text" placeholder="Email" name="regemail" required>
						</p>
						<div class="password-confirm">
							<p class="register-text">
								Your password, keep this secret because it's used to access your account.
								<br>
								<br>
								<input class="register-border" type="password" placeholder="Password" name="regpassword" required>
							</p>
							<p class="register-text">
								Confirm your password, we need to verify that you can access your account again in future.
								<br>
								<br>
								<input class="register-border" type="password" placeholder="Confirm Password" name="regconfirmpassword" required>
							</p>
						</div>
						<br>
						<p class="register-text">
							We need to verify you're not a robot!
							<br>
							<div class="g-recaptcha" data-sitekey="{{ register.publicRecaptchaKey }}"></div>
						</p>
						<button class="login-button" style="width:300px;" type="submit">CREATE MY ACCOUNT!</button>
						
					</form>	
					
					
				</div>
			</div>
		</div>
		{% include "base/footer.tpl" %}
	</body>
</html>