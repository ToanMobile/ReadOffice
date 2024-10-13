package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.util.LittleEndianInput;
import java.io.IOException;
import java.io.InputStream;

public class DocumentInputStream extends InputStream implements LittleEndianInput {
    protected static final int EOF = -1;
    protected static final int SIZE_INT = 4;
    protected static final int SIZE_LONG = 8;
    protected static final int SIZE_SHORT = 2;
    private DocumentInputStream delegate;

    public boolean markSupported() {
        return true;
    }

    protected DocumentInputStream() {
    }

    public DocumentInputStream(DocumentEntry documentEntry) throws IOException {
        if (documentEntry instanceof DocumentNode) {
            DirectoryNode directoryNode = (DirectoryNode) documentEntry.getParent();
            if (((DocumentNode) documentEntry).getDocument() != null) {
                this.delegate = new ODocumentInputStream(documentEntry);
            } else if (directoryNode.getFileSystem() != null) {
                this.delegate = new ODocumentInputStream(documentEntry);
            } else if (directoryNode.getNFileSystem() != null) {
                this.delegate = new NDocumentInputStream(documentEntry);
            } else {
                throw new IOException("No FileSystem bound on the parent, can't read contents");
            }
        } else {
            throw new IOException("Cannot open internal document storage");
        }
    }

    public DocumentInputStream(POIFSDocument pOIFSDocument) {
        this.delegate = new ODocumentInputStream(pOIFSDocument);
    }

    public DocumentInputStream(NPOIFSDocument nPOIFSDocument) {
        this.delegate = new NDocumentInputStream(nPOIFSDocument);
    }

    public int available() {
        return this.delegate.available();
    }

    public void close() {
        this.delegate.close();
    }

    public void mark(int i) {
        this.delegate.mark(i);
    }

    public int read() throws IOException {
        return this.delegate.read();
    }

    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.delegate.read(bArr, i, i2);
    }

    public void reset() {
        this.delegate.reset();
    }

    public long skip(long j) throws IOException {
        return this.delegate.skip(j);
    }

    public byte readByte() {
        return this.delegate.readByte();
    }

    public double readDouble() {
        return this.delegate.readDouble();
    }

    public short readShort() {
        return (short) readUShort();
    }

    public void readFully(byte[] bArr) {
        readFully(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr, int i, int i2) {
        this.delegate.readFully(bArr, i, i2);
    }

    public long readLong() {
        return this.delegate.readLong();
    }

    public int readInt() {
        return this.delegate.readInt();
    }

    public int readUShort() {
        return this.delegate.readUShort();
    }

    public int readUByte() {
        return this.delegate.readUByte();
    }
}
