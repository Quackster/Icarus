<?php
$this->inc('housekeeping/base/header');		
?>

<h1>Users</h1>
<table class="table table-striped">
	<tr>
		<th>Figure</th>
		<th>#</th>
		<th>Username</th>
		<th>Rank</th>
		<th>Credits</th>
		<th>Motto</th>
		<th>Actions</th>
	</tr>
<?php
	foreach(R::findAll("users") as $user) {
	?>
		<tr>
			<td><img src="http://www.habbo.com.tr/habbo-imaging/avatarimage?figure=<?php echo $user->figure; ?>&size=s" style="margin-top: -10px;"/></td>
			<td><?php echo $user->id; ?></td>
			<td><?php echo $user->username; ?></td>
			<td><?php echo $user->rank; ?></td>
			<td><?php echo $user->credits; ?></td>
			<td><?php echo $user->mission; ?></td>
			<td>
				<a href="{$site->url}/housekeeping/users/edit?user=<?php echo $user->id; ?>" class="btn btn-primary">Edit</a>
				<a href="{$site->url}/housekeeping/users/delete?user=<?php echo $user->id; ?>" class="btn btn-danger">Delete</a>
				<a href="{$site->url}/housekeeping/users/delete?user=<?php echo $user->id; ?>" class="btn btn-warning">Ban</a>
			</td>
		</tr>
	<?php
	}
?>
</table>


<?php
$this->inc('housekeeping/base/footer');		
?>