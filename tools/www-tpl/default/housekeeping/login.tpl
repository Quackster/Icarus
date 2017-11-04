{% include "base/header.tpl" %}
	<link href="{{ site.url }}/public/hk/css/bootstrap.login.override.css" rel="stylesheet">	
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
{% include "base/footer.tpl" %}