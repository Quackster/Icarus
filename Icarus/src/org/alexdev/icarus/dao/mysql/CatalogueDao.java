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
			preparedStatement = Dao.getStorage().prepare("SELECT * FROM catalogue_pages WHERE parent_id = " + parentId, sqlConnection);
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
			preparedStatement = Dao.getStorage().prepare("SELECT id, parent_id, caption, icon_color, icon_image, visible, enabled, min_rank, club_only, "
																+ "order_num, page_layout, page_headline, page_teaser, page_special, page_text1, page_text2, " 
																+ "min_sub, page_text_details, page_text_teaser, vip_only, page_link_description, page_link_pagename "
																+ "FROM catalogue_pages", sqlConnection);
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
			preparedStatement = Dao.getStorage().prepare("SELECT id, page_id, item_ids, catalog_name, cost_credits, cost_duckets, amount, vip, achievement, "
																+ "song_id, limited_sells, limited_stack, offer_active, extradata, badge, flat_id " 
																+ "FROM catalogue_items", sqlConnection);
			resultSet = preparedStatement.executeQuery();
	
			while (resultSet.next()) {

				CatalogueItem item = CatalogueFactory.getItem(resultSet.getInt("id"), resultSet.getInt("page_id"), resultSet.getInt("item_ids"), resultSet.getString("catalog_name"), 
						resultSet.getInt("cost_credits"), resultSet.getInt("cost_duckets"), resultSet.getInt("amount"), 
						resultSet.getInt("vip"), resultSet.getInt("song_id"), resultSet.getString("extradata"),
						resultSet.getString("badge"), resultSet.getInt("limited_stack"), resultSet.getInt("limited_sells"), resultSet.getInt("offer_active") == 1);

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

		CataloguePage page = CatalogueFactory.getPage(row.getInt("id"), row.getString("page_layout"), row.getString("page_headline"), row.getString("page_teaser"),
				row.getString("page_special"), row.getString("page_text1"), row.getString("page_text2"), row.getString("page_text_details"), 
				row.getString("page_text_teaser"), row.getBoolean("club_only"), row.getInt("min_rank"));


		return page;

	}

}
