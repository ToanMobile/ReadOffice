package com.app.office.fc.hssf.record.cont;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.util.LittleEndianInput;

public class ContinuableRecordInput implements LittleEndianInput {
    private final RecordInputStream _in;

    public ContinuableRecordInput(RecordInputStream recordInputStream) {
        this._in = recordInputStream;
    }

    public int available() {
        return this._in.available();
    }

    public byte readByte() {
        return this._in.readByte();
    }

    public int readUByte() {
        return this._in.readUByte();
    }

    public short readShort() {
        return this._in.readShort();
    }

    public int readUShort() {
        return (readUByte() << 8) + (readUByte() << 0);
    }

    public int readInt() {
        int readUByte = this._in.readUByte();
        int readUByte2 = this._in.readUByte();
        return (this._in.readUByte() << 24) + (this._in.readUByte() << 16) + (readUByte2 << 8) + (readUByte << 0);
    }

    public long readLong() {
        int readUByte = this._in.readUByte();
        int readUByte2 = this._in.readUByte();
        int readUByte3 = this._in.readUByte();
        int readUByte4 = this._in.readUByte();
        int readUByte5 = this._in.readUByte();
        int readUByte6 = this._in.readUByte();
        return (((long) this._in.readUByte()) << 56) + (((long) this._in.readUByte()) << 48) + (((long) readUByte6) << 40) + (((long) readUByte5) << 32) + (((long) readUByte4) << 24) + ((long) (readUByte3 << 16)) + ((long) (readUByte2 << 8)) + ((long) (readUByte << 0));
    }

    public double readDouble() {
        return this._in.readDouble();
    }

    public void readFully(byte[] bArr) {
        this._in.readFully(bArr);
    }

    public void readFully(byte[] bArr, int i, int i2) {
        this._in.readFully(bArr, i, i2);
    }
}
