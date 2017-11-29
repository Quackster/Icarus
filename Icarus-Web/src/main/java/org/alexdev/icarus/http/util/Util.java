package org.alexdev.icarus.http.util;

import org.alexdev.icarus.http.IcarusWeb;
import org.apache.commons.lang3.RandomUtils;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class Util {

    private static Random random;

    public static Random getRandom() {

        if (random == null) {
            random = new Random();
        }

        return random;
    }

    /**
     * Gets the readable timestamp.
     *
     * @param timestamp the timestamp
     * @return the readable timestamp
     */
    public static String getReadableTimestamp(long timestamp) {

        try {

            long different = System.currentTimeMillis() - (timestamp * 1000);

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;
            return elapsedDays + " days, " + elapsedHours + " hours, " + elapsedMinutes + " minutes, " + elapsedSeconds + " seconds";

        } catch (Exception e){
            IcarusWeb.getErrorLogger().error("Error occurred: ", e);
        }

        return null;
    }

    /**
     * Gets the date formatter for this app.
     *
     * @return the date formatter
     */
    public static DateFormat getDateFormatter() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMM, yyyy");
        return format;
    }

    /**
     * Gets the current formatted date as string.
     *
     * @return the date as string
     */
    public static String getDateAsString() {

        try {
            Date date = new Date();
            return getDateFormatter().format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Gets the date given by unix timestamp as string.
     *
     * @param time the unix timestamp
     * @return the date as string
     */
    public static String getDateAsString(long time) {

        try {
            Date date = new Date();
            date.setTime(time * 1000);
            return getDateFormatter().format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Create random alpha numeric string with given length
     *
     * @param length the string length
     * @return the valid alpha numeric string
     */
    public static String randomString(final int length) {

        char[] alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijlmnopqrstuvwxyz0123456789".toCharArray();

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < length; i++) {
            char c = alphaNumeric[RandomUtils.nextInt(0, alphaNumeric.length)];
            sb.append(c);
        }

        return sb.toString();
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
     * Converts a bitmap file to base64 string
     *
     * @param image the image
     * @return bitmap
     */
    public static String convert(BufferedImage image) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DatatypeConverter.printBase64Binary(output.toByteArray());
    }

    /**
     * Get recent files in the directory
     *
     * @param filePath the directory path
     * @param maxFiles the number of max files
     * @throws IOException
     */
    public static void getRecentFiles(String filePath, int maxFiles) throws IOException {

        int i = 1;

        Path dir = FileSystems.getDefault().getPath(filePath);
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir);

        for (Path path : stream) {

            System.out.println(path.getFileName());

            if (++i > maxFiles)
                break;
        }

        stream.close();
    }
}
