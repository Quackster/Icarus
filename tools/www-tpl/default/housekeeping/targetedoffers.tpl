<?php
$this->inc('housekeeping/base/header');		
?>

<h1>Targeted Offers</h1>
<table class="table table-striped">
	<tr>
        <th>Status</th>
		<th>Name</th>
		<th>Description</th>
		<th>Cost Credits</th>
		<th>Enabled</th>
	</tr>
<?php
	foreach(R::getAll("SELECT * FROM targeted_offers LIMIT 25") as $targetedoffer) {
	
	?>
		<tr>
            <td>
            
            <?php
            
                if ($targetedoffer["enabled"] == "0") {
                    echo '<font color="red">Disabled</font>';
                } else if (time() > $targetedoffer["expire_time"]) {
                    echo '<font color="red">Expired</font>';
                } else {
                    echo '<font color="green">Active</font>';
                }
             ?>
            
            </td>
			<td><?php echo $targetedoffer["title"]; ?></td>
			<td width="50%"><?php echo $targetedoffer["description"]; ?></td>
			<td><?php echo $targetedoffer["credits"]; ?></td>
			<td><?php echo $targetedoffer["purchase_limit"]; ?></td>
			<td><?php echo $targetedoffer["enabled"]; ?></td>
			<td>
				<a href="{$site->url}/housekeeping/targetedoffers/edit?id=<?php echo $targetedoffer["id"]; ?>" class="btn btn-primary">Edit</a>
				<a href="{$site->url}/housekeeping/targetedoffers/delete?id=<?php echo $targetedoffer["id"]; ?>" class="btn btn-danger">Delete</a>
			</td>
		</tr>
	<?php
	}
?>
</table>


<?php
$this->inc('housekeeping/base/footer');		
?>