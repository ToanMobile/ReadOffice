package com.app.office.fc.hssf.record;

import com.app.office.fc.ss.util.CellRangeAddressList;
import com.app.office.fc.ss.util.HSSFCellRangeAddress;
import com.app.office.fc.util.LittleEndianOutput;

public final class MergeCellsRecord extends StandardRecord {
    public static final short sid = 229;
    private final int _numberOfRegions;
    private HSSFCellRangeAddress[] _regions;
    private final int _startIndex;

    public short getSid() {
        return sid;
    }

    public MergeCellsRecord(HSSFCellRangeAddress[] hSSFCellRangeAddressArr, int i, int i2) {
        this._regions = hSSFCellRangeAddressArr;
        this._startIndex = i;
        this._numberOfRegions = i2;
    }

    public MergeCellsRecord(RecordInputStream recordInputStream) {
        int readUShort = recordInputStream.readUShort();
        HSSFCellRangeAddress[] hSSFCellRangeAddressArr = new HSSFCellRangeAddress[readUShort];
        for (int i = 0; i < readUShort; i++) {
            hSSFCellRangeAddressArr[i] = new HSSFCellRangeAddress(recordInputStream);
        }
        this._numberOfRegions = readUShort;
        this._startIndex = 0;
        this._regions = hSSFCellRangeAddressArr;
    }

    public short getNumAreas() {
        return (short) this._numberOfRegions;
    }

    public HSSFCellRangeAddress getAreaAt(int i) {
        return this._regions[this._startIndex + i];
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return CellRangeAddressList.getEncodedSize(this._numberOfRegions);
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._numberOfRegions);
        for (int i = 0; i < this._numberOfRegions; i++) {
            this._regions[this._startIndex + i].serialize(littleEndianOutput);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[MERGEDCELLS]");
        stringBuffer.append("\n");
        stringBuffer.append("     .numregions =");
        stringBuffer.append(getNumAreas());
        stringBuffer.append("\n");
        for (int i = 0; i < this._numberOfRegions; i++) {
            HSSFCellRangeAddress hSSFCellRangeAddress = this._regions[this._startIndex + i];
            stringBuffer.append("     .rowfrom =");
            stringBuffer.append(hSSFCellRangeAddress.getFirstRow());
            stringBuffer.append("\n");
            stringBuffer.append("     .rowto   =");
            stringBuffer.append(hSSFCellRangeAddress.getLastRow());
            stringBuffer.append("\n");
            stringBuffer.append("     .colfrom =");
            stringBuffer.append(hSSFCellRangeAddress.getFirstColumn());
            stringBuffer.append("\n");
            stringBuffer.append("     .colto   =");
            stringBuffer.append(hSSFCellRangeAddress.getLastColumn());
            stringBuffer.append("\n");
        }
        stringBuffer.append("[MERGEDCELLS]");
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    public Object clone() {
        int i = this._numberOfRegions;
        HSSFCellRangeAddress[] hSSFCellRangeAddressArr = new HSSFCellRangeAddress[i];
        for (int i2 = 0; i2 < i; i2++) {
            hSSFCellRangeAddressArr[i2] = this._regions[this._startIndex + i2].copy();
        }
        return new MergeCellsRecord(hSSFCellRangeAddressArr, 0, i);
    }
}
