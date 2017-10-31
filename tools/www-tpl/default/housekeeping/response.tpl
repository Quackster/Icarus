<?php
$this->inc('housekeeping/base/header');	
?>
<style type="text/css">
	input[type="message"] {
		width: 300px;
	}
</style>	

<div class="page-header">
	<h1><?php echo $this->data->status; ?></h1>
</div>

<div style="float: left; width: 80%;">
    
        <div class="alert alert-<?php echo $this->data->alert_type; ?>">
        <?php echo $this->data->error; ?>
        </div>

</div>

<?php
$this->inc('housekeeping/base/footer');		
?>