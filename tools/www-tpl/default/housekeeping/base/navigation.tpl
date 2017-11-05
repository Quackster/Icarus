    <nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <a class="navbar-brand" href="#">Icarus Web</a>

      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Settings</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Profile</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Help</a>
          </li>
        </ul>
        <form class="form-inline mt-2 mt-md-0">
          <a href="{{ site.url }}/"><button type="button" class="btn my-2 my-sm-0">Back to main site</button></a>
        </form>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <nav class="col-sm-3 col-md-2 hidden-xs-down bg-faded sidebar">
          <ul class="nav nav-pills flex-column">
            <li class="nav-item">
              <a class="nav-link active" href="/housekeeping/dashboard">Overview <span class="sr-only">(current)</span></a>
            </li>
          </ul>

          <ul class="nav nav-pills flex-column">
            <li class="nav-item">
              <a class="nav-link" href="#">View users</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Search users</a>
            </li>
			<li class="nav-item">
              <a class="nav-link" href="#">Create new user</a>
            </li>
          </ul>

          <ul class="nav nav-pills flex-column">
            <li class="nav-item">
              <a class="nav-link" href="#">View news articles</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Post new article</a>
            </li>
          </ul>
		  
		  <ul class="nav nav-pills flex-column">
            <li class="nav-item">
              <a class="nav-link" href="#">Modify server scheduler</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Modify room limits</a>
            </li>
          </ul>
        </nav>