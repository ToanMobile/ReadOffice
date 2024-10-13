package com.app.office.thirdpart.emf.io;

import android.graphics.Bitmap;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

public class FlateInputStream extends InflaterInputStream {
    public Bitmap readImage() throws IOException {
        return null;
    }

    public FlateInputStream(InputStream inputStream) {
        super(inputStream);
    }
}
