    <nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <a class="navbar-brand" href="{{ site.url }}/housekeeping">Icarus Web</a>

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
			<a href="{{ site.url }}/housekeeping/logout"><button type="button" class="btn my-2 my-sm-0">Logout</button></a>
        </form>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <nav class="col-sm-3 col-md-2 hidden-xs-down bg-faded sidebar">
          <ul class="nav nav-pills flex-column">
            <li class="nav-item">
              <a class="nav-link{{ dashboardActive }}" href="{{ site.url }}/housekeeping">Dashboard</a>
            </li>
          </ul>

          <ul class="nav nav-pills flex-column">
            <li class="nav-item">
              <a class="nav-link{{ searchUsersActive }}" href="{{ site.url }}/housekeeping/users/search">Search users</a>
            </li>
			<li class="nav-item">
              <a class="nav-link{{ createUserActive }}" href="{{ site.url }}/housekeeping/users/create">Create new user</a>
            </li>
          </ul>

          <ul class="nav nav-pills flex-column">
            <li class="nav-item">
              <a class="nav-link{{ articlesActive }}" href="{{ site.url }}/housekeeping/articles">View news articles</a>
            </li>
            <li class="nav-item">
              <a class="nav-link{{ createArticlesActive }}" href="{{ site.url }}/housekeeping/articles/create">Post new article</a>
            </li>
          </ul>
		  
		  <ul class="nav nav-pills flex-column">
            <li class="nav-item">
              <a class="nav-link{{ schedulerSettingsActive }}" href="{{ site.url }}/housekeeping/settings/scheduler">Edit scheduler settings</a>
            </li>
            <li class="nav-item">
             <a class="nav-link{{ cameraSettingsActive }}" href="{{ site.url }}/housekeeping/settings/camera">Edit camera settings</a>
            </li>
          </ul>
        </nav>