package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public abstract class CellRecord extends StandardRecord implements CellValueRecordInterface {
    private int _columnIndex;
    private int _formatIndex;
    private int _rowIndex;

    /* access modifiers changed from: protected */
    public abstract void appendValueText(StringBuilder sb);

    /* access modifiers changed from: protected */
    public abstract String getRecordName();

    /* access modifiers changed from: protected */
    public abstract int getValueDataSize();

    /* access modifiers changed from: protected */
    public abstract void serializeValue(LittleEndianOutput littleEndianOutput);

    protected CellRecord() {
    }

    protected CellRecord(RecordInputStream recordInputStream) {
        this._rowIndex = recordInputStream.readUShort();
        this._columnIndex = recordInputStream.readUShort();
        this._formatIndex = recordInputStream.readUShort();
    }

    public final void setRow(int i) {
        this._rowIndex = i;
    }

    public final void setColumn(short s) {
        this._columnIndex = s;
    }

    public final void setXFIndex(short s) {
        this._formatIndex = s;
    }

    public final int getRow() {
        return this._rowIndex;
    }

    public final short getColumn() {
        return (short) this._columnIndex;
    }

    public final short getXFIndex() {
        return (short) this._formatIndex;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        String recordName = getRecordName();
        sb.append("[");
        sb.append(recordName);
        sb.append("]\n");
        sb.append("    .row    = ");
        sb.append(HexDump.shortToHex(getRow()));
        sb.append("\n");
        sb.append("    .col    = ");
        sb.append(HexDump.shortToHex(getColumn()));
        sb.append("\n");
        sb.append("    .xfindex= ");
        sb.append(HexDump.shortToHex(getXFIndex()));
        sb.append("\n");
        appendValueText(sb);
        sb.append("\n");
        sb.append("[/");
        sb.append(recordName);
        sb.append("]\n");
        return sb.toString();
    }

    public final void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getRow());
        littleEndianOutput.writeShort(getColumn());
        littleEndianOutput.writeShort(getXFIndex());
        serializeValue(littleEndianOutput);
    }

    /* access modifiers changed from: protected */
    public final int getDataSize() {
        return getValueDataSize() + 6;
    }

    /* access modifiers changed from: protected */
    public final void copyBaseFields(CellRecord cellRecord) {
        cellRecord._rowIndex = this._rowIndex;
        cellRecord._columnIndex = this._columnIndex;
        cellRecord._formatIndex = this._formatIndex;
    }
}
