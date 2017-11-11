{% include "base/header.tpl" %}
  <body>
	{% include "base/navigation.tpl" %}
     <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
		<h2>Edit User</h2>
		{% include "base/alert.tpl" %}
		<p>Here you can edit user details.</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>Username:</label>
				<input type="text" class="form-control" id="text" name="username"  value="{{ playerUsername }}">
			</div>
			<div class="form-group">
				<label for="pwd">Email:</label>
				<input type="email" class="form-control" name="email" value="{{ playerEmail }}">
			</div>
			<div class="form-group">
				<label for="pwd">Look/figure:</label>
				<input type="text" class="form-control" name="figure" value="{{ playerFigure }}">
			</div>
			<div class="form-group">
				<label for="pwd">Mission:</label>
				<input type="text" class="form-control" name="mission" value="{{ playerMission }}">
			</div>
			<div class="form-group">
				<label for="pwd">Credits:</label>
				<input type="text" class="form-control" name="credits" value="{{ playerCredits }}">
			</div>
			<div class="form-group">
				<label for="pwd">Duckets:</label>
				<input type="text" class="form-control" name="duckets" value="{{ playerDuckets }}">
			</div>
			<div class="form-group"> 
				<input type="hidden" id="text" name="id" value="{{ playerId }}">
				<button type="submit" class="btn btn-info">Save Details</button>
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