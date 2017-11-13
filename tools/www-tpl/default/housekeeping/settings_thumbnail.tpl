{% include "base/header.tpl" %}
  <body>
    {% set thumbnailSettingsActive = " active " %}
	{% include "base/navigation.tpl" %}
     <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
		<h2>Edit Room Thumbnail Settings</h2>
		{% include "base/alert.tpl" %}
		<p>Here you can edit the variables that the room thumbnails use.</p>
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label>Room thumbnails enabled:</i></label>
				<br>
				<select name="thumbnail_enabled">
					<option value="1"{% if cameraenabled == "1" %} selected{% endif %}>Yes</option>
					<option value="0"{% if cameraenabled == "0" %} selected{% endif %}>No</option>
				</select>
			</div>
			<div class="form-group">
				<label>Thumbnail filename:</label>
				<label>
				<small>
					<i>(Use {generatedId} for a random sequence of letters and numbers)</i>
					<br><i>(Use {username} for a the Thumbnail owners username)</i>
					<br><i>(Use {id} for the room id the photo was taken)</i>
				</small></label>
				
				<input type="text" class="form-control" id="text" name="thumbnail_filename"  value="{{ thumbnailfilename }}">
			</div>
			<div class="form-group">
				<label>Thumbnail path <i>(Directory path to save images, somewhere within the www directory such as c_images):</i></label>
				<input type="text" class="form-control" id="text" name="thumbnail_path"  value="{{ thumbnailpath }}">
			</div>	
			<div class="form-group">
				<label>Thumbnail relative url <i>(After {{ site.url }}/c_images/):</i></label>
				<label>
				<small>
				<i>(Use {filename} within this setting, or else it won't work)</i>
				</label>
				</small></label>
			
				<input type="text" class="form-control" id="text" name="thumbnail_url"  value="{{ thumbnailurl }}">
			</div>				
			<div class="form-group"> 
				<button type="submit" class="btn btn-info">Save Settings</button>
			</div>
		</form>
        </main>
      </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
  </body>
</html>

{% include "base/footer.tpl" %}