package com.app.office.fc.hssf.record.crypto;

import com.app.office.fc.hssf.record.BiffHeaderInput;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianInputStream;
import java.io.InputStream;

public final class Biff8DecryptingStream implements BiffHeaderInput, LittleEndianInput {
    private final LittleEndianInput _le;
    private final Biff8RC4 _rc4;

    public Biff8DecryptingStream(InputStream inputStream, int i, Biff8EncryptionKey biff8EncryptionKey) {
        this._rc4 = new Biff8RC4(i, biff8EncryptionKey);
        if (inputStream instanceof LittleEndianInput) {
            this._le = (LittleEndianInput) inputStream;
        } else {
            this._le = new LittleEndianInputStream(inputStream);
        }
    }

    public int available() {
        return this._le.available();
    }

    public int readRecordSID() {
        int readUShort = this._le.readUShort();
        this._rc4.skipTwoBytes();
        this._rc4.startRecord(readUShort);
        return readUShort;
    }

    public int readDataSize() {
        int readUShort = this._le.readUShort();
        this._rc4.skipTwoBytes();
        return readUShort;
    }

    public double readDouble() {
        double longBitsToDouble = Double.longBitsToDouble(readLong());
        if (!Double.isNaN(longBitsToDouble)) {
            return longBitsToDouble;
        }
        throw new RuntimeException("Did not expect to read NaN");
    }

    public void readFully(byte[] bArr) {
        readFully(bArr, 0, bArr.length);
    }

    public void readFully(byte[] bArr, int i, int i2) {
        this._le.readFully(bArr, i, i2);
        this._rc4.xor(bArr, i, i2);
    }

    public int readUByte() {
        return this._rc4.xorByte(this._le.readUByte());
    }

    public byte readByte() {
        return (byte) this._rc4.xorByte(this._le.readUByte());
    }

    public int readUShort() {
        return this._rc4.xorShort(this._le.readUShort());
    }

    public short readShort() {
        return (short) this._rc4.xorShort(this._le.readUShort());
    }

    public int readInt() {
        return this._rc4.xorInt(this._le.readInt());
    }

    public long readLong() {
        return this._rc4.xorLong(this._le.readLong());
    }
}
