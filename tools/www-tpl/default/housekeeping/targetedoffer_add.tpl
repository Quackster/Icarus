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
	document.getElementById('ts-preview').innerHTML = '<img src="{$site->url}/c_images/targetedoffers/' + el + '" /><br />';
}
</script>

<div class="page-header">
	<h1>Create Targeted Offer</h1>
</div>

<form action="{$site->url}/housekeeping/targetedoffers/create?add" method="post">
	
	<div style="float: left; width: 80%;">
    
    <div class="alert alert-success">
    Please double check all edits before submitting them!
    </div>

    Title:<br />
	<p><input type="text" name="title" value="" style="width: 50%;"></p>
    <br />
    
    Large Image:<br />
    <p><select onkeypress="previewTS(this.value);" onchange="previewTS(this.value);" name="largeimage" id="largeimage">
	<?php
	
    $files = array();
    
	if ($handle = opendir('c_images/targetedoffers'))
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

	<p><img src="{$site->url}/c_images/targetedoffers/<?php echo $files[rand(0, count($files))]; ?>"></p>

    </div>
    <br />
	
	Description:<br />
	<p><textarea name="description" rows="3" style="width: 50%;"></textarea></p>
    
    Cost credits:<br />
	<p><input type="text" name="credits" value="0" style="width: 50%;"></p>
    
    Cost activity points:<br />
	<p><input type="text" name="activitypoints" value="0" style="width: 50%;"></p>

    Activity points type:<br />
	<p>     
        <select name="activitypointstype" id="activitypointstype">
            <option value="0">Duckets</option>
            <option value="101">Shells</option>
            <option value="102">Nuts</option>
            <option value="103">Stars</option>
            <option value="104">Clouds</option>
            <option value="105">Diamonds</option>
        </select>
    </p>
    
    Days until expiry:<br />
	<p><input type="text" name="expirydays" value="7" style="width: 50%;"></p>
    
    Items:<br />
    <br />
    <p>This contains a list of item definition ID's seperated by ";". Enter the ID's more times if you wish for them to buy it more than once.</p>
    <p><i>(Use command <strong>:debugfurniture</strong> and either double click furniture or browse the catalogue to find these definition ID's)</i></p>
	<p><input type="text" name="items" value="" placeholder="0000;0000;0000" style="width: 50%;"></p>
    
    Disable offer? <i>(Selecting yes will stop this offer being active)</i><br />
	<p>     
        <select name="enabled" id="enabled">
            <option value="1">No</option>
            <option value="0">Yes</option>
        </select>
    </p>
    
    <p><input type="hidden" name="id" value="<?php echo $targetedoffer->id; ?>" style="width: 50%;"></p>
	<p><input type="submit" value="Edit Offer" class="btn btn-primary" /></p>
	</div>
	
</form>

<?php
$this->inc('housekeeping/base/footer');		
?>