package org.alexdev.icarus.http.template.binders;

import org.alexdev.icarus.http.game.news.NewsArticle;
import org.alexdev.icarus.http.mysql.dao.NewsDao;

import java.util.List;

public class TemplateSiteBinder {

    private String url;
    private String name;
    private boolean popupClient;
    private List<NewsArticle> articles;

    public TemplateSiteBinder() {
        this.popupClient = true;
        this.url = "http://localhost";
        this.name = "Icarus";
        this.articles = NewsDao.getTop(6);
    }
}
