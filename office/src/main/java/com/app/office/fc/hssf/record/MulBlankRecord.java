package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class MulBlankRecord extends StandardRecord {
    public static final short sid = 190;
    private final int _firstCol;
    private final int _lastCol;
    private final int _row;
    private final short[] _xfs;

    public Object clone() {
        return this;
    }

    public short getSid() {
        return 190;
    }

    public MulBlankRecord(int i, int i2, short[] sArr) {
        this._row = i;
        this._firstCol = i2;
        this._xfs = sArr;
        this._lastCol = (i2 + sArr.length) - 1;
    }

    public int getRow() {
        return this._row;
    }

    public int getFirstColumn() {
        return this._firstCol;
    }

    public int getLastColumn() {
        return this._lastCol;
    }

    public int getNumColumns() {
        return (this._lastCol - this._firstCol) + 1;
    }

    public short getXFAt(int i) {
        return this._xfs[i];
    }

    public MulBlankRecord(RecordInputStream recordInputStream) {
        this._row = recordInputStream.readUShort();
        this._firstCol = recordInputStream.readShort();
        this._xfs = parseXFs(recordInputStream);
        this._lastCol = recordInputStream.readShort();
    }

    private static short[] parseXFs(RecordInputStream recordInputStream) {
        int remaining = (recordInputStream.remaining() - 2) / 2;
        short[] sArr = new short[remaining];
        for (int i = 0; i < remaining; i++) {
            sArr[i] = recordInputStream.readShort();
        }
        return sArr;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[MULBLANK]\n");
        stringBuffer.append("row  = ");
        stringBuffer.append(Integer.toHexString(getRow()));
        stringBuffer.append("\n");
        stringBuffer.append("firstcol  = ");
        stringBuffer.append(Integer.toHexString(getFirstColumn()));
        stringBuffer.append("\n");
        stringBuffer.append(" lastcol  = ");
        stringBuffer.append(Integer.toHexString(this._lastCol));
        stringBuffer.append("\n");
        for (int i = 0; i < getNumColumns(); i++) {
            stringBuffer.append("xf");
            stringBuffer.append(i);
            stringBuffer.append("\t\t= ");
            stringBuffer.append(Integer.toHexString(getXFAt(i)));
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/MULBLANK]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._row);
        littleEndianOutput.writeShort(this._firstCol);
        for (short writeShort : this._xfs) {
            littleEndianOutput.writeShort(writeShort);
        }
        littleEndianOutput.writeShort(this._lastCol);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this._xfs.length * 2) + 6;
    }
}
