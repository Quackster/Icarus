<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">
    <title>{{ site.name }}: Housekeeping</title>
    <link href="{{ site.url }}/public/hk/css/bootstrap.min.css" rel="stylesheet">
	<link href="{{ site.url }}/public/hk/css/bootstrap.login.override.css" rel="stylesheet">	
	<link href="{{ site.url }}/public/hk/css/sticky-footer.css" rel="stylesheet">
  </head>
  <body>
	
	<div class="container">
		<div class="mt-1">
			<h1>Housekeeping</h1>
		</div>
		{% include "base/alert.tpl" %}
		<div class="login-image">
			<p style="margin-left:0">Please sign in to access the housekeeping</p>
			<form class="form-signin" action="/housekeeping/login" method="post">
				<label for="inputEmail" class="sr-only">Email address</label>
				<input type="email" name="hkemail" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
				<label for="inputPassword" class="sr-only">Password</label>
				<input type="password" name="hkpassword" id="inputPassword" class="form-control" placeholder="Password" required>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
				<div class="checkbox">
					<label>
						<!--<input type="checkbox" value="remember-me"> Remember me-->
						<p style="font-size; 5px">Oops, <a href="{{ site.url }}">take me back please!</a></p>
					</label>
				</div>
			</form>
		</div>
	</div>
    <footer class="footer">
      <div class="container">
        <span class="text-muted">&copy; Copyright 2017 - Alex Miller</span>
      </div>
    </footer>
    <script src="{{ site.url }}/public/hk/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
