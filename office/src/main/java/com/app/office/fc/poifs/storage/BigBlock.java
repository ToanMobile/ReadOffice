package com.app.office.fc.poifs.storage;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import java.io.IOException;
import java.io.OutputStream;

abstract class BigBlock implements BlockWritable {
    protected POIFSBigBlockSize bigBlockSize;

    /* access modifiers changed from: package-private */
    public abstract void writeData(OutputStream outputStream) throws IOException;

    protected BigBlock(POIFSBigBlockSize pOIFSBigBlockSize) {
        this.bigBlockSize = pOIFSBigBlockSize;
    }

    /* access modifiers changed from: protected */
    public void doWriteData(OutputStream outputStream, byte[] bArr) throws IOException {
        outputStream.write(bArr);
    }

    public void writeBlocks(OutputStream outputStream) throws IOException {
        writeData(outputStream);
    }
}
