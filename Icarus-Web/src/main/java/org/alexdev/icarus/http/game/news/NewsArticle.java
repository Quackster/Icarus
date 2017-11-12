package org.alexdev.icarus.http.game.news;

public class NewsArticle {

    private int id;
    private String title;
    private String author;
    private String shortstory;
    private String fullstory;
    private String date;
    private String topstory;
    private int views;

    public NewsArticle(int id, String title, String author, String shortstory, String fullStory, String date, String topstory, int views) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.shortstory = shortstory;
        this.fullstory = fullStory;
        this.date = date;
        this.topstory = topstory;
        this.views = views;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getShortStory() {
        return shortstory;
    }

    public void setShortStory(String shortstory) {
        this.shortstory = shortstory;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopStory() {
        return topstory;
    }

    public void setTopStory(String topstory) {
        this.topstory = topstory;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getFullStory() {
        return fullstory;
    }

    public void setFullStory(String fullstory) {
        this.fullstory = fullstory;
    }
}
