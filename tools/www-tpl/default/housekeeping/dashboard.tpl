{% include "base/header.tpl" %}
  <body>
	{% include "base/navigation.tpl" %}
     <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
          <h2>Hotel Statistics</h2>
		  <p>Welcome to the housekeeping for {{ site.name }} Hotel, here you can manage a lot of things at once, such as users, news, site content and view the statistics of the hotel.</p>
		   <div class="table-responsive col-md-4">
            <table class="table table-striped">
			<thead>
				<tr>
					<td></td>
					<td></td>
				</tr>
			</thead>
			<tbody class="col-md-4">
				<tr>
					<td><strong>Icarus Web Version</strong></td>
					<td>1.0</td>
				</tr>
				<tr>
					<td>Users</td>
					<td>1337</td>
				</tr>
				<tr>
					<td>Furniture</td>
					<td>15667</td>
				</tr>
				<tr>
					<td>Inventory Items</td>
					<td>10</td>
				</tr>
				<tr>
					<td>Groups</td>
					<td>2</td>
				</tr>
				<tr>
					<td>Pets</td>
					<td>5</td>
				</tr>
			</tbody>
			</table>
		  </div>
          <h2>Newest Players</h2>
		  <p>The recently joined player list is seen below</p>
		  <div style="margin:10px">
			{% if nextPlayers|length > 0 %}
				{% set ourNextPage = page + 1 %}
				<a href="{{ site.url}}/housekeeping?page={{ ourNextPage }}"><button type="button" class="btn btn-info">Next Page</button></a>
			{% endif %}
			{% if previousPlayers|length > 0 %}
				{% set ourNextPage = page - 1 %}
				<a href="{{ site.url}}/housekeeping?page={{ ourNextPage }}"><button type="button" class="btn btn-warning">Go back</button></a>
			{% endif %}
			</div>
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