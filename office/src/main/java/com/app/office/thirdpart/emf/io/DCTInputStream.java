package com.app.office.thirdpart.emf.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DCTInputStream extends FilterInputStream {
    public DCTInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public int read() throws IOException {
        throw new IOException(getClass() + ": read() not implemented, use readImage().");
    }
}
