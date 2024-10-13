package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class DrawingRecord extends StandardRecord {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final short sid = 236;
    private byte[] contd;
    private byte[] recordData;

    public short getSid() {
        return 236;
    }

    public DrawingRecord() {
        this.recordData = EMPTY_BYTE_ARRAY;
    }

    public DrawingRecord(RecordInputStream recordInputStream) {
        this.recordData = recordInputStream.readRemainder();
    }

    public void processContinueRecord(byte[] bArr) {
        byte[] bArr2 = this.contd;
        if (bArr2 == null) {
            this.contd = bArr;
            return;
        }
        byte[] bArr3 = new byte[(bArr2.length + bArr.length)];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, 0, bArr3, this.contd.length, bArr.length);
        this.contd = bArr3;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.write(this.recordData);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return this.recordData.length;
    }

    public int getDataLength() {
        byte[] bArr = this.contd;
        if (bArr != null) {
            return this.recordData.length + bArr.length;
        }
        return this.recordData.length;
    }

    public byte[] getData() {
        byte[] bArr = this.contd;
        if (bArr == null) {
            return this.recordData;
        }
        byte[] bArr2 = this.recordData;
        byte[] bArr3 = new byte[(bArr2.length + bArr.length)];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        byte[] bArr4 = this.contd;
        System.arraycopy(bArr4, 0, bArr3, this.recordData.length, bArr4.length);
        return bArr3;
    }

    public void setData(byte[] bArr) {
        if (bArr != null) {
            this.recordData = bArr;
            return;
        }
        throw new IllegalArgumentException("data must not be null");
    }

    public Object clone() {
        DrawingRecord drawingRecord = new DrawingRecord();
        drawingRecord.recordData = (byte[]) this.recordData.clone();
        byte[] bArr = this.contd;
        if (bArr != null) {
            drawingRecord.contd = (byte[]) bArr.clone();
        }
        return drawingRecord;
    }
}
