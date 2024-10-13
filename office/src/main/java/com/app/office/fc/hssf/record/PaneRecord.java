package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class PaneRecord extends StandardRecord {
    public static final short ACTIVE_PANE_LOWER_LEFT = 2;
    public static final short ACTIVE_PANE_LOWER_RIGHT = 0;
    public static final short ACTIVE_PANE_UPER_LEFT = 3;
    public static final short ACTIVE_PANE_UPPER_LEFT = 3;
    public static final short ACTIVE_PANE_UPPER_RIGHT = 1;
    public static final short sid = 65;
    private short field_1_x;
    private short field_2_y;
    private short field_3_topRow;
    private short field_4_leftColumn;
    private short field_5_activePane;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 10;
    }

    public short getSid() {
        return 65;
    }

    public PaneRecord() {
    }

    public PaneRecord(RecordInputStream recordInputStream) {
        this.field_1_x = recordInputStream.readShort();
        this.field_2_y = recordInputStream.readShort();
        this.field_3_topRow = recordInputStream.readShort();
        this.field_4_leftColumn = recordInputStream.readShort();
        this.field_5_activePane = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[PANE]\n");
        stringBuffer.append("    .x                    = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getX()));
        stringBuffer.append(" (");
        stringBuffer.append(getX());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .y                    = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getY()));
        stringBuffer.append(" (");
        stringBuffer.append(getY());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .topRow               = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getTopRow()));
        stringBuffer.append(" (");
        stringBuffer.append(getTopRow());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .leftColumn           = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getLeftColumn()));
        stringBuffer.append(" (");
        stringBuffer.append(getLeftColumn());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .activePane           = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getActivePane()));
        stringBuffer.append(" (");
        stringBuffer.append(getActivePane());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/PANE]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_x);
        littleEndianOutput.writeShort(this.field_2_y);
        littleEndianOutput.writeShort(this.field_3_topRow);
        littleEndianOutput.writeShort(this.field_4_leftColumn);
        littleEndianOutput.writeShort(this.field_5_activePane);
    }

    public Object clone() {
        PaneRecord paneRecord = new PaneRecord();
        paneRecord.field_1_x = this.field_1_x;
        paneRecord.field_2_y = this.field_2_y;
        paneRecord.field_3_topRow = this.field_3_topRow;
        paneRecord.field_4_leftColumn = this.field_4_leftColumn;
        paneRecord.field_5_activePane = this.field_5_activePane;
        return paneRecord;
    }

    public short getX() {
        return this.field_1_x;
    }

    public void setX(short s) {
        this.field_1_x = s;
    }

    public short getY() {
        return this.field_2_y;
    }

    public void setY(short s) {
        this.field_2_y = s;
    }

    public short getTopRow() {
        return this.field_3_topRow;
    }

    public void setTopRow(short s) {
        this.field_3_topRow = s;
    }

    public short getLeftColumn() {
        return this.field_4_leftColumn;
    }

    public void setLeftColumn(short s) {
        this.field_4_leftColumn = s;
    }

    public short getActivePane() {
        return this.field_5_activePane;
    }

    public void setActivePane(short s) {
        this.field_5_activePane = s;
    }
}
