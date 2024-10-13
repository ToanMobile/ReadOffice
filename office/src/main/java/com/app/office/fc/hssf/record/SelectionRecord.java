package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.util.CellRangeAddress8Bit;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class SelectionRecord extends StandardRecord {
    public static final short sid = 29;
    private byte field_1_pane;
    private int field_2_row_active_cell;
    private int field_3_col_active_cell;
    private int field_4_active_cell_ref_index;
    private CellRangeAddress8Bit[] field_6_refs;

    public short getSid() {
        return 29;
    }

    public SelectionRecord(int i, int i2) {
        this.field_1_pane = 3;
        this.field_2_row_active_cell = i;
        this.field_3_col_active_cell = i2;
        this.field_4_active_cell_ref_index = 0;
        this.field_6_refs = new CellRangeAddress8Bit[]{new CellRangeAddress8Bit(i, i, i2, i2)};
    }

    public SelectionRecord(RecordInputStream recordInputStream) {
        this.field_1_pane = recordInputStream.readByte();
        this.field_2_row_active_cell = recordInputStream.readUShort();
        this.field_3_col_active_cell = recordInputStream.readShort();
        this.field_4_active_cell_ref_index = recordInputStream.readShort();
        this.field_6_refs = new CellRangeAddress8Bit[recordInputStream.readUShort()];
        int i = 0;
        while (true) {
            CellRangeAddress8Bit[] cellRangeAddress8BitArr = this.field_6_refs;
            if (i < cellRangeAddress8BitArr.length) {
                cellRangeAddress8BitArr[i] = new CellRangeAddress8Bit(recordInputStream);
                i++;
            } else {
                return;
            }
        }
    }

    public void setPane(byte b) {
        this.field_1_pane = b;
    }

    public void setActiveCellRow(int i) {
        this.field_2_row_active_cell = i;
    }

    public void setActiveCellCol(short s) {
        this.field_3_col_active_cell = s;
    }

    public void setActiveCellRef(short s) {
        this.field_4_active_cell_ref_index = s;
    }

    public byte getPane() {
        return this.field_1_pane;
    }

    public int getActiveCellRow() {
        return this.field_2_row_active_cell;
    }

    public int getActiveCellCol() {
        return this.field_3_col_active_cell;
    }

    public int getActiveCellRef() {
        return this.field_4_active_cell_ref_index;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SELECTION]\n");
        stringBuffer.append("    .pane            = ");
        stringBuffer.append(HexDump.byteToHex(getPane()));
        stringBuffer.append("\n");
        stringBuffer.append("    .activecellrow   = ");
        stringBuffer.append(HexDump.shortToHex(getActiveCellRow()));
        stringBuffer.append("\n");
        stringBuffer.append("    .activecellcol   = ");
        stringBuffer.append(HexDump.shortToHex(getActiveCellCol()));
        stringBuffer.append("\n");
        stringBuffer.append("    .activecellref   = ");
        stringBuffer.append(HexDump.shortToHex(getActiveCellRef()));
        stringBuffer.append("\n");
        stringBuffer.append("    .numrefs         = ");
        stringBuffer.append(HexDump.shortToHex(this.field_6_refs.length));
        stringBuffer.append("\n");
        stringBuffer.append("[/SELECTION]\n");
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return CellRangeAddress8Bit.getEncodedSize(this.field_6_refs.length) + 9;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPane());
        littleEndianOutput.writeShort(getActiveCellRow());
        littleEndianOutput.writeShort(getActiveCellCol());
        littleEndianOutput.writeShort(getActiveCellRef());
        littleEndianOutput.writeShort(this.field_6_refs.length);
        int i = 0;
        while (true) {
            CellRangeAddress8Bit[] cellRangeAddress8BitArr = this.field_6_refs;
            if (i < cellRangeAddress8BitArr.length) {
                cellRangeAddress8BitArr[i].serialize(littleEndianOutput);
                i++;
            } else {
                return;
            }
        }
    }

    public Object clone() {
        SelectionRecord selectionRecord = new SelectionRecord(this.field_2_row_active_cell, this.field_3_col_active_cell);
        selectionRecord.field_1_pane = this.field_1_pane;
        selectionRecord.field_4_active_cell_ref_index = this.field_4_active_cell_ref_index;
        selectionRecord.field_6_refs = this.field_6_refs;
        return selectionRecord;
    }
}
