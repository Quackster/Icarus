{% include "base/header.tpl" %}
  <body>
	{% include "base/navigation.tpl" %}
     <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
          <h1>Dashboard</h1>

          <section class="row text-center placeholders">
            <div class="col-6 col-sm-3 placeholder">
              <img src="data:image/png;base64,{{ pieChartImage1 }}" width="300" height="200" class="img-fluid">
              <h4>{{ pieChartLabel1 }}</h4>
              <div class="text-muted">{{ pieChartText1 }}</div>
            </div>
            <div class="col-6 col-sm-3 placeholder">
              <img src="data:image/png;base64,{{ pieChartImage2 }}" width="300" height="200" class="img-fluid">
              <h4>{{ pieChartLabel2 }}</h4>
              <div class="text-muted">{{ pieChartText2 }}</div>
            </div>
            <div class="col-6 col-sm-3 placeholder">
              <img src="data:image/png;base64,{{ pieChartImage3 }}" width="300" height="200" class="img-fluid">
              <h4>{{ pieChartLabel3 }}</h4>
              <div class="text-muted">{{ pieChartText3 }}</div>
            </div>
            <div class="col-6 col-sm-3 placeholder">
              <img src="data:image/png;base64,{{ pieChartImage3 }}" width="300" height="200" class="img-fluid">
              <h4>{{ pieChartLabel3 }}</h4>
              <div class="text-muted">{{ pieChartText3 }}</div>
            </div>
          </section>
		  
          <h2>
			Newest Players
		  </h2>
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
                  <td>{{ player.mission }}</td>
                  <td>{{ player.credits }}</td>
                  <td>{{ player.duckets }}</td>
				  <td>{{ player.getReadableLastOnline() }}
				  <td>{{ player.getReadableJoinDate() }}
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