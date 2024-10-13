package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class SeriesLabelsRecord extends StandardRecord {
    private static final BitField labelAsPercentage = BitFieldFactory.getInstance(4);
    private static final BitField showActual = BitFieldFactory.getInstance(1);
    private static final BitField showBubbleSizes = BitFieldFactory.getInstance(32);
    private static final BitField showLabel = BitFieldFactory.getInstance(16);
    private static final BitField showPercent = BitFieldFactory.getInstance(2);
    public static final short sid = 4108;
    private static final BitField smoothedLine = BitFieldFactory.getInstance(8);
    private short field_1_formatFlags;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 4108;
    }

    public SeriesLabelsRecord() {
    }

    public SeriesLabelsRecord(RecordInputStream recordInputStream) {
        this.field_1_formatFlags = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[ATTACHEDLABEL]\n");
        stringBuffer.append("    .formatFlags          = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getFormatFlags()));
        stringBuffer.append(" (");
        stringBuffer.append(getFormatFlags());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .showActual               = ");
        stringBuffer.append(isShowActual());
        stringBuffer.append(10);
        stringBuffer.append("         .showPercent              = ");
        stringBuffer.append(isShowPercent());
        stringBuffer.append(10);
        stringBuffer.append("         .labelAsPercentage        = ");
        stringBuffer.append(isLabelAsPercentage());
        stringBuffer.append(10);
        stringBuffer.append("         .smoothedLine             = ");
        stringBuffer.append(isSmoothedLine());
        stringBuffer.append(10);
        stringBuffer.append("         .showLabel                = ");
        stringBuffer.append(isShowLabel());
        stringBuffer.append(10);
        stringBuffer.append("         .showBubbleSizes          = ");
        stringBuffer.append(isShowBubbleSizes());
        stringBuffer.append(10);
        stringBuffer.append("[/ATTACHEDLABEL]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_formatFlags);
    }

    public Object clone() {
        SeriesLabelsRecord seriesLabelsRecord = new SeriesLabelsRecord();
        seriesLabelsRecord.field_1_formatFlags = this.field_1_formatFlags;
        return seriesLabelsRecord;
    }

    public short getFormatFlags() {
        return this.field_1_formatFlags;
    }

    public void setFormatFlags(short s) {
        this.field_1_formatFlags = s;
    }

    public void setShowActual(boolean z) {
        this.field_1_formatFlags = showActual.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isShowActual() {
        return showActual.isSet(this.field_1_formatFlags);
    }

    public void setShowPercent(boolean z) {
        this.field_1_formatFlags = showPercent.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isShowPercent() {
        return showPercent.isSet(this.field_1_formatFlags);
    }

    public void setLabelAsPercentage(boolean z) {
        this.field_1_formatFlags = labelAsPercentage.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isLabelAsPercentage() {
        return labelAsPercentage.isSet(this.field_1_formatFlags);
    }

    public void setSmoothedLine(boolean z) {
        this.field_1_formatFlags = smoothedLine.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isSmoothedLine() {
        return smoothedLine.isSet(this.field_1_formatFlags);
    }

    public void setShowLabel(boolean z) {
        this.field_1_formatFlags = showLabel.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isShowLabel() {
        return showLabel.isSet(this.field_1_formatFlags);
    }

    public void setShowBubbleSizes(boolean z) {
        this.field_1_formatFlags = showBubbleSizes.setShortBoolean(this.field_1_formatFlags, z);
    }

    public boolean isShowBubbleSizes() {
        return showBubbleSizes.isSet(this.field_1_formatFlags);
    }
}
