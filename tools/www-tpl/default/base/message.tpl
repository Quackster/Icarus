				{% if session.showAlert %}
				
				<div class="{{ session.alertType }}">
					<span class="closebtn" onclick="this.parentElement.style.display='none';">&times;</span> 
					{{ session.alertMessage }}
				</div>
				
				{% endif %}