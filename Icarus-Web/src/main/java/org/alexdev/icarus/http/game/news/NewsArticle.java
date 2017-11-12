package org.alexdev.icarus.http.game.news;

public class NewsArticle {

    private int id;
    private String name;
    private String author;
    private String description;
    private String date;
    private String topstory;
    private int views;

    public NewsArticle(int id, String name, String author, String description, String date, String topstory, int views) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
