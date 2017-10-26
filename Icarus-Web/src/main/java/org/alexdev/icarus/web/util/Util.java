package org.alexdev.icarus.web.util;

import java.io.File;

public class Util {
    public static String combine (String path1, String path2) {
        File file1 = new File(path1);
        File file2 = new File(file1, path2);
        return file2.getPath();
    }
}
