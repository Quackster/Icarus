package org.alexdev.icarus.http.mysql.dao;

import org.alexdev.icarus.http.game.news.NewsArticle;
import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.Storage;
import org.alexdev.icarus.http.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDao {

    public static List<NewsArticle> getTop(int limit) {

        List<NewsArticle> articles = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT * FROM `site_articles` ORDER by `id` DESC LIMIT ?", sqlConnection);
            preparedStatement.setInt(1, limit);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                articles.add(fill(resultSet));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return articles;
    }

    public static List<NewsArticle> getArticles() {
        return getTop(25);
    }


    public static void create(String title, String shortstory, String fullstory, String topstory, String author) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("INSERT INTO site_articles (article_title, article_author, article_shortstory, article_fullstory, article_date, article_topstory, views)", sqlConnection);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, shortstory);
            preparedStatement.setString(4, fullstory);
            preparedStatement.setString(5, Util.getDateAsString());
            preparedStatement.setInt(6, 0);
            resultSet = preparedStatement.executeQuery();


        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    private static NewsArticle fill(ResultSet resultSet) throws SQLException {
        return new NewsArticle(
                resultSet.getInt("id"), resultSet.getString("article_title"), resultSet.getString("article_author"),
                resultSet.getString("article_shortstory"), resultSet.getString("article_date"), resultSet.getString("article_topstory"),
                resultSet.getInt("views"));
    }
}
