package org.alexdev.icarus.game.room.model;

public class Rotation {
    
    /*    
    public final static double PI_DIVIDE = Math.PI / 4.0;
    
    public static byte calculate(int X, int Y, int toX, int toY) {
       
        double number = Math.atan2(toY - Y, toX - X);
        double var = Math.round(number / PI_DIVIDE);
        
        return (byte)(var + (var < -2L ? 10 : 2));
    }
    */
    
    public static int calculate(int x1, int y1, int X2, int Y2) {
        int rotation = 0;

        if (x1 > X2 && y1 > Y2)
            rotation = 7;
        else if (x1 < X2 && y1 < Y2)
            rotation = 3;
        else if (x1 > X2 && y1 < Y2)
            rotation = 5;
        else if (x1 < X2 && y1 > Y2)
            rotation = 1;
        else if (x1 > X2)
            rotation = 6;
        else if (x1 < X2)
            rotation = 2;
        else if (y1 < Y2)
            rotation = 4;
        else if (y1 > Y2)
            rotation = 0;
  
        return rotation;
    }
}
