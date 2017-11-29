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
		<link rel="stylesheet" href="{{ site.url }}/public/css/community.css">
		
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
					<div id="div-header-top"><p>Latest Users</p></div>
					<br>
					<p>The most recent users who have joined the hotel</p>
					{% if site.allowPhotos %}
					<div id="div-header-middle"><p>Latest Photos</p></div>
					<br>
					<p>The most recent photos taken on the hotel</p>
					<table style="width:100%">
					{% for image in images %}
						<tr>
						{% for img in image %}
						<td>
							<a href="/habbo-stories/{{ img }}">
								<img class="camera-photo" src="/habbo-stories/{{ img }}">
							</a>
						</td>
						{% endfor %}
						</tr>
					{% endfor %}
					</table>
					{% endif %}
				</div>
			
			{% if session.loggedIn %}
				{% include "base/sidebar_logged_in.tpl" %}
			{% else %}
				{% include "base/sidebar_login.tpl" %}
			{% endif %}
			</div>
		</div>
		{% include "base/footer.tpl" %}
	</body>
</html>