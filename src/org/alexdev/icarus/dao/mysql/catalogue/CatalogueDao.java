package org.alexdev.icarus.dao.mysql.catalogue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.catalogue.CatalogueTab;
import org.alexdev.icarus.log.Log;

public class CatalogueDao {

    /**
     * Gets the catalog tabs.
     *
     * @param parentId the parent id
     * @return the catalog tabs
     */
    public static List<CatalogueTab> getCatalogTabs(int parentId) {

        List<CatalogueTab> tabs = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM catalog_pages WHERE parent_id = ? AND enabled = ? ORDER BY order_num ASC", sqlConnection);
            preparedStatement.setInt(1, parentId);
            preparedStatement.setString(2, "1");
            
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                
                CatalogueTab tab = new CatalogueTab(resultSet.getInt("id"), resultSet.getInt("parent_id"), resultSet.getString("caption"), 
                        resultSet.getInt("icon_image"), true, resultSet.getInt("min_rank"), resultSet.getString("page_link"));

                tabs.add(tab);
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return tabs;
    }

    /**
     * Gets the catalogue pages.
     *
     * @return the catalogue pages
     */
    public static List<CataloguePage> getCataloguePages() {

        List<CataloguePage> pages = new ArrayList<>();
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM catalog_pages", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CataloguePage page = fill(resultSet);
                pages.add(page);
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return pages;
    }

    /**
     * Gets the catalogue items.
     *
     * @return the catalogue items
     */
    public static List<CatalogueItem> getCatalogueItems() {

        List<CatalogueItem> items = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM catalog_items", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                CatalogueItem item = new CatalogueItem(resultSet.getInt("id"), resultSet.getInt("page_id"), resultSet.getString("item_id"), resultSet.getString("item_name"), 
                        resultSet.getInt("cost_credits"), resultSet.getInt("cost_other_type"), resultSet.getInt("cost_other"), resultSet.getInt("amount"), 
                        resultSet.getInt("buy_multiple") == 1, resultSet.getInt("subscription_status"), resultSet.getString("extradata"), resultSet.getString("badge"), 
                        resultSet.getInt("limited_stack"), resultSet.getInt("limited_sells"), resultSet.getInt("offer_active") == 1);

                items.add(item);
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return items;
    }

    /**
     * Fill.
     *
     * @param row the row
     * @return the catalogue page
     * @throws Exception the exception
     */
    public static CataloguePage fill(ResultSet row) throws Exception {
        
        List<String> images = new ArrayList<>();
        List<String> texts = new ArrayList<>();
        
        String rawImages = row.getString("images");
        String rawTexts = row.getString("texts");
        
        for (String image : rawImages.split("\\|")) {
            images.add(image);
        }
        
        for (String text : rawTexts.split("\\|")) {
            texts.add(text);
        }
        
        CataloguePage page = new CataloguePage(row.getInt("id"), row.getString("caption"), row.getInt("parent_id"), "DEFAULT", row.getString("page_layout"), row.getInt("min_rank"), images, texts, null);
        return page;
    }
}
