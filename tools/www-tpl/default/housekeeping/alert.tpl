<?php
$this->inc('housekeeping/base/header');	

?>
<style type="text/css">
	input[type="message"] {
		width: 300px;
	}
</style>	

<div class="page-header">
	<h1>Hotel Alert</h1>
</div>

<form action="{$site->url}/housekeeping/alert/send" method="post">
	
	<div style="float: left; width: 80%;">
    
    <div class="alert alert-info">
    Please carefully type want you want to be sent to all users, once sent, there is no going back!
    </div>

	Hotel message (sending to all users):<br />
    <br />
	<p><textarea name="message" rows="6"  style="width: 98%;"></textarea></p>

	<p><input type="submit" value="Send alert" class="btn btn-primary" /></p>
	</div>
	
</form>

<?php
$this->inc('housekeeping/base/footer');		
?>