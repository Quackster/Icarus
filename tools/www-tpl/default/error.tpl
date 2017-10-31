<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>{$site->name}: Error</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="Description" lang="en" content="ADD SITE DESCRIPTION">
		<meta name="author" content="ADD AUTHOR INFORMATION">
		<meta name="robots" content="index, follow">

		<!-- icons -->
		<link rel="apple-touch-icon" href="assets/img/apple-touch-icon.png">
		<link rel="shortcut icon" href="favicon.ico">

		<!-- Original CSS -->
		<?php $this->inc("base/style"); ?>
		
		
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
		<?php $this->inc("base/links"); ?>
		<div class="content" id="text-main">
			<div class="container">
				<div class="main">
					<div id="div-header-top"><p>Homepage</p></div>
					<br>
					<h2>{$error->title}</h2>
					<p>{$error->message}</p>
					
				</div>
			</div>
		</div>
		<?php $this->inc("base/footer"); ?>
	</body>
</html>