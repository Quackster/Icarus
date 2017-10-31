<div id="div-header-middle"><p>Latest News</p></div>
					<p></p>
					
					
					<?php
					$all = NewsDao::getTop(5);
					foreach($all as $n) {
					?>
				
					
					<div class="news-img" style="background-image: url(<?php echo $n->article_topstory; ?>)">

					</div>
					<div class="news-item">
					<div><h3><?php echo $n->article_name; ?></div>
						<div>
							Posted: <i class="date"><?php echo $n->article_date; ?></i>
							&nbsp;&nbsp;
							<p></p>
						</div>
						<p><?php echo $n->article_description; ?></p>
					</div>
					
					<?php } ?>
					