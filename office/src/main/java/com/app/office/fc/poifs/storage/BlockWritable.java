package com.app.office.fc.poifs.storage;

import java.io.IOException;
import java.io.OutputStream;

public interface BlockWritable {
    void writeBlocks(OutputStream outputStream) throws IOException;
}
