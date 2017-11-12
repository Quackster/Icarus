package org.alexdev.icarus.http.mysql.dao;

import org.alexdev.icarus.http.game.news.NewsArticle;
import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.Storage;

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

        List<NewsArticle> articles = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT * FROM `site_articles` ORDER by `id` DESC LIMIT 25", sqlConnection);
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

    private static NewsArticle fill(ResultSet resultSet) throws SQLException {
        return new NewsArticle(
                resultSet.getInt("id"), resultSet.getString("article_name"), resultSet.getString("article_author"),
                resultSet.getString("article_description"), resultSet.getString("article_date"), resultSet.getString("article_topstory"),
                resultSet.getInt("views"));
    }
}
