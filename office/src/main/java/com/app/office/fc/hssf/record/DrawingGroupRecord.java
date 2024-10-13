package com.app.office.fc.hssf.record;

import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.ddf.NullEscherSerializationListener;
import com.app.office.fc.util.ArrayUtil;
import com.app.office.fc.util.LittleEndian;
import java.util.List;

public final class DrawingGroupRecord extends AbstractEscherHolderRecord {
    private static final int MAX_DATA_SIZE = 8224;
    static final int MAX_RECORD_SIZE = 8228;
    public static final short sid = 235;

    /* access modifiers changed from: protected */
    public String getRecordName() {
        return "MSODRAWINGGROUP";
    }

    public short getSid() {
        return sid;
    }

    public DrawingGroupRecord() {
    }

    public DrawingGroupRecord(RecordInputStream recordInputStream) {
        super(recordInputStream);
    }

    public int serialize(int i, byte[] bArr) {
        byte[] rawData = getRawData();
        if (getEscherRecords().size() == 0 && rawData != null) {
            return writeData(i, bArr, rawData);
        }
        byte[] bArr2 = new byte[getRawDataSize()];
        int i2 = 0;
        for (EscherRecord serialize : getEscherRecords()) {
            i2 += serialize.serialize(i2, bArr2, new NullEscherSerializationListener());
        }
        return writeData(i, bArr, bArr2);
    }

    public void processChildRecords() {
        convertRawBytesToEscherRecords();
    }

    public int getRecordSize() {
        return grossSizeFromDataSize(getRawDataSize());
    }

    private int getRawDataSize() {
        List<EscherRecord> escherRecords = getEscherRecords();
        byte[] rawData = getRawData();
        if (escherRecords.size() == 0 && rawData != null) {
            return rawData.length;
        }
        int i = 0;
        for (EscherRecord recordSize : escherRecords) {
            i += recordSize.getRecordSize();
        }
        return i;
    }

    static int grossSizeFromDataSize(int i) {
        return i + ((((i - 1) / MAX_DATA_SIZE) + 1) * 4);
    }

    private int writeData(int i, byte[] bArr, byte[] bArr2) {
        int i2 = 0;
        int i3 = 0;
        while (i2 < bArr2.length) {
            int min = Math.min(bArr2.length - i2, MAX_DATA_SIZE);
            if (i2 / MAX_DATA_SIZE >= 2) {
                writeContinueHeader(bArr, i, min);
            } else {
                writeHeader(bArr, i, min);
            }
            int i4 = i + 4;
            ArrayUtil.arraycopy(bArr2, i2, bArr, i4, min);
            i = i4 + min;
            i2 += min;
            i3 = i3 + 4 + min;
        }
        return i3;
    }

    private void writeHeader(byte[] bArr, int i, int i2) {
        LittleEndian.putShort(bArr, i + 0, getSid());
        LittleEndian.putShort(bArr, i + 2, (short) i2);
    }

    private void writeContinueHeader(byte[] bArr, int i, int i2) {
        LittleEndian.putShort(bArr, i + 0, 60);
        LittleEndian.putShort(bArr, i + 2, (short) i2);
    }
}
