<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>{$site->name} Housekeeping &raquo;</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="{$site->url}/public/hk/assets/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
      body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .sidebar-nav {
        padding: 9px 0;
      }

      @media (max-width: 980px) {
        /* Enable use of floated navbar text */
        .navbar-text.pull-right {
          float: none;
          padding-left: 5px;
          padding-right: 5px;
        }
      }
    </style>
    <link href="{$site->url}/public/hk/assets/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="{$site->url}/public/hk/assets/js/html5shiv.js"></script>
    <![endif]-->

  </head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="#">{$site->name}</a>
          <div class="nav-collapse collapse">
            <p class="navbar-text pull-right">
              Logged in as <a href="#" class="navbar-link">{$user->username}</a>
            </p>
            <ul class="nav">
              <li><a href="{$site->url}">Go back to the main site</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container-fluid">
      <div class="row-fluid">
        <div class="span3">
          <div class="well sidebar-nav">
            <ul class="nav nav-list">
              <li class="nav-header">User Management</li>
              <li><a href="{$site->url}/housekeeping/users">View Users</a></li>
              <li><a href="{$site->url}/housekeeping/users/ban">Ban User</a></li>
              <!-- <li><a href="{$site->url}/housekeeping/users/give_badge">Give Badge</a></li> -->
              <li class="nav-header">Site Management</li>
              <li><a href="{$site->url}/housekeeping/articles">View News Articles</a></li>
              <li><a href="{$site->url}/housekeeping/articles/create">Create Article</a></li>
              <!-- <li><a href="{$site->url}/housekeeping/campaigns">View Campaigns</a></li>
              <li><a href="{$site->url}/housekeeping/campaigns/create">Create Campaign</a></li> -->
              <li class="nav-header">Hotel Management</li>
              <li><a href="{$site->url}/housekeeping/alert">Hotel Alert</a></li>
              <li class="nav-header">Offer Management</li>
              <li><a href="{$site->url}/housekeeping/targetedoffers">View Targeted Offers</a></li>
              <li><a href="{$site->url}/housekeeping/targetedoffers/create">Create Targeted Offer</a></li>
              <!-- <li><a href="{$site->url}/housekeeping/catalog">Catalog</a></li>
              <li><a href="{$site->url}/housekeeping/rooms">Rooms</a></li>
              <li><a href="{$site->url}/housekeeping/rooms/official">Navigation</a></li> -->
            </ul>
          </div><!--/.well -->
        </div><!--/span-->
        <div class="span9">