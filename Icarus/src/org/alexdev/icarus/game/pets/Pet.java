package org.alexdev.icarus.game.pets;

import org.alexdev.icarus.dao.mysql.pets.PetDao;
import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.room.RoomUser;

public class Pet extends Entity {
    public static final int DEFAULT_LEVEL = 1;
    public static final int DEFAULT_ENERGY = 100;
    public static final int DEFAULT_HAPPINESS = 100;
    public static final int DEFAULT_EXPERIENCE = 0;
    
    private int id;
    private String name;

    private int scratches;
    private int level;
    private int happiness;
    private int experience;
    private int energy;

    private int ownerID;
    private String colour;
    private int raceID;
    private int typeID;

    private int hairDye = 0;
    private int hair = -1;

    private boolean anyRider = false;
    private boolean saddled = false;

    private int birthday;
    
    private RoomUser roomUser;
    private PlayerDetails playerDetails;
    private String ownerName;
    private int roomID;
    
    private int x;
    private int y;
    
    public Pet(int id, String name, int level, int happiness, int experience, int energy, int ownerID, String colour, int raceID, int typeID, boolean saddled, int hair, int hairDye, boolean anyRider, int birthday, int roomID, int x, int y) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.happiness = happiness;
        this.experience = experience;
        this.energy = energy;
        this.ownerID = ownerID;
        this.ownerName = PlayerDao.getName(this.ownerID);
        this.colour = colour;
        this.raceID = raceID;
        this.typeID = typeID;
        this.saddled = saddled;
        this.hair = hair;
        this.hairDye = hairDye;
        this.anyRider = anyRider;
        this.birthday = birthday;
        this.roomID = roomID;
        
        this.roomUser = new RoomUser(this);
        this.playerDetails = new PlayerDetails(this);
        
        this.playerDetails.setName(this.name);
        this.playerDetails.setFigure(this.getLook());
        this.playerDetails.setID(this.id);
        
        if (this.roomID > 0) {
            this.x = x;
            this.y = y;
        }
    }
    
    @Override
    public PlayerDetails getDetails() {
        return this.playerDetails;
    }

    @Override
    public RoomUser getRoomUser() {
        return this.roomUser;
    }

    @Override
    public EntityType getType() {
        return EntityType.PET;
    }

    @Override
    public void dispose() {
        this.disposed = true;
    }
    
    public void save() {
        PetDao.savePet(this);
    }
    
    public void savePosition() {
        PetDao.savePetPosition(this);
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScratches() {
        return scratches;
    }

    public void setScratches(int scratches) {
        this.scratches = scratches;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getOwnerID() {
        return ownerID;
    }
    
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
    
   public String getLook() {
        return this.getTypeID() + " " + this.getRaceID() + " " + this.getColour();
    }

    public int getRaceID() {
        return raceID;
    }

    public void setRaceID(int raceID) {
        this.raceID = raceID;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getHairDye() {
        return hairDye;
    }

    public void setHairDye(int hairDye) {
        this.hairDye = hairDye;
    }

    public int getHair() {
        return hair;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public boolean isAnyRider() {
        return anyRider;
    }

    public void setAnyRider(boolean anyRider) {
        this.anyRider = anyRider;
    }

    public boolean isSaddled() {
        return saddled;
    }

    public void setSaddled(boolean saddled) {
        this.saddled = saddled;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public Boolean isRidingHorse() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean hasRider() {
        // TODO Auto-generated method stub
        return false;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
