{% include "base/header.tpl" %}
  <body>
	{% include "base/navigation.tpl" %}
     <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
		<h2>Search users</h2>
		{% include "base/alert.tpl" %}
		<p>Here you can search users by the field of your choice, and the requested input by you</p>
		<form class="form-horizontal col-md-2" method="post">
			<div class="form-group">
				<label class="control-label col-sm-2" for="email">Username:</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="text" placeholder="Enter username" name="username">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="email">Password:</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="pwd">Email:</label>
				<div class="col-sm-10"> 
					<input type="email" class="form-control" id="pwd" placeholder="Enter email" name="email">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="pwd">Look/figure:</label>
				<div class="col-sm-10"> 
					<input type="text" class="form-control" id="pwd" name="figure">
				</div>
			</div>
			<div class="form-group"> 
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-info">Submit</button>
				</div>
			</div>
		</form>
        </main>
      </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  </body>
</html>

{% include "base/footer.tpl" %}