<?php
$this->inc('housekeeping/base/header');		
?>

<h1>News Articles</h1>
<table class="table table-striped">
	<tr>
		<th>Name</th>
		<th>Author</th>
		<th>Description</th>
		<th>Date</th>
		<th>Views</th>
	</tr>
<?php
	foreach(R::getAll("SELECT * FROM site_articles LIMIT 25") as $article) {
	
	?>
		<tr>
			<td><?php echo $article["article_name"]; ?></td>
			<td><?php echo $article["article_author"]; ?></td>
			<td><?php echo $article["article_description"]; ?></td>
			<td><?php echo $article["article_date"]; ?></td>
			<td><?php echo $article["views"]; ?></td>
			<td>
				<a href="{$site->url}/housekeeping/articles/edit?article=<?php echo $article["id"]; ?>" class="btn btn-primary">Edit</a>
				<a href="{$site->url}/housekeeping/articles/delete?article=<?php echo $article["id"]; ?>" class="btn btn-danger">Delete</a>
			</td>
		</tr>
	<?php
	}
?>
</table>


<?php
$this->inc('housekeeping/base/footer');		
?>