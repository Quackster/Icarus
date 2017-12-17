package org.alexdev.icarus.http.template.binders;

import org.alexdev.icarus.dao.mysql.site.SiteKey;
import org.alexdev.icarus.http.game.GameSettings;
import org.alexdev.icarus.http.game.news.NewsArticle;
import org.alexdev.icarus.http.mysql.dao.NewsDao;
import org.alexdev.icarus.http.mysql.dao.SiteDao;
import org.alexdev.icarus.http.util.config.Configuration;

import java.util.List;

public class TemplateSiteBinder {
    private String url;
    private String name;
    private boolean popupClient;
    private boolean allowPhotos;
    private String users;
    private List<NewsArticle> articles;

    public TemplateSiteBinder() {
        this.popupClient = Configuration.CLIENT_POPUP;
        this.url = Configuration.SITE_URL;
        this.name = Configuration.SITE_NAME;
        this.allowPhotos = GameSettings.CAMERA_ENABLED;
        this.users = SiteDao.get(SiteKey.USERS_ONLINE);
        this.articles = NewsDao.getTop(6);
    }
}
