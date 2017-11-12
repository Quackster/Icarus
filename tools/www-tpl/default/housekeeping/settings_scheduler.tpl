{% include "base/header.tpl" %}
  <body>
    {% set schedulerSettingsActive = " active " %}
	{% include "base/navigation.tpl" %}
     <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
		<h2>Edit Server Scheduler Settings</h2>
		{% include "base/alert.tpl" %}
		<p>Here you can edit the variables that give the user duckets or credits every hour or so.</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>Credits every (x) minutes:</label>
				<input type="text" class="form-control" id="text" name="credits_interval_minutes"  value="{{ creditsintervalminutes }}">
			</div>
			<div class="form-group">
				<label>Credits given every (x) minutes:</label>
				<input type="text" class="form-control" id="text" name="credits_interval_amount"  value="{{ creditsintervalamount }}">
			</div>
			<div class="form-group">
				<label>Duckets every (x) minutes:</label>
				<input type="text" class="form-control" id="text" name="duckets_interval_minutes"  value="{{ ducketsintervalminutes }}">
			</div>
			<div class="form-group">
				<label>Duckets given every (x) minutes:</label>
				<input type="text" class="form-control" id="text" name="duckets_interval_amount"  value="{{ ducketsintervalamount }}">
			</div>			
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Save Settings</button>
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