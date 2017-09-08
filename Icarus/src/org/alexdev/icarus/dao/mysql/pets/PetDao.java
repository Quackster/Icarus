package org.alexdev.icarus.dao.mysql.pets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.pets.PetRace;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;

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

    public static int createPet(int ownerId, String petName, int type, int race, String colour) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("INSERT INTO pet_data (owner_id, pet_name, type, race_id, colour, scratches, level, happiness, experience, energy, birthday) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", sqlConnection);

            preparedStatement.setInt(1, ownerId);
            preparedStatement.setString(2, petName);
            preparedStatement.setInt(3, type);
            preparedStatement.setInt(4, race);
            preparedStatement.setString(5, colour);
            preparedStatement.setInt(6, 0);
            preparedStatement.setInt(7, Pet.DEFAULT_LEVEL);
            preparedStatement.setInt(8, Pet.DEFAULT_HAPPINESS);
            preparedStatement.setInt(9, Pet.DEFAULT_EXPERIENCE);
            preparedStatement.setInt(10, Pet.DEFAULT_ENERGY);
            preparedStatement.setInt(11, Util.getCurrentTimeSeconds());

            preparedStatement.execute();

            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return 0;
    }

    public static List<Pet> getRoomPets(int roomId) {

        List<Pet> pets = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM pet_data WHERE room_id = " + roomId, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pets.add(fill(resultSet));
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return pets;
    }

    public static void savePet(Pet pet) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE pet_data SET level = ?, room_id = ?, energy = ?, happiness = ?, experience = ? WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, pet.getLevel());
            preparedStatement.setInt(2, pet.getRoomId());
            preparedStatement.setInt(3, pet.getEnergy());
            preparedStatement.setInt(4, pet.getHappiness());
            preparedStatement.setInt(5, pet.getExperience());
            preparedStatement.setInt(6, pet.getId());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
    
    public static void savePetPosition(Pet pet) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        pet.setX(pet.getRoomUser().getPosition().getX());
        pet.setY(pet.getRoomUser().getPosition().getY());

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE pet_data SET x = ?, y = ? WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, pet.getX());
            preparedStatement.setInt(2, pet.getY());
            preparedStatement.setInt(3, pet.getId());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static Pet fill(ResultSet data) throws SQLException {
        Pet pet = new Pet(data.getInt("id"), data.getString("pet_name"), data.getInt("level"), data.getInt("happiness"), data.getInt("experience"), data.getInt("energy"), data.getInt("owner_id"), data.getString("colour"), data.getInt("race_id"), data.getInt("type"), data.getBoolean("saddled"), data.getInt("hair_style"), data.getInt("hair_colour"), data.getBoolean("any_rider"), data.getInt("birthday"), data.getInt("room_id"), data.getInt("x"), data.getInt("y"));
        return pet;
    }
}
