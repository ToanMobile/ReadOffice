package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianByteArrayOutputStream;
import com.app.office.fc.util.LittleEndianOutput;

public abstract class StandardRecord extends Record {
    /* access modifiers changed from: protected */
    public abstract int getDataSize();

    /* access modifiers changed from: protected */
    public abstract void serialize(LittleEndianOutput littleEndianOutput);

    public final int getRecordSize() {
        return getDataSize() + 4;
    }

    public final int serialize(int i, byte[] bArr) {
        int dataSize = getDataSize();
        int i2 = dataSize + 4;
        LittleEndianByteArrayOutputStream littleEndianByteArrayOutputStream = new LittleEndianByteArrayOutputStream(bArr, i, i2);
        littleEndianByteArrayOutputStream.writeShort(getSid());
        littleEndianByteArrayOutputStream.writeShort(dataSize);
        serialize(littleEndianByteArrayOutputStream);
        if (littleEndianByteArrayOutputStream.getWriteIndex() - i == i2) {
            return i2;
        }
        throw new IllegalStateException("Error in serialization of (" + getClass().getName() + "): Incorrect number of bytes written - expected " + i2 + " but got " + (littleEndianByteArrayOutputStream.getWriteIndex() - i));
    }
}
