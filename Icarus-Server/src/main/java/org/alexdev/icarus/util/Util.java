package org.alexdev.icarus.util;

import org.alexdev.icarus.encryption.RSA;
import org.alexdev.icarus.log.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    private static SecureRandom secureRandom;
    private static RSA rsa;

    static {
        secureRandom = new SecureRandom();
        rsa = new RSA();
    }

    /**
     * Checks if is null or empty.
     *
     * @param param the param
     * @return true, if is null or empty
     */
    public static boolean isNullOrEmpty(String param) { 
        return param == null || param.trim().length() == 0;
    }

    /**
     * Filter input.
     *
     * @param input the input
     * @return the string
     */
    public static String filterInput(String input) {
        input = input.replace((char)10, ' ');
        input = input.replace((char)11, ' ');
        input = input.replace((char)12, ' ');
        input = input.replace((char)13, ' ');
        input = input.replace((char)14, ' ');

        return input;
    }

    /**
     * Checks if is number.
     *
     * @param object the object
     * @return true, if is number
     */
    public static boolean isNumber(Object object) {

        try {
            Integer.valueOf(object.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Random int.
     *
     * @param from the from
     * @param to the to
     * @return the int
     */
    public static int randomInt(int from, int to) {
        return randomInt(from, to, false);
    }

    /**
     * Random int.
     *
     * @param from the from
     * @param to the to
     * @param inclusive the inclusive
     * @return the int
     */
    public static int randomInt(int from, int to, boolean inclusive) {
        if (inclusive) {
            return from + secureRandom.nextInt(to + 1);    
        } else {
            return from + secureRandom.nextInt(to);
        }
    }

    /**
     * Checks if is alpha numeric.
     *
     * @param word the word
     * @return true, if is alpha numeric
     */
    public static boolean isAlphaNumeric(String word) {
        return word.matches("[A-Za-z0-9]+");
    }

    /**
     * Removes the non alpha numeric.
     *
     * @param caption the caption
     * @return the string
     */
    public static String removeNonAlphaNumeric(String caption) {
        return caption.replaceAll("[^A-Za-z0-9]", "");
    }
    
    /**
     * Generate random string.
     *
     * @param len the len
     * @return the string
     */
    public static String generateRandomString(int len, boolean uppercase) {
        
        String CHAR_LIST = null;
        
        if (uppercase) {
            CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        } else {
            CHAR_LIST = "abcdefghijklmnopqrstuvwxyz1234567890";
        }
        
        StringBuffer randStr = new StringBuffer();
        
        for (int i = 0; i < len; i++) {
            int number = Util.randomInt(0, CHAR_LIST.length());
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    /**
     * Read to end.
     *
     * @param socket the socket
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String readToEnd(Socket socket) throws IOException {

        byte[] receive = new byte[512];

        String str;

        DataInputStream reader = new DataInputStream(socket.getInputStream());
        reader.read(receive);

        str = new String(receive);
        return str;
    }
    
    /**
     * Paginate a list of items.
     *
     * @param <T> the generic type
     * @param originalList the original list
     * @param chunkSize the chunk size
     * @return the list
     */
    public static <T> List<List<T>> paginate(List<T> originalList, int chunkSize) {
        List<List<T>> listOfChunks = new ArrayList<>();

        for (int i = 0; i < originalList.size() / chunkSize; i++) {
            listOfChunks.add(originalList.subList(i * chunkSize, i * chunkSize + chunkSize));
        }

        if (originalList.size() % chunkSize != 0) {
            listOfChunks.add(originalList.subList(originalList.size() - originalList.size() % chunkSize, originalList.size()));
        }

        return listOfChunks;
    }
    
    /**
     * Round to two decimal places.
     *
     * @param decimal the decimal
     * @return the double
     */
    public static double format(double decimal) {
        return Math.round(decimal * 100.0) / 100.0;
    }
    
    /**
     * Split.
     *
     * @param str the string
     * @param delim the delimiter
     * @return the list
     */
    public static List<String> split(String str, String delim) {
        return new ArrayList<>(Arrays.asList(str.split(delim)));
    }

    /**
     * Gets the random.
     *
     * @return the random
     */
    public static SecureRandom getRandom() {
        return secureRandom;
    }

    /**
     * Gets the rsa.
     *
     * @return the rsa
     */
    public static RSA getRSA() {
        return rsa;
    }
}
