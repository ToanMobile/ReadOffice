package com.app.office.fc.util;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.InputStream;

public class ShortField implements FixedField {
    private final int _offset;
    private short _value;

    public ShortField(int i) throws ArrayIndexOutOfBoundsException {
        if (i >= 0) {
            this._offset = i;
            return;
        }
        throw new ArrayIndexOutOfBoundsException("Illegal offset: " + i);
    }

    public ShortField(int i, short s) throws ArrayIndexOutOfBoundsException {
        this(i);
        set(s);
    }

    public ShortField(int i, byte[] bArr) throws ArrayIndexOutOfBoundsException {
        this(i);
        readFromBytes(bArr);
    }

    public ShortField(int i, short s, byte[] bArr) throws ArrayIndexOutOfBoundsException {
        this(i);
        set(s, bArr);
    }

    public short get() {
        return this._value;
    }

    public void set(short s) {
        this._value = s;
    }

    public void set(short s, byte[] bArr) throws ArrayIndexOutOfBoundsException {
        this._value = s;
        writeToBytes(bArr);
    }

    public void readFromBytes(byte[] bArr) throws ArrayIndexOutOfBoundsException {
        this._value = LittleEndian.getShort(bArr, this._offset);
    }

    public void readFromStream(InputStream inputStream) throws IOException, LittleEndian.BufferUnderrunException {
        this._value = LittleEndian.readShort(inputStream);
    }

    public void writeToBytes(byte[] bArr) throws ArrayIndexOutOfBoundsException {
        LittleEndian.putShort(bArr, this._offset, this._value);
    }

    public String toString() {
        return String.valueOf(this._value);
    }
}
