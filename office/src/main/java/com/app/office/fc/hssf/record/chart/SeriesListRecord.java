package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.LittleEndianOutput;

public final class SeriesListRecord extends StandardRecord {
    public static final short sid = 4118;
    private short[] field_1_seriesNumbers;

    public short getSid() {
        return sid;
    }

    public SeriesListRecord(short[] sArr) {
        this.field_1_seriesNumbers = sArr;
    }

    public SeriesListRecord(RecordInputStream recordInputStream) {
        int readUShort = recordInputStream.readUShort();
        short[] sArr = new short[readUShort];
        for (int i = 0; i < readUShort; i++) {
            sArr[i] = recordInputStream.readShort();
        }
        this.field_1_seriesNumbers = sArr;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SERIESLIST]\n");
        stringBuffer.append("    .seriesNumbers= ");
        stringBuffer.append(" (");
        stringBuffer.append(getSeriesNumbers());
        stringBuffer.append(" )");
        stringBuffer.append("\n");
        stringBuffer.append("[/SERIESLIST]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(r0);
        for (short writeShort : this.field_1_seriesNumbers) {
            littleEndianOutput.writeShort(writeShort);
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this.field_1_seriesNumbers.length * 2) + 2;
    }

    public Object clone() {
        return new SeriesListRecord((short[]) this.field_1_seriesNumbers.clone());
    }

    public short[] getSeriesNumbers() {
        return this.field_1_seriesNumbers;
    }
}
