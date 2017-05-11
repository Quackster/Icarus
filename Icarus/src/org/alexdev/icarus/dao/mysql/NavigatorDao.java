package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.alexdev.icarus.factories.NavigatorFactory;
import org.alexdev.icarus.game.navigator.NavigatorCategory;
import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.log.Log;

import com.google.common.collect.Lists;

public class NavigatorDao {

	public static List<NavigatorTab> getTabs(int childId) {

		List<NavigatorTab> tabs = Lists.newArrayList();

		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			preparedStatement = Dao.getStorage().prepare("SELECT * FROM navigator_tabs WHERE child_id = " + childId, sqlConnection);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				NavigatorTab tab = fill(resultSet);

				tabs.add(tab);
				tabs.addAll(getTabs(tab.getId()));
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
	
	   public static List<NavigatorCategory> getCategories() {

	        List<NavigatorCategory> categories = Lists.newArrayList();

	        Connection sqlConnection = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;

	        try {

	            sqlConnection = Dao.getStorage().getConnection();
	            preparedStatement = Dao.getStorage().prepare("SELECT * FROM navigator_categories", sqlConnection);
	            resultSet = preparedStatement.executeQuery();

	            while (resultSet.next()) {
	                categories.add(new NavigatorCategory(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getInt("min_rank")));
	            }

	        } catch (Exception e) {
	            Log.exception(e);
	        } finally {
	            Storage.closeSilently(resultSet);
	            Storage.closeSilently(preparedStatement);
	            Storage.closeSilently(sqlConnection);
	        }

	        return categories;
	    }

	public static NavigatorTab fill(ResultSet set) throws SQLException {

		NavigatorTab instance = NavigatorFactory.newTab();
		instance.fill(set.getInt("id"), set.getInt("child_id"), set.getString("tab_name"), set.getString("title"), set.getByte("button_type"), 
		                set.getByte("closed") == 1, set.getByte("thumbnail") == 1, set.getString("room_populator"));

		return instance;
	}

}
