package org.alexdev.icarus.http.util.config;

import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.icarus.http.util.web.TwigTemplate;
import org.apache.log4j.PropertyConfigurator;
import org.ini4j.Wini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Configuration {

    private Wini configuration;

    public static String TEMPLATE_NAME;
    public static String TEMPLATE_DIRECTORY;

    public static String PUBLIC_RECAPTCHA_KEY;
    public static String PRIVATE_RECAPTCHA_KEY;

    public static String REGISTER_FIGURE;
    public static String REGISTER_MOTTO;

    public static Integer REGISTER_CREDITS;
    public static Integer REGISTER_DUCKETS;

    public static String CLIENT_IP;
    public static String CLIENT_PORT;
    public static String CLIENT_EXTERNAL_VARIABLES;
    public static String CLIENT_EXTERNAL_FLASH_TEXTS;
    public static String CLIENT_EXTERNAL_OVERRIDE_TEXTS;
    public static String CLIENT_EXTERNAL_OVERRIDE_VARIABLES;
    public static String CLIENT_PRODUCTDATA;
    public static String CLIENT_FURNIDATA;
    public static String CLIENT_FIGUREDATA;
    public static String CLIENT_PATH;
    public static String CLIENT_SWF;

    public void load() throws IOException {

        this.checkLog4j();

        File file = new File("site-config.ini");
        if (!file.isFile()) {
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
            writeSiteConfiguration(writer);
            writer.flush();
            writer.close();
        }

        configuration = new Wini(new File("site-config.ini"));
        loadConfiguration();
    }

    /**
     * Create the configuration files for this application, with the default values. Will throw an
     * exception if it could not create such files.
     *
     * @throws IOException
     */
    private void checkLog4j() throws FileNotFoundException {

        String output = "log4j.rootLogger=INFO, stdout\n" +
                "log4j.appender.stdout.threshold=info\n" +
                "log4j.appender.stdout=org.apache.log4j.ConsoleAppender\n" +
                "log4j.appender.stdout.Target=System.out\n" +
                "log4j.appender.stdout.layout=org.apache.log4j.PatternLayout\n" +
                "log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd'T'HH:mm:ss.SSS} %-5p [%c] - %m%n\n" +
                "\n" +
                "# Create new logger information for error\n" +
                "log4j.logger.ErrorLogger=ERROR, error, FILE\n" +
                "log4j.additivity.ErrorLogger=false\n" +
                "\n" +
                "# Set settings for the error logger\n" +
                "log4j.appender.error=org.apache.log4j.ConsoleAppender\n" +
                "log4j.appender.error.Target=System.err\n" +
                "log4j.appender.error.layout=org.apache.log4j.PatternLayout\n" +
                "log4j.appender.error.layout.ConversionPattern=%d{yyyy-MM-dd'T'HH:mm:ss.SSS} %-5p [%c] - %m%n\n" +
                "\n" +
                "# Define the file appender\n" +
                "log4j.appender.FILE=org.apache.log4j.FileAppender\n" +
                "log4j.appender.FILE.File=error.log\n" +
                "log4j.appender.FILE.ImmediateFlush=true\n" +
                "log4j.appender.FILE.Threshold=debug\n" +
                "log4j.appender.FILE.Append=false\n" +
                "log4j.appender.FILE.layout=org.apache.log4j.PatternLayout\n" +
                "log4j.appender.FILE.layout.conversionPattern=%d{yyyy-MM-dd'T'HH:mm:ss.SSS} - [%c] - %m%n\n";

        File loggingConfig = new File("log4j.properties");

        if (!loggingConfig.exists()) {
            PrintWriter writer = new PrintWriter(loggingConfig.getAbsoluteFile());
            writer.write(output);
            writer.flush();
            writer.close();
        }

        // Change the path where the logger property should be read from
        PropertyConfigurator.configure(loggingConfig.getAbsolutePath());
    }

    private static void writeSiteConfiguration(PrintWriter writer) {
        writer.println("[Register]");
        writer.println("register.figure=ch-210-66.hr-100-0.sh-290-80.hd-180-7.lg-270-82");
        writer.println("register.credits=5000");
        writer.println("register.duckets=200");
        writer.println("register.motto=I'm a new user");
        writer.println();
        writer.println("[Client]");
        writer.println("client.ip=");
        writer.println("client.port=");
        writer.println("client.external.variables=");
        writer.println("client.external.flash.texts=");
        writer.println("client.external.override.texts=");
        writer.println("client.external.override.variables=");
        writer.println("client.productdata=");
        writer.println("client.furnidata=");
        writer.println("client.figuredata=");
        writer.println("client.path=");
        writer.println("client.swf=");
        writer.println();
        writer.println("[Directories]");
        writer.println("site.directory=tools/www");
        writer.println("template.directory=tools/www-tpl");
        writer.println();
        writer.println("[Template]");
        writer.println("template.name=default");
        writer.println();
        writer.println("[ReCaptcha]");
        writer.println("recaptcha.public.key=");
        writer.println("recaptcha.private.key=");
    }

    private void loadConfiguration() {
        TEMPLATE_DIRECTORY = configuration.get("Directories", "template.directory");
        TEMPLATE_NAME = configuration.get("Template", "template.name");
        PUBLIC_RECAPTCHA_KEY = configuration.get("ReCaptcha", "recaptcha.public.key");
        PRIVATE_RECAPTCHA_KEY = configuration.get("ReCaptcha", "recaptcha.private.key");
        REGISTER_FIGURE = configuration.get("Register", "register.figure");
        REGISTER_CREDITS = configuration.get("Register", "register.credits", Integer.class);
        REGISTER_DUCKETS = configuration.get("Register", "register.duckets", Integer.class);
        REGISTER_MOTTO = configuration.get("Register", "register.motto");
        CLIENT_IP = configuration.get("Client", "client.ip");
        CLIENT_PORT = configuration.get("Client", "client.port");

        CLIENT_EXTERNAL_VARIABLES =configuration.get("Client", "client.external.variables");
        CLIENT_EXTERNAL_FLASH_TEXTS  =configuration.get("Client", "client.external.flash.texts");
        CLIENT_EXTERNAL_OVERRIDE_TEXTS = configuration.get("Client", "client.external.override.texts");
        CLIENT_EXTERNAL_OVERRIDE_VARIABLES = configuration.get("Client", "client.external.override.variables");
        CLIENT_PRODUCTDATA = configuration.get("Client", "client.productdata");
        CLIENT_FURNIDATA = configuration.get("Client", "client.furnidata");
        CLIENT_FIGUREDATA = configuration.get("Client", "client.figuredata");
        CLIENT_PATH = configuration.get("Client", "client.path");
        CLIENT_SWF = configuration.get("Client", "client.swf");


    }

    public void setSettings(Settings settings) {
        settings.setSiteDirectory(configuration.get("Directories", "site.directory"));
        settings.setTemplateHook(TwigTemplate.class);
    }
    
    public Wini values() {
        return configuration;
    }
}
