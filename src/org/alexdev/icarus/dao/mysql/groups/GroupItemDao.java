package org.alexdev.icarus.dao.mysql.groups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.groups.types.GroupBackgroundColour;
import org.alexdev.icarus.game.groups.types.GroupBase;
import org.alexdev.icarus.game.groups.types.GroupBaseColour;
import org.alexdev.icarus.game.groups.types.GroupSymbol;
import org.alexdev.icarus.game.groups.types.GroupSymbolColour;
import org.alexdev.icarus.log.Log;

public class GroupItemDao {
    
    /**
     * Gets the group items.
     *
     * @param bases the bases
     * @param symbols the symbols
     * @param baseColours the base colours
     * @param symbolColours the symbol colours
     * @param backgroundColours the background colours
     * @return the group items
     */
    public static void getGroupItems(List<GroupBase> bases, List<GroupSymbol> symbols, List<GroupBaseColour> baseColours, Map<Integer, GroupSymbolColour> symbolColours, Map<Integer, GroupBackgroundColour> backgroundColours) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM group_items", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                
                switch (resultSet.getString("type")) {
                    case "base":
                        bases.add(new GroupBase(resultSet.getInt("id"), resultSet.getString("firstvalue"), resultSet.getString("secondvalue")));
                        break;
    
                    case "symbol":
                        symbols.add(new GroupSymbol(resultSet.getInt("id"), resultSet.getString("firstvalue"), resultSet.getString("secondvalue")));
                        break;
    
                    case "color":
                        baseColours.add(new GroupBaseColour(resultSet.getInt("id"), resultSet.getString("firstvalue")));
                        break;
    
                    case "color2":
                        symbolColours.put(resultSet.getInt("id"), new GroupSymbolColour(resultSet.getInt("id"), resultSet.getString("firstvalue")));
                        break;
    
                    case "color3":
                        backgroundColours.put(resultSet.getInt("id"), new GroupBackgroundColour(resultSet.getInt("id"), resultSet.getString("firstvalue")));
                        break;
                }
            }
            
        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
}
