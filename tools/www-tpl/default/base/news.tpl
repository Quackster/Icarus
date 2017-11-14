<div id="div-header-middle"><p>Latest News</p></div>
<p></p>

{% for article in site.articles %}
<a href="/article?id={{ article.id }}">
<div class="news-img" style="background-image: url({{ site.url }}/c_images/Top_Story_Images/{{ article.topstory }})">

</div>
<div class="news-item">
<div><h3><a href="/article?id={{ article.id }}">{{ article.title }}</a></div>
	<div>
		Posted: <i class="date">{{ article.date }}</i>
		&nbsp;&nbsp;
		<p></p>
	</div>
	<p><a href="/article?id={{ article.id }}">{{ article.shortstory }}</a></p>
</div>
</a>
{% endfor %}