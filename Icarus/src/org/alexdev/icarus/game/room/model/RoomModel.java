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

public class RoomModel {
    
    public final static int OPEN = 0;
    public final static int CLOSED = 1;

    private String name;
    private String heightmap;

    private Position doorLocation;

    private int mapSizeX;
    private int mapSizeY;

    private int[][] squares;
    private double[][] squareHeight;
    
    private String relativeHeightmap;

    public RoomModel(String name, String heightmap, int doorX, int doorY, int doorZ, int doorRot) {

        this.name = name;
        this.heightmap = heightmap;

        this.doorLocation = new Position(doorX, doorY, doorZ);
        this.doorLocation.setRotation(doorRot);

        String[] temporary = heightmap.split("\\{13}");

        this.mapSizeX = temporary[0].length();
        this.mapSizeY = temporary.length;
        this.squares = new int[mapSizeX][mapSizeY];
        this.squareHeight = new double[mapSizeX][mapSizeY];

        for (int y = 0; y < mapSizeY; y++) {

            String line = temporary[y];
                
            line = line.replace(Character.toString((char)10), "");
            line = line.replace(Character.toString((char)13), "");
            
            int x = 0;

            for (char square : line.toCharArray()) {

                if (square == 'x') {
                    this.squares[x][y] = CLOSED;
                } else {

                    this.squares[x][y] = OPEN;
                    this.squareHeight[x][y] = parse(square);
                }
                
                if (x == this.doorLocation.getX() && y == this.doorLocation.getY()) {
                    this.squares[x][y] = OPEN; 
                    this.squareHeight[x][y] = this.doorLocation.getZ();
                }

                x++;
            }
        }

        this.generateRelativeHeightmap();
    }

    private void generateRelativeHeightmap() {

        StringBuilder relativeMap = new StringBuilder();

        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {

                if (x == this.doorLocation.getX() && y == this.doorLocation.getY()) {

                    if (this.doorLocation.getZ() > 9) {
                        relativeMap.append((char)(87 + this.doorLocation.getZ()));
                    } else {
                        relativeMap.append((int)this.doorLocation.getZ());
                    }
                    
                    continue;
                }

                if (this.squares[x][y] == CLOSED) {
                    relativeMap.append('x');
                    continue;
                }

                double height = this.squareHeight[x][y];

                if (height > 9) {
                    relativeMap.append((char)(87 + height));
                } else {
                    relativeMap.append((int)height);
                }
                
                this.squares[x][y] = OPEN;

            }

            relativeMap.append(Character.toString((char)13));
        }

        this.relativeHeightmap = relativeMap.toString();
    }

    public static double parse(char input) {

        switch (input) {
        case '0':
            return 0;
        case '1':
            return 1;
        case '2':
            return 2;
        case '3':
            return 3;
        case '4':
            return 4;
        case '5':
            return 5;
        case '6':
            return 6;
        case '7':
            return 7;
        case '8':
            return 8;
        case '9':
            return 9;
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

    public double getHeight(Position point) {
        return squareHeight[point.getX()][point.getY()];
    }

    public String getHeightMap() {
        return heightmap;
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

    public String getRelativeHeightmap() {
        return relativeHeightmap;
    }

    public void setRelativeHeightmap(String relativeHeightmap) {
        this.relativeHeightmap = relativeHeightmap;
    }

    public int getWallHeight() {
        return -1;
    }
}