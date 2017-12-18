package org.alexdev.icarus.util;

import java.util.Random;

public class RandomInteger {
    public static int getRandom(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}