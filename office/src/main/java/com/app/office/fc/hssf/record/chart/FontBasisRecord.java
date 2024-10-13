package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class FontBasisRecord extends StandardRecord {
    public static final short sid = 4192;
    private short field_1_xBasis;
    private short field_2_yBasis;
    private short field_3_heightBasis;
    private short field_4_scale;
    private short field_5_indexToFontTable;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 10;
    }

    public short getSid() {
        return sid;
    }

    public FontBasisRecord() {
    }

    public FontBasisRecord(RecordInputStream recordInputStream) {
        this.field_1_xBasis = recordInputStream.readShort();
        this.field_2_yBasis = recordInputStream.readShort();
        this.field_3_heightBasis = recordInputStream.readShort();
        this.field_4_scale = recordInputStream.readShort();
        this.field_5_indexToFontTable = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FBI]\n");
        stringBuffer.append("    .xBasis               = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getXBasis()));
        stringBuffer.append(" (");
        stringBuffer.append(getXBasis());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .yBasis               = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getYBasis()));
        stringBuffer.append(" (");
        stringBuffer.append(getYBasis());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .heightBasis          = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getHeightBasis()));
        stringBuffer.append(" (");
        stringBuffer.append(getHeightBasis());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .scale                = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getScale()));
        stringBuffer.append(" (");
        stringBuffer.append(getScale());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .indexToFontTable     = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getIndexToFontTable()));
        stringBuffer.append(" (");
        stringBuffer.append(getIndexToFontTable());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/FBI]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_xBasis);
        littleEndianOutput.writeShort(this.field_2_yBasis);
        littleEndianOutput.writeShort(this.field_3_heightBasis);
        littleEndianOutput.writeShort(this.field_4_scale);
        littleEndianOutput.writeShort(this.field_5_indexToFontTable);
    }

    public Object clone() {
        FontBasisRecord fontBasisRecord = new FontBasisRecord();
        fontBasisRecord.field_1_xBasis = this.field_1_xBasis;
        fontBasisRecord.field_2_yBasis = this.field_2_yBasis;
        fontBasisRecord.field_3_heightBasis = this.field_3_heightBasis;
        fontBasisRecord.field_4_scale = this.field_4_scale;
        fontBasisRecord.field_5_indexToFontTable = this.field_5_indexToFontTable;
        return fontBasisRecord;
    }

    public short getXBasis() {
        return this.field_1_xBasis;
    }

    public void setXBasis(short s) {
        this.field_1_xBasis = s;
    }

    public short getYBasis() {
        return this.field_2_yBasis;
    }

    public void setYBasis(short s) {
        this.field_2_yBasis = s;
    }

    public short getHeightBasis() {
        return this.field_3_heightBasis;
    }

    public void setHeightBasis(short s) {
        this.field_3_heightBasis = s;
    }

    public short getScale() {
        return this.field_4_scale;
    }

    public void setScale(short s) {
        this.field_4_scale = s;
    }

    public short getIndexToFontTable() {
        return this.field_5_indexToFontTable;
    }

    public void setIndexToFontTable(short s) {
        this.field_5_indexToFontTable = s;
    }
}
