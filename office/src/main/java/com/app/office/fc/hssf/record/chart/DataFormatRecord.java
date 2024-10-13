package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class DataFormatRecord extends StandardRecord {
    public static final short sid = 4102;
    private static final BitField useExcel4Colors = BitFieldFactory.getInstance(1);
    private short field_1_pointNumber;
    private short field_2_seriesIndex;
    private short field_3_seriesNumber;
    private short field_4_formatFlags;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 8;
    }

    public short getSid() {
        return 4102;
    }

    public DataFormatRecord() {
    }

    public DataFormatRecord(RecordInputStream recordInputStream) {
        this.field_1_pointNumber = recordInputStream.readShort();
        this.field_2_seriesIndex = recordInputStream.readShort();
        this.field_3_seriesNumber = recordInputStream.readShort();
        this.field_4_formatFlags = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[DATAFORMAT]\n");
        stringBuffer.append("    .pointNumber          = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getPointNumber()));
        stringBuffer.append(" (");
        stringBuffer.append(getPointNumber());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .seriesIndex          = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getSeriesIndex()));
        stringBuffer.append(" (");
        stringBuffer.append(getSeriesIndex());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .seriesNumber         = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getSeriesNumber()));
        stringBuffer.append(" (");
        stringBuffer.append(getSeriesNumber());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .formatFlags          = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getFormatFlags()));
        stringBuffer.append(" (");
        stringBuffer.append(getFormatFlags());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .useExcel4Colors          = ");
        stringBuffer.append(isUseExcel4Colors());
        stringBuffer.append(10);
        stringBuffer.append("[/DATAFORMAT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_pointNumber);
        littleEndianOutput.writeShort(this.field_2_seriesIndex);
        littleEndianOutput.writeShort(this.field_3_seriesNumber);
        littleEndianOutput.writeShort(this.field_4_formatFlags);
    }

    public Object clone() {
        DataFormatRecord dataFormatRecord = new DataFormatRecord();
        dataFormatRecord.field_1_pointNumber = this.field_1_pointNumber;
        dataFormatRecord.field_2_seriesIndex = this.field_2_seriesIndex;
        dataFormatRecord.field_3_seriesNumber = this.field_3_seriesNumber;
        dataFormatRecord.field_4_formatFlags = this.field_4_formatFlags;
        return dataFormatRecord;
    }

    public short getPointNumber() {
        return this.field_1_pointNumber;
    }

    public void setPointNumber(short s) {
        this.field_1_pointNumber = s;
    }

    public short getSeriesIndex() {
        return this.field_2_seriesIndex;
    }

    public void setSeriesIndex(short s) {
        this.field_2_seriesIndex = s;
    }

    public short getSeriesNumber() {
        return this.field_3_seriesNumber;
    }

    public void setSeriesNumber(short s) {
        this.field_3_seriesNumber = s;
    }

    public short getFormatFlags() {
        return this.field_4_formatFlags;
    }

    public void setFormatFlags(short s) {
        this.field_4_formatFlags = s;
    }

    public void setUseExcel4Colors(boolean z) {
        this.field_4_formatFlags = useExcel4Colors.setShortBoolean(this.field_4_formatFlags, z);
    }

    public boolean isUseExcel4Colors() {
        return useExcel4Colors.isSet(this.field_4_formatFlags);
    }
}
