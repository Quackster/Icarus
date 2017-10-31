<?php
$this->inc('housekeeping/base/header');		
?>

<div class="hero-unit">
	<h1>Hey, {$user->username}!</h1>
	<p>Welcome to the {$site->name} Housekeeping, where you can manage the hotel!</p>
</div>
<div class="row-fluid">
	<h3>Notes</h3>
	<p>Every hotelier needs to keep notes from time-to-time. Here's your notepad, have fun!</p>

	<form action="{$site->url}/housekeeping/save/note" method="post">
		<textarea style="height: 200px; width: 96%;" name="note">{$hk->note}</textarea><br />
		<input type="submit" value="Save" class="btn btn-primary" />
	</form>
</div><!--/row-->


<?php
$this->inc('housekeeping/base/footer');		
?>