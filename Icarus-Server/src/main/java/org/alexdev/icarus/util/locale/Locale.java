package org.alexdev.icarus.util.locale;

import org.alexdev.icarus.log.Log;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Locale {

    private Wini locale;
    private String language;

    private static Locale instance;

    public Locale() {
        try {
            this.writeFileIfNotExist();
            this.locale = new Wini(new File("locale.ini"));
            this.language = this.locale.get("Locale", "language", String.class);
        } catch (Exception e) {
            Log.getErrorLogger().error("Unhandled exception while loading the locale: ", e);
        }
    }

    /**
     * Create the configuration files for this application, with the default values. Will throw an
     * exception if it could not create such files.
     *
     * @throws IOException
     */
	private void writeFileIfNotExist() throws IOException {
        
        File file = new File("locale.ini");

        if (!file.isFile()) { 
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
            writeLocaleConfiguration(writer);
            writer.flush();
            writer.close();
        }
	}

    /**
     * Writes default values for the locale
     * 
     * @param writer - {@link PrintWriter} the file writer
     */
    private void writeLocaleConfiguration(PrintWriter writer) {
        writer.println("[Locale]");
        writer.println("language=English");
        writer.println();
        writer.println("[English]");
        writer.println("camera.error=Oops! Could not process the photo, relog maybe?");
        writer.println();
        writer.println("one.dimmer.per.room=You can only have one dimmer per room!");
        writer.println();
        writer.println("group.remove.administrator.denied=Sorry, only group creators can remove other administrators from the group.");
        writer.println("group.existing.member=Sorry, this user is already a group member.");
        writer.println("group.only.creators.add.admin=Sorry, only group creators can give administrator rights to other group members.");
        writer.println("group.only.creators.remove.admin=Sorry, only group creators can remove administrator rights from other group members.");
        writer.println();
        writer.println("player.commands.no.args=You did not supply enough arguments for this command!");
    }


    /**
     * Gets the locale.
     *
     * @param entry the entry
     * @return the locale
     */
    public String getEntry(String entry) {
        return this.locale.get(this.language, entry, String.class);
    }

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static Locale getInstance() {

        if (instance == null) {
            instance = new Locale();
        }

        return instance;
    }
}
