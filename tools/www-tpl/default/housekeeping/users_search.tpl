{% include "base/header.tpl" %}
  <body>
	{% include "base/navigation.tpl" %}
     <main>
		<h2>Search users</h2>
		{% include "base/alert.tpl" %}
		<p>Here you can search users by the field of your choice, and the requested input by you</p>
		<form class="table-responsive col-md-2" method="post">
			<div class="form-group">
				<label for="field">Field</label>
				<select name="searchField" class="form-control" id="field">
					<option value="username">Username</option>
					<option value="id">ID</option>
					<option value="credits">Credits</option>
					<option value="duckets">Duckets</option>
					<option value="mission">Mission</option>
				</select>
			</div>
			<div class="form-group">
				<label for="field">Search type</label>
				<select name="searchType" class="form-control" id="field">
					<option value="contains">Contains</option>
					<option value="starts_with">Starts with</option>
					<option value="ends_with">Ends with</option>
					<option value="equals">Equals</option>
				</select>
			</div>
			<div class="form-group">
				<label for="searchFor">Search data</label>
				<input type="text" name="searchQuery" class="form-control" id="searchFor" placeholder="Looking for...">
			</div>
			<button type="submit" class="btn btn-primary">Perform Search</button>
		</form>
		<br>
		{% if players|length > 0 %}
		<h2>Search Results</h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
				  <th>Look</th>
                  <th>Mission</th>
                  <th>Credits</th>
                  <th>Duckets</th>
				  <th>Last online</th>
				  <th>Date joined</th>
                </tr>
              </thead>
              <tbody>
			    {% set num = 1 %}
				{% for player in players %}
                <tr>
                  <td>{{ player.id }}</td>
                  <td>{{ player.name }}</td>
				  <td><img src="https://www.habbo.com.tr/habbo-imaging/avatarimage?figure={{ player.figure }}&size=s"></td>
                  <td>{{ player.mission }}</td>
                  <td>{{ player.credits }}</td>
                  <td>{{ player.duckets }}</td>
				  <td>{{ player.getReadableLastOnline() }}</td>
				  <td>{{ player.getReadableJoinDate() }}</td>
                </tr>
			   {% set num = num + 1 %}
			   {% endfor %}
              </tbody>
            </table>
          </div>
		{% endif %}
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