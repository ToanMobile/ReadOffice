package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class BarRecord extends StandardRecord {
    private static final BitField displayAsPercentage = BitFieldFactory.getInstance(4);
    private static final BitField horizontal = BitFieldFactory.getInstance(1);
    private static final BitField shadow = BitFieldFactory.getInstance(8);
    public static final short sid = 4119;
    private static final BitField stacked = BitFieldFactory.getInstance(2);
    private short field_1_barSpace;
    private short field_2_categorySpace;
    private short field_3_formatFlags;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 6;
    }

    public short getSid() {
        return sid;
    }

    public BarRecord() {
    }

    public BarRecord(RecordInputStream recordInputStream) {
        this.field_1_barSpace = recordInputStream.readShort();
        this.field_2_categorySpace = recordInputStream.readShort();
        this.field_3_formatFlags = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[BAR]\n");
        stringBuffer.append("    .barSpace             = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getBarSpace()));
        stringBuffer.append(" (");
        stringBuffer.append(getBarSpace());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .categorySpace        = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getCategorySpace()));
        stringBuffer.append(" (");
        stringBuffer.append(getCategorySpace());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .formatFlags          = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getFormatFlags()));
        stringBuffer.append(" (");
        stringBuffer.append(getFormatFlags());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .horizontal               = ");
        stringBuffer.append(isHorizontal());
        stringBuffer.append(10);
        stringBuffer.append("         .stacked                  = ");
        stringBuffer.append(isStacked());
        stringBuffer.append(10);
        stringBuffer.append("         .displayAsPercentage      = ");
        stringBuffer.append(isDisplayAsPercentage());
        stringBuffer.append(10);
        stringBuffer.append("         .shadow                   = ");
        stringBuffer.append(isShadow());
        stringBuffer.append(10);
        stringBuffer.append("[/BAR]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_barSpace);
        littleEndianOutput.writeShort(this.field_2_categorySpace);
        littleEndianOutput.writeShort(this.field_3_formatFlags);
    }

    public Object clone() {
        BarRecord barRecord = new BarRecord();
        barRecord.field_1_barSpace = this.field_1_barSpace;
        barRecord.field_2_categorySpace = this.field_2_categorySpace;
        barRecord.field_3_formatFlags = this.field_3_formatFlags;
        return barRecord;
    }

    public short getBarSpace() {
        return this.field_1_barSpace;
    }

    public void setBarSpace(short s) {
        this.field_1_barSpace = s;
    }

    public short getCategorySpace() {
        return this.field_2_categorySpace;
    }

    public void setCategorySpace(short s) {
        this.field_2_categorySpace = s;
    }

    public short getFormatFlags() {
        return this.field_3_formatFlags;
    }

    public void setFormatFlags(short s) {
        this.field_3_formatFlags = s;
    }

    public void setHorizontal(boolean z) {
        this.field_3_formatFlags = horizontal.setShortBoolean(this.field_3_formatFlags, z);
    }

    public boolean isHorizontal() {
        return horizontal.isSet(this.field_3_formatFlags);
    }

    public void setStacked(boolean z) {
        this.field_3_formatFlags = stacked.setShortBoolean(this.field_3_formatFlags, z);
    }

    public boolean isStacked() {
        return stacked.isSet(this.field_3_formatFlags);
    }

    public void setDisplayAsPercentage(boolean z) {
        this.field_3_formatFlags = displayAsPercentage.setShortBoolean(this.field_3_formatFlags, z);
    }

    public boolean isDisplayAsPercentage() {
        return displayAsPercentage.isSet(this.field_3_formatFlags);
    }

    public void setShadow(boolean z) {
        this.field_3_formatFlags = shadow.setShortBoolean(this.field_3_formatFlags, z);
    }

    public boolean isShadow() {
        return shadow.isSet(this.field_3_formatFlags);
    }
}
