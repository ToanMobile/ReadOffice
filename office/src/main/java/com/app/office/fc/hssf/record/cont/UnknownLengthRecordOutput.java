package com.app.office.fc.hssf.record.cont;

import com.app.office.fc.util.DelayableLittleEndianOutput;
import com.app.office.fc.util.LittleEndianByteArrayOutputStream;
import com.app.office.fc.util.LittleEndianOutput;

final class UnknownLengthRecordOutput implements LittleEndianOutput {
    private static final int MAX_DATA_SIZE = 8224;
    private final byte[] _byteBuffer;
    private final LittleEndianOutput _dataSizeOutput;
    private final LittleEndianOutput _originalOut;
    private LittleEndianOutput _out;
    private int _size;

    public UnknownLengthRecordOutput(LittleEndianOutput littleEndianOutput, int i) {
        this._originalOut = littleEndianOutput;
        littleEndianOutput.writeShort(i);
        if (littleEndianOutput instanceof DelayableLittleEndianOutput) {
            this._dataSizeOutput = ((DelayableLittleEndianOutput) littleEndianOutput).createDelayedOutput(2);
            this._byteBuffer = null;
            this._out = littleEndianOutput;
            return;
        }
        this._dataSizeOutput = littleEndianOutput;
        byte[] bArr = new byte[MAX_DATA_SIZE];
        this._byteBuffer = bArr;
        this._out = new LittleEndianByteArrayOutputStream(bArr, 0);
    }

    public int getTotalSize() {
        return this._size + 4;
    }

    public int getAvailableSpace() {
        if (this._out != null) {
            return 8224 - this._size;
        }
        throw new IllegalStateException("Record already terminated");
    }

    public void terminate() {
        if (this._out != null) {
            this._dataSizeOutput.writeShort(this._size);
            byte[] bArr = this._byteBuffer;
            if (bArr != null) {
                this._originalOut.write(bArr, 0, this._size);
                this._out = null;
                return;
            }
            this._out = null;
            return;
        }
        throw new IllegalStateException("Record already terminated");
    }

    public void write(byte[] bArr) {
        this._out.write(bArr);
        this._size += bArr.length;
    }

    public void write(byte[] bArr, int i, int i2) {
        this._out.write(bArr, i, i2);
        this._size += i2;
    }

    public void writeByte(int i) {
        this._out.writeByte(i);
        this._size++;
    }

    public void writeDouble(double d) {
        this._out.writeDouble(d);
        this._size += 8;
    }

    public void writeInt(int i) {
        this._out.writeInt(i);
        this._size += 4;
    }

    public void writeLong(long j) {
        this._out.writeLong(j);
        this._size += 8;
    }

    public void writeShort(int i) {
        this._out.writeShort(i);
        this._size += 2;
    }
}
