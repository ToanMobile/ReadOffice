package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class PlotGrowthRecord extends StandardRecord {
    public static final short sid = 4196;
    private int field_1_horizontalScale;
    private int field_2_verticalScale;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 8;
    }

    public short getSid() {
        return sid;
    }

    public PlotGrowthRecord() {
    }

    public PlotGrowthRecord(RecordInputStream recordInputStream) {
        this.field_1_horizontalScale = recordInputStream.readInt();
        this.field_2_verticalScale = recordInputStream.readInt();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[PLOTGROWTH]\n");
        stringBuffer.append("    .horizontalScale      = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getHorizontalScale()));
        stringBuffer.append(" (");
        stringBuffer.append(getHorizontalScale());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .verticalScale        = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getVerticalScale()));
        stringBuffer.append(" (");
        stringBuffer.append(getVerticalScale());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/PLOTGROWTH]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(this.field_1_horizontalScale);
        littleEndianOutput.writeInt(this.field_2_verticalScale);
    }

    public Object clone() {
        PlotGrowthRecord plotGrowthRecord = new PlotGrowthRecord();
        plotGrowthRecord.field_1_horizontalScale = this.field_1_horizontalScale;
        plotGrowthRecord.field_2_verticalScale = this.field_2_verticalScale;
        return plotGrowthRecord;
    }

    public int getHorizontalScale() {
        return this.field_1_horizontalScale;
    }

    public void setHorizontalScale(int i) {
        this.field_1_horizontalScale = i;
    }

    public int getVerticalScale() {
        return this.field_2_verticalScale;
    }

    public void setVerticalScale(int i) {
        this.field_2_verticalScale = i;
    }
}
