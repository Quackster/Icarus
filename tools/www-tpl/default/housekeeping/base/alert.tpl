{% if session.showAlert %}

<div class="alert alert-{{ session.alertType }}">
  {{ session.alertMessage }}
</div>


{% endif %}