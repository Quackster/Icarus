<?php
$this->inc('housekeeping/base/header');	

$user = $this->data->user;	
?>
<style type="text/css">
	input[type="text"], input[type="email"] {
		width: 300px;
	}
</style>	

<div class="page-header">
	<h1>Edit User</h1>
</div>

<form action="{$site->url}/housekeeping/users/save?id=<?php echo $user->id; ?>" method="post">
	
	<div style="float: left; width: 40%;">
	Username:<br />
	<input type="text" name="username" value="<?php echo $user->username; ?>" /><br />

	Rank:<br />
	<input type="text" name="rank" value="<?php echo $user->rank; ?>" /><br />

	Motto:<br />
	<textarea name="motto" style="width: 300px; height: 50px;"><?php echo $user->mission; ?></textarea><br />

	
	E-mail:<br />
	<input type="email" name="email" value="<?php echo $user->email; ?>" /><br />
	
	Figure:<br />
	<input type="text" name="look" value="<?php echo $user->figure; ?>" /><br />
	
	Credits:<br />
	<input type="text" name="credits" value="<?php echo $user->credits; ?>" /><br />
	
	Pixels:<br />
	<input type="text" name="credits" value="<?php echo $user->pixels; ?>" /><br />
	
	VIP Points:<br />
	<input type="text" name="vip_points" value="<?php echo $user->vip_points; ?>" /><br />
	
	<input type="submit" value="Save User" class="btn btn-primary" />
	</div>
	
	<div style="float: left; width: 40%;">
		<h2><?php echo $user->username; ?></h2>
		<img src="http://www.habbo.com.tr/habbo-imaging/avatarimage?figure=<?php echo $user->figure; ?>&size=l" align="left" style="margin-top: -20px;"/>
		Rooms: <?php echo count(R::findAll("rooms", " WHERE ownerid = '" . $user->id . "'")); ?><br />
		<br /><br /><br />
		<h2>Badges</h2>
		<table class="table table-striped">

		</table>
	</div>
</form>

<?php
$this->inc('housekeeping/base/footer');		
?>