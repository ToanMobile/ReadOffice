package com.app.office.fc.hwpf.model.io;

import com.app.office.fc.util.Internal;
import java.io.ByteArrayOutputStream;

@Internal
public final class HWPFOutputStream extends ByteArrayOutputStream {
    int _offset;

    public int getOffset() {
        return this._offset;
    }

    public synchronized void reset() {
        super.reset();
        this._offset = 0;
    }

    public synchronized void write(byte[] bArr, int i, int i2) {
        super.write(bArr, i, i2);
        this._offset += i2;
    }

    public synchronized void write(int i) {
        super.write(i);
        this._offset++;
    }
}
