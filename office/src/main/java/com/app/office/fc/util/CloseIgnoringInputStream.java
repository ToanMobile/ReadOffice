package com.app.office.fc.util;

import java.io.FilterInputStream;
import java.io.InputStream;

public class CloseIgnoringInputStream extends FilterInputStream {
    public void close() {
    }

    public CloseIgnoringInputStream(InputStream inputStream) {
        super(inputStream);
    }
}
