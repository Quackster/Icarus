package org.alexdev.icarus.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.alexdev.icarus.util.Util;

public class Log {

    private final static String PREFIX = "ICARUS";

    /**
     * Startup.
     */
    public static void startup() {

        output("", false);
        output("-----------------------------------------", false);
        output("-- SERVER BOOT TIME: " + formatDateTime(), false);
        output("-----------------------------------------", false);
        output("", false);

        info("Icarus - Habbo Hotel PRODUCTION63 Server");
        info("Loading server...");
        info();
    }

    /**
     * Format date time.
     *
     * @return the string
     */
    private static String formatDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * Generate data format.
     *
     * @return the string
     */
    private static String generateDataFormat() {
        return "[" + formatDateTime() + "]";//DateTime.now();
    }

    /**
     * Info.
     */
    public static void info() {
        output(generateDataFormat() + " [" + PREFIX + "] ");
    }

    /**
     * Info.
     *
     * @param format the format
     */
    public static void info(Object format)  {
        output(generateDataFormat() + " [" + PREFIX + "] >> " + format.toString());
    }

    /**
     * Output.
     *
     * @param string the string
     */
    private static void output(String string) {
        output(string, true);
    }

    /**
     * Output.
     *
     * @param string the string
     * @param log the log
     */
    private static void output(String string, boolean log) {

        if (log) {
            System.out.println(string);
        }

        if (Util.getConfiguration().get("Logging", "log.output", boolean.class)) {
            writeToFile("log/output.log", string);
        }
    }

    /**
     * Exception.
     *
     * @param e the e
     */
    public static void exception(Exception e) {

        info("---------------------------------------------");
        info("Error has occured!");
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        output(exceptionAsString, false);
        e.printStackTrace();
        info("---------------------------------------------");

        if (Util.getConfiguration().get("Logging", "log.errors", boolean.class)) {
            writeToFile("log/error.log", "---------------------------------------------");
            writeToFile("log/error.log", " " + formatDateTime() + " - Error has occured!");
            writeToFile("log/error.log", exceptionAsString);
        }
    }
    
    /**
     * Write to file.
     *
     * @param dir the dir
     * @param string the string
     */
    private static void writeToFile(String dir, String string) {

        File file = new File(dir);
        
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        } catch (Exception e1) { }

        try {
            if (!file.exists()) {     
                file.createNewFile();
            }

            PrintWriter writer =  new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true)));
            writer.println(string);
            writer.flush();
            writer.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
