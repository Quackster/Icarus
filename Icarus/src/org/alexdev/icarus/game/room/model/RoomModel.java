/*
 * Copyright (c) 2012 Quackster <alex.daniel.97@gmail>. 
 * 
 * This file is part of Sierra.
 * 
 * Sierra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Sierra is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Sierra.  If not, see <http ://www.gnu.org/licenses/>.
 */

package org.alexdev.icarus.game.room.model;

import org.alexdev.icarus.game.pathfinder.Position;

public class RoomModel 
{
    public final static int OPEN = 0;
    public final static int CLOSED = 1;
    
    private String name;
    private String heightmap;
    private String floorMap;
    private String[][] squareChar;
    
    private Position doorLocation;
    
    private int mapSizeX;
    private int mapSizeY;
    
    private int[][] squares;
    private double[][] squareHeight;

    public RoomModel(String name, String heightmap, int doorX, int doorY, int doorZ, int doorRot) {
        
        this.name = name;
        this.heightmap = heightmap;
        
        this.doorLocation = new Position(doorX, doorY, doorZ);
        this.doorLocation.setRotation(doorRot);
        
        String[] temporary = heightmap.split(Character.toString((char)13));

        this.mapSizeX = temporary[0].length();
        this.mapSizeY = temporary.length;
        this.squares = new int[mapSizeX][mapSizeY];
        this.squareHeight = new double[mapSizeX][mapSizeY];
        this.squareChar = new String[mapSizeX][mapSizeY];

        for (int y = 0; y < mapSizeY; y++) {
            
            if (y > 0) {
                temporary[y] = temporary[y].substring(1);
            }

            for (int x = 0; x < mapSizeX; x++) {
                
                String square = temporary[y].substring(x, x + 1).trim().toLowerCase();

                if (square.equals("x"))    {
                    squares[x][y] = CLOSED;
                    
                } else if (isNumeric(square)) {
                    squares[x][y] = OPEN;
                    squareHeight[x][y] = Double.parseDouble(square);
                    
                } else if (isLetter(square)) {
                    squares[x][y] = OPEN;
                    squareHeight[x][y] = parse(square.charAt(0));
                }
                
                if (this.doorLocation.getX() == x && this.doorLocation.getY() == y) {
                    squares[x][y] = OPEN;
                    squareHeight[x][y] = Double.parseDouble(this.doorLocation.getZ() + "");
                }
                
                squareChar[x][y] = square;

            }
        }
        
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < this.getMapSizeY(); i++) {
            for (int j = 0; j < this.getMapSizeX(); j++) {

                try {

                    if (j == this.doorLocation.getX() && i == this.doorLocation.getY())    {
                        stringBuilder.append((int)this.getDoorLocation().getZ());
                    } else {

                        stringBuilder.append(this.getSquareChar()[j][i].toString());
                    }
                }
                catch (Exception e) {
                    stringBuilder.append("0");
                }
            }
            
            stringBuilder.append((char)13);
        }
        
        this.floorMap = stringBuilder.toString();

    }
    
    public double getHeight(Position point) {
        return squareHeight[point.getX()][point.getY()];
    }
    
    public String getHeightMap() {
        return heightmap;
    }
    
    public String getFloorMap() {
        return floorMap;
    }

    private boolean isNumeric(String input) {
        
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
	public static short parse(char input) {
		
		switch (input) {
		
		case 'a':
			return 10;
		case 'b':
			return 11;
		case 'c':
			return 12;
		case 'd':
			return 13;
		case 'e':
			return 14;
		case 'f':
			return 15;
		case 'g':
			return 16;
		case 'h':
			return 17;
		case 'i':
			return 18;
		case 'j':
			return 19;
		case 'k':
			return 20;
		case 'l':
			return 21;
		case 'm':
			return 22;
		case 'n':
			return 23;
		case 'o':
			return 24;
		case 'p':
			return 25;
		case 'q':
			return 26;
		case 'r':
			return 27;
		case 's':
			return 28;
		case 't':
			return 29;
		case 'u':
			return 30;
		case 'v':
			return 31;
		case 'w':
			return 32;

		default:
			return -1;
		}
	}

	private boolean isLetter(String input) {

		try {
			Character.isLetter(input.charAt(0));
			return true;
		} catch (Exception e) {	}

		return false;
	}
    
    public boolean invalidXYCoords(int x, int y) {
        if (x >= this.mapSizeX) {
            return true;
        }

        if (y >= this.mapSizeY) {
            return true;
        }

        if (x < 0) {
            return true;
        }

        if (y < 0) {
            return true;
        }
        
        return false;
    }
    
    public String getName() {
        return name;
    }
    
    public Position getDoorLocation() {
    	return doorLocation;
    }
    
    public int getMapSizeX() {
        return mapSizeX;
    }
    
    public int getMapSizeY() {
        return mapSizeY;
    }
    
    public double getHeight(int x, int y) {
        return squareHeight[x][y];
    }
    
    public boolean isBlocked(int x, int y) {
        return squares[x][y] == RoomModel.CLOSED;
    }

    public String[][] getSquareChar() {
        return squareChar;
    }
}