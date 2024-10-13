package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class SheetPropertiesRecord extends StandardRecord {
    public static final byte EMPTY_INTERPOLATED = 2;
    public static final byte EMPTY_NOT_PLOTTED = 0;
    public static final byte EMPTY_ZERO = 1;
    private static final BitField autoPlotArea = BitFieldFactory.getInstance(16);
    private static final BitField chartTypeManuallyFormatted = BitFieldFactory.getInstance(1);
    private static final BitField defaultPlotDimensions = BitFieldFactory.getInstance(8);
    private static final BitField doNotSizeWithWindow = BitFieldFactory.getInstance(4);
    private static final BitField plotVisibleOnly = BitFieldFactory.getInstance(2);
    public static final short sid = 4164;
    private int field_1_flags;
    private int field_2_empty;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 4;
    }

    public short getSid() {
        return sid;
    }

    public SheetPropertiesRecord() {
    }

    public SheetPropertiesRecord(RecordInputStream recordInputStream) {
        this.field_1_flags = recordInputStream.readUShort();
        this.field_2_empty = recordInputStream.readUShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SHTPROPS]\n");
        stringBuffer.append("    .flags                = ");
        stringBuffer.append(HexDump.shortToHex(this.field_1_flags));
        stringBuffer.append(10);
        stringBuffer.append("         .chartTypeManuallyFormatted= ");
        stringBuffer.append(isChartTypeManuallyFormatted());
        stringBuffer.append(10);
        stringBuffer.append("         .plotVisibleOnly           = ");
        stringBuffer.append(isPlotVisibleOnly());
        stringBuffer.append(10);
        stringBuffer.append("         .doNotSizeWithWindow       = ");
        stringBuffer.append(isDoNotSizeWithWindow());
        stringBuffer.append(10);
        stringBuffer.append("         .defaultPlotDimensions     = ");
        stringBuffer.append(isDefaultPlotDimensions());
        stringBuffer.append(10);
        stringBuffer.append("         .autoPlotArea              = ");
        stringBuffer.append(isAutoPlotArea());
        stringBuffer.append(10);
        stringBuffer.append("    .empty                = ");
        stringBuffer.append(HexDump.shortToHex(this.field_2_empty));
        stringBuffer.append(10);
        stringBuffer.append("[/SHTPROPS]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_flags);
        littleEndianOutput.writeShort(this.field_2_empty);
    }

    public Object clone() {
        SheetPropertiesRecord sheetPropertiesRecord = new SheetPropertiesRecord();
        sheetPropertiesRecord.field_1_flags = this.field_1_flags;
        sheetPropertiesRecord.field_2_empty = this.field_2_empty;
        return sheetPropertiesRecord;
    }

    public int getFlags() {
        return this.field_1_flags;
    }

    public int getEmpty() {
        return this.field_2_empty;
    }

    public void setEmpty(byte b) {
        this.field_2_empty = b;
    }

    public void setChartTypeManuallyFormatted(boolean z) {
        this.field_1_flags = chartTypeManuallyFormatted.setBoolean(this.field_1_flags, z);
    }

    public boolean isChartTypeManuallyFormatted() {
        return chartTypeManuallyFormatted.isSet(this.field_1_flags);
    }

    public void setPlotVisibleOnly(boolean z) {
        this.field_1_flags = plotVisibleOnly.setBoolean(this.field_1_flags, z);
    }

    public boolean isPlotVisibleOnly() {
        return plotVisibleOnly.isSet(this.field_1_flags);
    }

    public void setDoNotSizeWithWindow(boolean z) {
        this.field_1_flags = doNotSizeWithWindow.setBoolean(this.field_1_flags, z);
    }

    public boolean isDoNotSizeWithWindow() {
        return doNotSizeWithWindow.isSet(this.field_1_flags);
    }

    public void setDefaultPlotDimensions(boolean z) {
        this.field_1_flags = defaultPlotDimensions.setBoolean(this.field_1_flags, z);
    }

    public boolean isDefaultPlotDimensions() {
        return defaultPlotDimensions.isSet(this.field_1_flags);
    }

    public void setAutoPlotArea(boolean z) {
        this.field_1_flags = autoPlotArea.setBoolean(this.field_1_flags, z);
    }

    public boolean isAutoPlotArea() {
        return autoPlotArea.isSet(this.field_1_flags);
    }
}
