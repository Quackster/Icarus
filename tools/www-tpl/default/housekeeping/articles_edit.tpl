<?php
$this->inc('housekeeping/base/header');	

$article = $this->data->article;	

?>
<style type="text/css">
	input[type="message"] {
		width: 300px;
	}
</style>	

<script type="text/javascript">
function previewTS(el)
{
	document.getElementById('ts-preview').innerHTML = '<img src="{$site->url}/c_images/Top_Story_Images/' + el + '" /><br />';
}
</script>

<div class="page-header">
	<h1>Edit Article</h1>
</div>

<form action="{$site->url}/housekeeping/articles/save" method="post">
	
	<div style="float: left; width: 80%;">
    
    <div class="alert alert-success">
    Please double check all edits before submitting them!
    </div>

    Name:<br />
	<p><input type="text" name="name" value="<?php echo $article->article_name; ?>" style="width: 50%;"></textarea></p>
    
    <div id="ts-preview">

	<p><img src="<?php echo $article->article_topstory; ?>"></p>

    </div>
    <br />
    
    Article Small Image:<br />
    <p><select onkeypress="previewTS(this.value);" onchange="previewTS(this.value);" name="topstory" id="topstory">
	<?php
	
	if ($handle = opendir('c_images/Top_Story_Images'))
	{
		while (false !== ($file = readdir($handle)))
		{
			if ($file == '.' || $file == '..')
			{
				continue;
			}	
			
			echo '<option value="' . $file . '"';
			
		    $ex = explode("/", $article->article_topstory);
			
			if (isset($ex) && $ex[3] == $file)
			{
				echo ' selected';
			}
			
			echo '>' . $file . '</option>';
		}
	}

	?>
	</select></p>
	
    
    <!--Article Large Image:<br />
    <p><input type="text" name="large_image" value="<?php echo $article->article_bigimage; ?>" style="width: 50%;"></textarea></p>
    -->
	Article Description:<br />
	<p><textarea name="description" rows="3" style="width: 50%;"><?php echo $article->article_description; ?></textarea></p>
    
    Article Story:<br />
	<p><textarea name="story" rows="6" style="width: 50%;"><?php echo $article->article_story; ?></textarea></p>
    <p><input type="hidden" name="id" value="<?php echo $article->id; ?>" style="width: 50%;"></p>

	<p><input type="submit" value="Edit News" class="btn btn-primary" /></p>
	</div>
	
</form>

<?php
$this->inc('housekeeping/base/footer');		
?>