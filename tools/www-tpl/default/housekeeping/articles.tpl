{% include "base/header.tpl" %}
  <body>
    {% set articlesActive = " active " %}
	{% include "base/navigation.tpl" %}
     <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
		<h2>Posted Articles</h2>
		{% include "base/alert.tpl" %}
		<p>This includes the most recent articles posted on the site, you may edit or delete them if you wish.</p>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>Name</th>
				  <th>Author</th>
				  <th>Short Story</th>
				  <th>Date</th>
				  <th>Views</th>
				  <th></th>
                </tr>
              </thead>
              <tbody>
				{% for article in articles %}
                <tr>
				  <td>{{ article.title }}</td>
				  <td>{{ article.author }}</td>
				  <td>{{ article.shortstory }}</td>
				  <td>{{ article.date }}</td>
				  <td>{{ article.views }}</td>
				  <td>
				  	<a href="{{ site.url }}/housekeeping/articles/edit?id={{ article.id }}" class="btn btn-primary">Edit</a>
				  	<a href="{{ site.url }}/housekeeping/articles/delete?id={{ article.id }}" class="btn btn-danger">Delete</a>
				  </td>
                </tr>
			   {% endfor %}
              </tbody>
            </table>
          </div>
        </main>
      </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  </body>
</html>

{% include "base/footer.tpl" %}