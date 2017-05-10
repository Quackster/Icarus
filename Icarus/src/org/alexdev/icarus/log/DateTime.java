package org.alexdev.icarus.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

    private static SimpleDateFormat m_momentFormat = new SimpleDateFormat("dd-MM-yyyy hh:MM:ss");
    private static SimpleDateFormat m_shortMomentFormat = new SimpleDateFormat("dd/MM/yy");

    
    /**
     * Provides functions for working with date and time.
     * 
     * @author Nillus
     */

    public static long getTime() {
        return System.currentTimeMillis();
    }

    public static long getTimeSeconds() {
        return System.currentTimeMillis() / 1000L;
    }
    
    public static Date getDateTime(long time) {
        Date date = new Date(DateTime.getTime());
        return date;
    }
    
    public static Date getDateTime() {
        return getDateTime(DateTime.getTime());
    }

    public static String formatDateTime() {
        return DateTime.formatDateTime(DateTime.getDateTime());
    }

    public static String formatDateTime(Date d) {
        return m_momentFormat.format(d);
    }

    public static long calculateDaysElapsed(Date d) {
        long diff = DateTime.getTime() - d.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }

    public static SimpleDateFormat getMomentFormatter() {
        return m_momentFormat;
    }

    public static String formatDateTime(long timestamp) {
        return DateTime.formatDateTime(getDateTime(timestamp));
    }

    public static String toShortDate() {
        return m_shortMomentFormat.format(getDateTime());
    }
}
