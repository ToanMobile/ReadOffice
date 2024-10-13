package com.app.office.fc.util;

import java.io.File;
import java.util.Random;

public final class TempFile {
    private static File dir;
    private static final Random rnd = new Random();

    public static File createTempFile(String str, String str2) {
        if (dir == null) {
            File file = new File(System.getProperty("java.io.tmpdir"), "poifiles");
            dir = file;
            file.mkdir();
            if (System.getProperty("poi.keep.tmp.files") == null) {
                dir.deleteOnExit();
            }
        }
        File file2 = dir;
        File file3 = new File(file2, str + rnd.nextInt() + str2);
        if (System.getProperty("poi.keep.tmp.files") == null) {
            file3.deleteOnExit();
        }
        return file3;
    }
}
