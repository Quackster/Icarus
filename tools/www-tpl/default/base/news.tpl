<div id="div-header-middle"><p>Latest News</p></div>
<p></p>

{% for article in site.articles %}
<div class="news-img" style="background-image: url({{ article.topstory }})">

</div>
<div class="news-item">
<div><h3>{{ article.title }}</div>
	<div>
		Posted: <i class="date">{{ article.date }}</i>
		&nbsp;&nbsp;
		<p></p>
	</div>
	<p>{{ article.shortstory }}</p>
</div>
{% endfor %}