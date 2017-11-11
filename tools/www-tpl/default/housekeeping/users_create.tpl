{% include "base/header.tpl" %}
  <body>
	{% include "base/navigation.tpl" %}
     <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
		<h2>Create User</h2>
		{% include "base/alert.tpl" %}
		<p>Enter the details to create a new user to log into.</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>Username:</label>
				<input type="text" class="form-control" id="text" placeholder="Enter username" name="username">
			</div>
			<div class="form-group">
				<label>Password:</label>
				<input type="password" class="form-control" placeholder="Enter password" name="password">
			</div>
			<div class="form-group">
				<label>Confirm Password:</label>
				<input type="password" class="form-control" placeholder="Enter password" name="confirmpassword">
			</div>
			<div class="form-group">
				<label for="pwd">Email:</label>
				<input type="email" class="form-control" placeholder="Enter email" name="email">
			</div>
			<div class="form-group">
				<label for="pwd">Look/figure:</label>
				<input type="text" class="form-control" name="figure" value="{{ defaultFigure }}">
			</div>
			<div class="form-group">
				<label for="pwd">Mission:</label>
				<input type="text" class="form-control" placeholder="Enter mission" name="mission" value="{{ defaultMission }}">
			</div>
			<div class="form-group"> 
					<button type="submit" class="btn btn-info">Submit</button>
			</div>
		</form>
        </main>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  </body>
</html>

{% include "base/footer.tpl" %}