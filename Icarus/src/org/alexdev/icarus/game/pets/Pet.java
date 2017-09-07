package org.alexdev.icarus.game.pets;

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

    private int ownerId;
    private String colour;
    private int raceId;
    private int typeId;

    private int hairDye = 0;
    private int hair = -1;

    private boolean anyRider = false;
    private boolean saddled = false;

    private int birthday;
    
    private RoomUser roomUser;
    private PlayerDetails playerDetails;
    private String ownerName;
    
    public Pet(int id, String name, int level, int happiness, int experience, int energy, int ownerId, String colour, int raceId, int typeId, boolean saddled, int hair, int hairDye, boolean anyRider, int birthday) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.happiness = happiness;
        this.experience = experience;
        this.energy = energy;
        this.ownerId = ownerId;
        this.ownerName = PlayerDao.getName(id);
        this.colour = colour;
        this.raceId = raceId;
        this.typeId = typeId;
        this.saddled = saddled;
        this.hair = hair;
        this.hairDye = hairDye;
        this.anyRider = anyRider;
        this.birthday = birthday;
        
        this.roomUser = new RoomUser(this);
        this.playerDetails = new PlayerDetails(this);
        
        this.playerDetails.setName(this.name);
        this.playerDetails.setFigure(this.getLook());
        this.playerDetails.setId(this.id);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getOwnerId() {
        return ownerId;
    }
    
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
    
   public String getLook() {
        return this.getTypeId() + " " + this.getRaceId() + " " + this.getColour();
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
}
