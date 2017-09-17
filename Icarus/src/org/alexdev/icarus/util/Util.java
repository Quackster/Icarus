package org.alexdev.icarus.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.alexdev.icarus.Icarus;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Util {

    private static Wini configuration;
    private static SecureRandom secureRandom;
    private static Wini habboConfig;
    private static String language;
    private static Wini locale;
    private static Map<String, String> composerPaths;

    /**
     * Load.
     *
     * @throws InvalidFileFormatException the invalid file format exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void load() throws InvalidFileFormatException, IOException {
        configuration = new Wini(new File("icarus.properties"));
        habboConfig =  new Wini(new File("habbohotel.properties"));
        locale =  new Wini(new File("locale.ini"));
        secureRandom = new SecureRandom();
        language = locale.get("Locale", "language", String.class);
        composerPaths = Maps.newHashMap();
    }

    /**
     * Creates the composer lookup.
     *
     * @throws Exception the exception
     */
    public static void createComposerLookup() throws Exception {

        for (String packages : Icarus.getServer().getMessageHandler().getComposerPackages()) {
            for (Class<?> clazz : ClassFinder.getClasses(packages)) {
                composerPaths.put(clazz.getSimpleName(), clazz.getName());
            }
        }
    }

    /**
     * Gets the composer.
     *
     * @param className the class name
     * @return the composer
     */
    public static String getComposer(String className) {

        if (composerPaths.containsKey(className)) {
            return composerPaths.get(className);
        }

        return null;
    }

    /**
     * Gets the current time in seconds.
     *
     * @return the current time in seconds
     */
    public static int getCurrentTimeSeconds() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * Gets the locale.
     *
     * @param entry the entry
     * @return the locale
     */
    public static String getLocale(String entry) {
        return locale.get(language, entry, String.class);
    }

    /**
     * Checks if is null or empty.
     *
     * @param param the param
     * @return true, if is null or empty
     */
    public boolean isNullOrEmpty(String param) { 
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
     * Paginate.
     *
     * @param <T> the generic type
     * @param originalList the original list
     * @param chunkSize the chunk size
     * @return the list
     */
    /* Taken from Comet, used for various pagination methods, turned into generic type*/
    public static <T> List<List<T>> paginate(List<T> originalList, int chunkSize) {
        List<List<T>> listOfChunks = Lists.newArrayList();

        for (int i = 0; i < originalList.size() / chunkSize; i++) {
            listOfChunks.add(originalList.subList(i * chunkSize, i * chunkSize + chunkSize));
        }

        if (originalList.size() % chunkSize != 0) {
            listOfChunks.add(originalList.subList(originalList.size() - originalList.size() % chunkSize, originalList.size()));
        }

        return listOfChunks;
    }
    
    /**
     * Split.
     *
     * @param str the string
     * @param delim the delimiter
     * @return the list
     */
    public static List<String> split(String str, String delim) {
        return new ArrayList<String>(Arrays.asList(str.split(delim)));
    }
    
    /**
     * Gets the configuration.
     *
     * @return the configuration
     */
    public static Wini getConfiguration() {
        return configuration;
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
     * Gets the game config.
     *
     * @return the game config
     */
    public static Wini getGameConfig() {
        return habboConfig;
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
}
