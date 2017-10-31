<?php
$this->inc('housekeeping/base/header');	

if (!isset($_POST['message']))
{
    $_POST['message'] = "";
}

$message = $_POST['message'];

?>
<style type="text/css">
	input[type="message"] {
		width: 300px;
	}
</style>	

<div class="page-header">
	<h1>Hotel Alert</h1>
</div>

<div style="float: left; width: 80%;">
    
    <?php 
    
    if (!isset($message)) {
        $message = "";
    }
    
    if ($message == "")
    {
    
    ?>
        <div class="alert alert-important">
        Please supply a valid alert!
        </div>
    
    <?php } else { 
    
            MUS("ha", $message . "\r\n" . " - " . Session::auth()->username);
    
    ?>
    
        <div class="alert alert-success">
        Hotel alert sent!
        </div>
        
    
    <?php } ?>

</div>

<?php
$this->inc('housekeeping/base/footer');		
?>