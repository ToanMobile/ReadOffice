package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.poifs.property.DocumentProperty;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;

public final class NDocumentInputStream extends DocumentInputStream {
    private ByteBuffer _buffer;
    private boolean _closed;
    private int _current_block_count;
    private int _current_offset;
    private Iterator<ByteBuffer> _data;
    private NPOIFSDocument _document;
    private int _document_size;
    private int _marked_offset;
    private int _marked_offset_count;

    public NDocumentInputStream(DocumentEntry documentEntry) throws IOException {
        if (documentEntry instanceof DocumentNode) {
            this._current_offset = 0;
            this._current_block_count = 0;
            this._marked_offset = 0;
            this._marked_offset_count = 0;
            this._document_size = documentEntry.getSize();
            this._closed = false;
            DocumentNode documentNode = (DocumentNode) documentEntry;
            NPOIFSDocument nPOIFSDocument = new NPOIFSDocument((DocumentProperty) documentNode.getProperty(), ((DirectoryNode) documentNode.getParent()).getNFileSystem());
            this._document = nPOIFSDocument;
            this._data = nPOIFSDocument.getBlockIterator();
            return;
        }
        throw new IOException("Cannot open internal document storage, " + documentEntry + " not a Document Node");
    }

    public NDocumentInputStream(NPOIFSDocument nPOIFSDocument) {
        this._current_offset = 0;
        this._current_block_count = 0;
        this._marked_offset = 0;
        this._marked_offset_count = 0;
        this._document_size = nPOIFSDocument.getSize();
        this._closed = false;
        this._document = nPOIFSDocument;
        this._data = nPOIFSDocument.getBlockIterator();
    }

    public int available() {
        if (!this._closed) {
            return this._document_size - this._current_offset;
        }
        throw new IllegalStateException("cannot perform requested operation on a closed stream");
    }

    public void close() {
        this._closed = true;
    }

    public void mark(int i) {
        this._marked_offset = this._current_offset;
        this._marked_offset_count = Math.max(0, this._current_block_count - 1);
    }

    public int read() throws IOException {
        dieIfClosed();
        if (atEOD()) {
            return -1;
        }
        byte[] bArr = new byte[1];
        int read = read(bArr, 0, 1);
        if (read < 0) {
            return read;
        }
        if (bArr[0] < 0) {
            return bArr[0] + 256;
        }
        return bArr[0];
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        dieIfClosed();
        if (bArr == null) {
            throw new IllegalArgumentException("buffer must not be null");
        } else if (i < 0 || i2 < 0 || bArr.length < i + i2) {
            throw new IndexOutOfBoundsException("can't read past buffer boundaries");
        } else if (i2 == 0) {
            return 0;
        } else {
            if (atEOD()) {
                return -1;
            }
            int min = Math.min(available(), i2);
            readFully(bArr, i, min);
            return min;
        }
    }

    public void reset() {
        int i;
        int i2;
        int i3 = this._marked_offset;
        if (i3 == 0 && (i2 = this._marked_offset_count) == 0) {
            this._current_block_count = i2;
            this._current_offset = i3;
            this._data = this._document.getBlockIterator();
            this._buffer = null;
            return;
        }
        this._data = this._document.getBlockIterator();
        int i4 = 0;
        this._current_offset = 0;
        while (true) {
            i = this._marked_offset_count;
            if (i4 >= i) {
                break;
            }
            ByteBuffer next = this._data.next();
            this._buffer = next;
            this._current_offset += next.remaining();
            i4++;
        }
        this._current_block_count = i;
        if (this._current_offset != this._marked_offset) {
            ByteBuffer next2 = this._data.next();
            this._buffer = next2;
            this._current_block_count++;
            next2.position(next2.position() + (this._marked_offset - this._current_offset));
        }
        this._current_offset = this._marked_offset;
    }

    public long skip(long j) throws IOException {
        dieIfClosed();
        if (j < 0) {
            return 0;
        }
        int i = this._current_offset;
        int i2 = ((int) j) + i;
        if (i2 < i) {
            i2 = this._document_size;
        } else {
            int i3 = this._document_size;
            if (i2 > i3) {
                i2 = i3;
            }
        }
        long j2 = (long) (i2 - i);
        readFully(new byte[((int) j2)]);
        return j2;
    }

    private void dieIfClosed() throws IOException {
        if (this._closed) {
            throw new IOException("cannot perform requested operation on a closed stream");
        }
    }

    private boolean atEOD() {
        return this._current_offset == this._document_size;
    }

    private void checkAvaliable(int i) {
        if (this._closed) {
            throw new IllegalStateException("cannot perform requested operation on a closed stream");
        } else if (i > this._document_size - this._current_offset) {
            throw new RuntimeException("Buffer underrun - requested " + i + " bytes but " + (this._document_size - this._current_offset) + " was available");
        }
    }

    public void readFully(byte[] bArr, int i, int i2) {
        checkAvaliable(i2);
        int i3 = 0;
        while (i3 < i2) {
            ByteBuffer byteBuffer = this._buffer;
            if (byteBuffer == null || byteBuffer.remaining() == 0) {
                this._current_block_count++;
                this._buffer = this._data.next();
            }
            int min = Math.min(i2 - i3, this._buffer.remaining());
            this._buffer.get(bArr, i + i3, min);
            this._current_offset += min;
            i3 += min;
        }
    }

    public byte readByte() {
        return (byte) readUByte();
    }

    public double readDouble() {
        return Double.longBitsToDouble(readLong());
    }

    public long readLong() {
        checkAvaliable(8);
        byte[] bArr = new byte[8];
        readFully(bArr, 0, 8);
        return LittleEndian.getLong(bArr, 0);
    }

    public short readShort() {
        checkAvaliable(2);
        byte[] bArr = new byte[2];
        readFully(bArr, 0, 2);
        return LittleEndian.getShort(bArr);
    }

    public int readInt() {
        checkAvaliable(4);
        byte[] bArr = new byte[4];
        readFully(bArr, 0, 4);
        return LittleEndian.getInt(bArr);
    }

    public int readUShort() {
        checkAvaliable(2);
        byte[] bArr = new byte[2];
        readFully(bArr, 0, 2);
        return LittleEndian.getUShort(bArr);
    }

    public int readUByte() {
        checkAvaliable(1);
        byte[] bArr = new byte[1];
        readFully(bArr, 0, 1);
        if (bArr[0] >= 0) {
            return bArr[0];
        }
        return bArr[0] + 256;
    }
}
