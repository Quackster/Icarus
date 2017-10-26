package org.alexdev.icarus.web.util;

import org.alexdev.icarus.web.IcarusWeb;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {

    public static String getMimeType(File file) {
        return MimeType.valueOf(FilenameUtils.getExtension(file.getName())).contentType;
    }

    public static byte[] readFile(String relativePath) {
        File file = Paths.get(IcarusWeb.getSiteDirectory(), relativePath).toFile();
        return readFile(file);
    }

    public static byte[] readFile(File file) {

        try {
            return Files.readAllBytes(Paths.get(file.getCanonicalPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
