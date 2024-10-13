package com.app.office.fc.hssf.record;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class ColumnInfoRecord extends StandardRecord {
    private static final BitField collapsed = BitFieldFactory.getInstance(4096);
    private static final BitField hidden = BitFieldFactory.getInstance(1);
    private static final BitField outlevel = BitFieldFactory.getInstance(1792);
    public static final short sid = 125;
    private int _colWidth;
    private int _firstCol;
    private int _lastCol;
    private int _options;
    private int _xfIndex;
    private int colPixelWidth = 74;
    private int field_6_reserved;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 12;
    }

    public short getSid() {
        return 125;
    }

    public ColumnInfoRecord() {
        setColumnWidth(2275);
        this._options = 2;
        this._xfIndex = 15;
        this.field_6_reserved = 2;
    }

    public ColumnInfoRecord(RecordInputStream recordInputStream) {
        this._firstCol = recordInputStream.readUShort();
        this._lastCol = recordInputStream.readUShort();
        this._colWidth = recordInputStream.readUShort();
        this._xfIndex = recordInputStream.readUShort();
        this._options = recordInputStream.readUShort();
        int remaining = recordInputStream.remaining();
        if (remaining == 0) {
            this.field_6_reserved = 0;
        } else if (remaining == 1) {
            this.field_6_reserved = recordInputStream.readByte();
        } else if (remaining == 2) {
            this.field_6_reserved = recordInputStream.readUShort();
        } else {
            throw new RuntimeException("Unusual record size remaining=(" + recordInputStream.remaining() + ")");
        }
    }

    public void setFirstColumn(int i) {
        this._firstCol = i;
    }

    public void setLastColumn(int i) {
        this._lastCol = i;
    }

    public void setColumnWidth(int i) {
        this._colWidth = i;
    }

    public void setXFIndex(int i) {
        this._xfIndex = i;
    }

    public void setHidden(boolean z) {
        this._options = hidden.setBoolean(this._options, z);
    }

    public void setOutlineLevel(int i) {
        this._options = outlevel.setValue(this._options, i);
    }

    public void setCollapsed(boolean z) {
        this._options = collapsed.setBoolean(this._options, z);
    }

    public int getFirstColumn() {
        return this._firstCol;
    }

    public int getLastColumn() {
        return this._lastCol;
    }

    public int getColumnWidth() {
        return this._colWidth;
    }

    public int getXFIndex() {
        return this._xfIndex;
    }

    public boolean getHidden() {
        return hidden.isSet(this._options);
    }

    public int getOutlineLevel() {
        return outlevel.getValue(this._options);
    }

    public boolean getCollapsed() {
        return collapsed.isSet(this._options);
    }

    public boolean containsColumn(int i) {
        return this._firstCol <= i && i <= this._lastCol;
    }

    public boolean isAdjacentBefore(ColumnInfoRecord columnInfoRecord) {
        return this._lastCol == columnInfoRecord._firstCol - 1;
    }

    public boolean formatMatches(ColumnInfoRecord columnInfoRecord) {
        if (this._xfIndex == columnInfoRecord._xfIndex && this._options == columnInfoRecord._options && this._colWidth == columnInfoRecord._colWidth) {
            return true;
        }
        return false;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getFirstColumn());
        littleEndianOutput.writeShort(getLastColumn());
        littleEndianOutput.writeShort(getColumnWidth());
        littleEndianOutput.writeShort(getXFIndex());
        littleEndianOutput.writeShort(this._options);
        littleEndianOutput.writeShort(this.field_6_reserved);
    }

    public int getColPixelWidth() {
        return this.colPixelWidth;
    }

    public void setColPixelWidth(int i) {
        this.colPixelWidth = i;
    }

    public String toString() {
        return "[COLINFO]\n" + "  colfirst = " + getFirstColumn() + "\n" + "  collast  = " + getLastColumn() + "\n" + "  colwidth = " + getColumnWidth() + "\n" + "  xfindex  = " + getXFIndex() + "\n" + "  options  = " + HexDump.shortToHex(this._options) + "\n" + "    hidden   = " + getHidden() + "\n" + "    olevel   = " + getOutlineLevel() + "\n" + "    collapsed= " + getCollapsed() + "\n" + "[/COLINFO]\n";
    }

    public Object clone() {
        ColumnInfoRecord columnInfoRecord = new ColumnInfoRecord();
        columnInfoRecord._firstCol = this._firstCol;
        columnInfoRecord._lastCol = this._lastCol;
        columnInfoRecord._colWidth = this._colWidth;
        columnInfoRecord._xfIndex = this._xfIndex;
        columnInfoRecord._options = this._options;
        columnInfoRecord.field_6_reserved = this.field_6_reserved;
        return columnInfoRecord;
    }
}
