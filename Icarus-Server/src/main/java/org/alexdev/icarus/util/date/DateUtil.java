package org.alexdev.icarus.util.date;

import org.alexdev.icarus.log.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

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
            Log.getErrorLogger().error("Error occurred: ", e);
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
}
