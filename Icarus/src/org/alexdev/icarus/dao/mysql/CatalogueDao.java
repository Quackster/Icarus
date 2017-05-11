package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.alexdev.icarus.factories.CatalogueFactory;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.catalogue.CatalogueTab;
import org.alexdev.icarus.log.Log;

import com.google.common.collect.Lists;

public class CatalogueDao {
    
	public static List<CatalogueTab> getCatalogTabs(int parentId) {

		List<CatalogueTab> tabs = Lists.newArrayList();

		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			preparedStatement = Dao.getStorage().prepare("SELECT * FROM catalog_pages WHERE parent_id = " + parentId, sqlConnection);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				CatalogueTab tab = CatalogueFactory.getTab(resultSet.getInt("id"), resultSet.getInt("parent_id"), resultSet.getString("caption"), 
						resultSet.getInt("icon_color"), resultSet.getInt("icon_image"), true, resultSet.getInt("min_rank"));

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

	public static List<CataloguePage> getCataloguePages() {

		List<CataloguePage> pages = Lists.newArrayList();

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

	public static List<CatalogueItem> getCatalogueItems() {

		List<CatalogueItem> items = Lists.newArrayList();

		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			preparedStatement = Dao.getStorage().prepare("SELECT id, page_id, item_ids, catalog_name, cost_credits, cost_snow, amount, vip, achievement, "
																+ "song_id, limited_sells, limited_stack, offer_active, extradata, badge_id, flat_id " 
																+ "FROM catalog_items", sqlConnection);
			resultSet = preparedStatement.executeQuery();
	
			while (resultSet.next()) {

				CatalogueItem item = CatalogueFactory.getItem(resultSet.getInt("id"), resultSet.getInt("page_id"), resultSet.getInt("item_ids"), resultSet.getString("catalog_name"), 
						resultSet.getInt("cost_credits"), resultSet.getInt("cost_snow"), resultSet.getInt("amount"), 
						resultSet.getInt("vip"), resultSet.getInt("song_id"), resultSet.getString("extradata"),
						resultSet.getString("badge_id"), resultSet.getInt("limited_stack"), resultSet.getInt("limited_sells"), resultSet.getInt("offer_active") == 1);

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
	
	public static CataloguePage fill(ResultSet row) throws Exception {

	    /*     public CataloguePage(int id, String caption, int parentId, String type, String layout, int minimum_rank, List<String> images, List<String> texts, List<CatalogueItem> items) {
        super();
        this.id = id;
        this.caption = caption;
        this.parentId = parentId;
        this.type = type;
        this.layout = layout;
        this.minimum_rank = minimum_rank;
        this.images = images;
        this.texts = texts;
    }
    }*/
	    
		CataloguePage page = new CataloguePage(row.getInt("id"), row.getString("caption"), row.getInt("parent_id"), row.getString("type"), row.getString("page_layout"), row.getInt("min_rank"), Lists.newArrayList(), Lists.newArrayList(), null);

		return page;

	}

}
