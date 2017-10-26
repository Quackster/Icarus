package org.alexdev.icarus.web;

import fi.iki.elonen.NanoHTTPD;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static fi.iki.elonen.NanoHTTPD.newChunkedResponse;

public class WebResponse {
    public static String getForbidden() {
        return "\n" + "<html>\n" + "<head>\n" + "</head>\n" + "<body>\n" + "   <h1>Forbidden</h1>\n" + "<body>\n" + "</html>";
    }

    public static String getNotFound() {
        return "\n" + "<html>\n" + "<head>\n" + "</head>\n" + "<body>\n" + "   <h1>Not Found</h1>\n" + "<body>\n" + "</html>";
    }

    public static NanoHTTPD.Response getFileResponse(File file) {
        String mimeType = MimeType.valueOf(FilenameUtils.getExtension(file.getName())).contentType;
        try {
            return newChunkedResponse(NanoHTTPD.Response.Status.OK, mimeType, new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
