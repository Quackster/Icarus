package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.alexdev.icarus.game.pets.PetRace;
import org.alexdev.icarus.log.Log;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class PetDao {

	public static Map<Integer, List<PetRace>> getPetRaces() {

		Map<Integer, List<PetRace>> races = Maps.newHashMap();

		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();

			preparedStatement = Dao.getStorage().prepare("SELECT * FROM pet_races", sqlConnection);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				PetRace race = new PetRace(resultSet.getInt("race_id"), resultSet.getInt("colour1"), resultSet.getInt("colour2"), resultSet.getInt("has1colour") == 1, resultSet.getInt("has2colour") == 1);
				
				if (!races.containsKey(race.getRaceId())) {
					races.put(race.getRaceId(), Lists.newArrayList());
				}
				
				races.get(race.getRaceId()).add(race);
			}

		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.closeSilently(resultSet);
			Storage.closeSilently(preparedStatement);
			Storage.closeSilently(sqlConnection);
		}

		return races;
	}

}
