<div class="aside">

	<!-- seperator -->
	<div id="div-header-top"><p>Enter Hotel</p></div>
	<br>
	<p>Can't wait to join the hotel? Click the button below!</p>
	{% if site.popupClient %}
		<p><a onClick="openLink()" href="#"><center><button class="enter-hotel" id="text-main" type="submit">ENTER NOW</button></center></a></p>
	{% else %}
		<p><a target="_blank" href="hotel"><center><button class="enter-hotel" id="text-main" type="submit">ENTER NOW</button></center></a></p>
	{% endif %}
	<!-- seperator -->
	<div id="div-header-middle" style="margin-top: -4px"><p>Extra Links</p></div>
	<br>
	<p>Visit the official server development thread</p>
	<p><a href="http://forum.ragezone.com/f331/icarus-emulator-java-netty-mysql-1087933/"><center><img src="https://i.imgur.com/fBvEwKg.png" height="40%" width="40%"></center></a></p>
	<!-- seperator -->
</div>