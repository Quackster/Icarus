<?php
$this->inc('housekeeping/base/header');		

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
	<h1>New Article</h1>
</div>

<form action="{$site->url}/housekeeping/articles/create?add" method="post">
	
	<div style="float: left; width: 80%;">
    
    <div class="alert alert-success">
    Please double check all entries before posting a new article!
    </div>

    Name:<br />
	<p><input type="text" name="name" style="width: 50%;"></textarea></p>
    
    Article Small Image:<br />
    <p><select onkeypress="previewTS(this.value);" onchange="previewTS(this.value);" name="topstory" id="topstory">
	<?php
	
    $files = array();
    
	if ($handle = opendir('c_images/Top_Story_Images'))
	{
		while (false !== ($file = readdir($handle)))
		{		
			if ($file == '.' || $file == '..')
			{
				continue;
			}	
			
			echo '<option value="' . $file . '"';
			
            $files[] = $file;
            
			echo '>' . $file . '</option>';
		}
	}

	?>
	</select></p>
	
    <div id="ts-preview">

	<p><img src="{$site->url}/c_images/Top_Story_Images/<?php echo $files[rand(0, count($files))]; ?>"></p>

    </div>
    <br />
    <!--Article Large Image:<br />
    <p><input type="text" name="large_image" style="width: 50%;"></textarea></p>
    -->
	Article Description:<br />
	<p><textarea name="description" placeholder="Short summary of article introduction. HTML allowed!" rows="3" style="width: 50%;"></textarea></p>
    
    Article Story:<br />
	<p><textarea name="story" placeholder="The full story. HTML allowed!" rows="6" style="width: 50%;"></textarea></p>
    
    <p><input type="submit" value="Add News" class="btn btn-primary" /></p>
	</div>
	
</form>

<?php
$this->inc('housekeeping/base/footer');		
?>