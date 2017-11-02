package org.alexdev.icarus.dao.mysql.pets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.pets.PetRace;

import org.alexdev.icarus.util.Util;

public class PetDao {

    /**
     * Gets the pet races.
     *
     * @return the pet races
     */
    public static Map<Integer, List<PetRace>> getPetRaces() {

        Map<Integer, List<PetRace>> races = new HashMap<>();

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
                    races.put(race.getRaceId(), new ArrayList<>());
                }

                races.get(race.getRaceId()).add(race);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return races;
    }

    /**
     * Creates the pet.
     *
     * @param ownerId the owner id
     * @param petName the pet name
     * @param type the type
     * @param race the race
     * @param colour the colour
     * @return the int
     */
    public static int createPet(int ownerId, String petName, int type, int race, String colour) {

        int petId = -1;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("INSERT INTO pets (owner_id, pet_name, type, race_id, colour, scratches, level, happiness, experience, energy, birthday) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", sqlConnection);

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
                petId = resultSet.getInt(1);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return petId;
    }

    /**
     * Gets the room pets.
     *
     * @param roomId the room id
     * @return the room pets
     */
    public static List<Pet> getRoomPets(int roomId) {

        List<Pet> pets = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM pets WHERE room_id = " + roomId, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                pets.add(fill(resultSet));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return pets;
    }

    /**
     * Save pet.
     *
     * @param pet the pet
     */
    public static void savePet(Pet pet) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE pets SET level = ?, room_id = ?, energy = ?, happiness = ?, experience = ? WHERE id = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, pet.getLevel());
            preparedStatement.setInt(2, pet.getRoomId());
            preparedStatement.setInt(3, pet.getEnergy());
            preparedStatement.setInt(4, pet.getHappiness());
            preparedStatement.setInt(5, pet.getExperience());
            preparedStatement.setInt(6, pet.getId());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
    
    /**
     * Save pet position.
     *
     * @param pet the pet
     */
    public static void savePetPosition(Pet pet) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        pet.setX(pet.getRoomUser().getPosition().getX());
        pet.setY(pet.getRoomUser().getPosition().getY());

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE pets SET x = ?, y = ? WHERE id = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, pet.getX());
            preparedStatement.setInt(2, pet.getY());
            preparedStatement.setInt(3, pet.getId());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Fill.
     *
     * @param data the data
     * @return the pet
     * @throws SQLException the SQL exception
     */
    public static Pet fill(ResultSet data) throws SQLException {
        Pet pet = new Pet(data.getInt("id"), data.getString("pet_name"), data.getInt("level"), data.getInt("happiness"), data.getInt("experience"), data.getInt("energy"), data.getInt("owner_id"), data.getString("colour"), data.getInt("race_id"), data.getInt("type"), data.getBoolean("saddled"), data.getInt("hair_style"), data.getInt("hair_colour"), data.getBoolean("any_rider"), data.getInt("birthday"), data.getInt("room_id"), data.getInt("x"), data.getInt("y"));
        return pet;
    }
}
