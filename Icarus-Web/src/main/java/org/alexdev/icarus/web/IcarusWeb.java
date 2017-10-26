package org.alexdev.icarus.web;

import fi.iki.elonen.NanoHTTPD;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IcarusWeb extends NanoHTTPD {

    private static String websitePath = "C:/Users/Alex/Documents/GitHub/Icarus/Icarus-Web/site";
    private static IcarusWeb instance;

    public IcarusWeb(int port) {
        super(port);
    }

    public static void main(String[] args) {

        int port = Integer.parseInt(args[0]);
        System.out.println("Starting web service on port " + port);

        instance = new IcarusWeb(port);

        try {
            instance.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response serve(IHTTPSession session) {

        try {
            System.out.println(websitePath + session.getUri());

            File file = new File(websitePath + session.getUri());
            String mimeType = "";

            if (file != null && file.exists()) {

                if (file.isFile()) {
                    return WebResponse.getFileResponse(file);
                }

                if (file.isDirectory()) {
                    File index = new File(file.getCanonicalPath() + File.separator + "index.html");

                    System.out.println(file.getCanonicalPath() + File.separator + "index.html");

                    if (index.exists() && index.isFile()) {
                        return WebResponse.getFileResponse(index);
                    }

                    session.getHeaders().clear();
                    return newFixedLengthResponse(Response.Status.FORBIDDEN, "text/html", WebResponse.getForbidden());
                }
            } else {
                session.getHeaders().clear();
                return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/html", WebResponse.getNotFound());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        session.
        return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/html", WebResponse.getNotFound());
    }

    static String readFile(String path) {

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, Charset.forName("ISO-8859-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
