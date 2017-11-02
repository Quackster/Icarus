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
				<div class="main">
					<div id="div-header-top"><p>Homepage</p></div>
					<br>
					<h2>{{ errorTitle }}</h2>
					<p>{{ errorMessage }}</p>
					
				</div>
			</div>
		</div>
		{% include "base/footer.tpl" %}
	</body>
</html>