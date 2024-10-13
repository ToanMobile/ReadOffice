package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class SeriesChartGroupIndexRecord extends StandardRecord {
    public static final short sid = 4165;
    private short field_1_chartGroupIndex;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 4165;
    }

    public SeriesChartGroupIndexRecord() {
    }

    public SeriesChartGroupIndexRecord(RecordInputStream recordInputStream) {
        this.field_1_chartGroupIndex = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SERTOCRT]\n");
        stringBuffer.append("    .chartGroupIndex      = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getChartGroupIndex()));
        stringBuffer.append(" (");
        stringBuffer.append(getChartGroupIndex());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/SERTOCRT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_chartGroupIndex);
    }

    public Object clone() {
        SeriesChartGroupIndexRecord seriesChartGroupIndexRecord = new SeriesChartGroupIndexRecord();
        seriesChartGroupIndexRecord.field_1_chartGroupIndex = this.field_1_chartGroupIndex;
        return seriesChartGroupIndexRecord;
    }

    public short getChartGroupIndex() {
        return this.field_1_chartGroupIndex;
    }

    public void setChartGroupIndex(short s) {
        this.field_1_chartGroupIndex = s;
    }
}
