package org.alexdev.icarus.game.pets;

import org.alexdev.icarus.dao.mysql.pets.PetDao;
import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.room.user.RoomUser;

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
    private int roomId;
    private int x;
    private int y;
    
    public Pet(int id, String name, int level, int happiness, int experience, int energy, int ownerId, String colour, int raceId, int typeId, boolean saddled, int hair, int hairDye, boolean anyRider, int birthday, int roomId, int x, int y) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.happiness = happiness;
        this.experience = experience;
        this.energy = energy;
        this.ownerId = ownerId;
        this.ownerName = PlayerDao.getName(this.ownerId);
        this.colour = colour;
        this.raceId = raceId;
        this.typeId = typeId;
        this.saddled = saddled;
        this.hair = hair;
        this.hairDye = hairDye;
        this.anyRider = anyRider;
        this.birthday = birthday;
        this.roomId = roomId;
        
        this.roomUser = new RoomUser(this);
        this.playerDetails = new PlayerDetails(this);
        
        this.playerDetails.setName(this.name);
        this.playerDetails.setFigure(this.getLook());
        this.playerDetails.setId(this.id);
        
        if (this.roomId > 0) {
            this.x = x;
            this.y = y;
        }
    }
    
    /* (non-Javadoc)
     * @see org.alexdev.icarus.game.entity.Entity#getDetails()
     */
    @Override
    public PlayerDetails getDetails() {
        return this.playerDetails;
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.game.entity.Entity#getRoomUser()
     */
    @Override
    public RoomUser getRoomUser() {
        return this.roomUser;
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.game.entity.Entity#getType()
     */
    @Override
    public EntityType getType() {
        return EntityType.PET;
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.game.entity.Entity#dispose()
     */
    @Override
    public void dispose() {
        this.disposed = true;
    }
    
    /**
     * Save.
     */
    public void save() {
        PetDao.savePet(this);
    }
    
    /**
     * Save position.
     */
    public void savePosition() {
        PetDao.savePetPosition(this);
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the scratches.
     *
     * @return the scratches
     */
    public int getScratches() {
        return scratches;
    }

    /**
     * Sets the scratches.
     *
     * @param scratches the new scratches
     */
    public void setScratches(int scratches) {
        this.scratches = scratches;
    }

    /**
     * Gets the level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level.
     *
     * @param level the new level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the happiness.
     *
     * @return the happiness
     */
    public int getHappiness() {
        return happiness;
    }

    /**
     * Sets the happiness.
     *
     * @param happiness the new happiness
     */
    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    /**
     * Gets the experience.
     *
     * @return the experience
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Sets the experience.
     *
     * @param experience the new experience
     */
    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * Gets the energy.
     *
     * @return the energy
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * Sets the energy.
     *
     * @param energy the new energy
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Gets the owner id.
     *
     * @return the owner id
     */
    public int getOwnerId() {
        return ownerId;
    }
    
    /**
     * Gets the colour.
     *
     * @return the colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * Sets the colour.
     *
     * @param colour the new colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }
    
   /**
    * Gets the look.
    *
    * @return the look
    */
   public String getLook() {
        return this.getTypeId() + " " + this.getRaceId() + " " + this.getColour();
    }

    /**
     * Gets the race id.
     *
     * @return the race id
     */
    public int getRaceId() {
        return raceId;
    }

    /**
     * Sets the race id.
     *
     * @param raceId the new race id
     */
    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    /**
     * Gets the type id.
     *
     * @return the type id
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * Sets the type id.
     *
     * @param typeId the new type id
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    /**
     * Gets the hair dye.
     *
     * @return the hair dye
     */
    public int getHairDye() {
        return hairDye;
    }

    /**
     * Sets the hair dye.
     *
     * @param hairDye the new hair dye
     */
    public void setHairDye(int hairDye) {
        this.hairDye = hairDye;
    }

    /**
     * Gets the hair.
     *
     * @return the hair
     */
    public int getHair() {
        return hair;
    }

    /**
     * Sets the hair.
     *
     * @param hair the new hair
     */
    public void setHair(int hair) {
        this.hair = hair;
    }

    /**
     * Checks if is any rider.
     *
     * @return true, if is any rider
     */
    public boolean isAnyRider() {
        return anyRider;
    }

    /**
     * Sets the any rider.
     *
     * @param anyRider the new any rider
     */
    public void setAnyRider(boolean anyRider) {
        this.anyRider = anyRider;
    }

    /**
     * Checks if is saddled.
     *
     * @return true, if is saddled
     */
    public boolean isSaddled() {
        return saddled;
    }

    /**
     * Sets the saddled.
     *
     * @param saddled the new saddled
     */
    public void setSaddled(boolean saddled) {
        this.saddled = saddled;
    }

    /**
     * Gets the birthday.
     *
     * @return the birthday
     */
    public int getBirthday() {
        return birthday;
    }

    /**
     * Sets the birthday.
     *
     * @param birthday the new birthday
     */
    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets the owner name.
     *
     * @return the owner name
     */
    public String getOwnerName() {
        return this.ownerName;
    }

    /**
     * Checks if is riding horse.
     *
     * @return the boolean
     */
    public Boolean isRidingHorse() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Checks for rider.
     *
     * @return true, if successful
     */
    public boolean hasRider() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Gets the room id.
     *
     * @return the room id
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Sets the room id.
     *
     * @param roomId the new room id
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x.
     *
     * @param x the new x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y.
     *
     * @param y the new y
     */
    public void setY(int y) {
        this.y = y;
    }
}