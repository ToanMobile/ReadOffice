package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class RecalcIdRecord extends StandardRecord {
    public static final short sid = 449;
    private int _engineId;
    private final int _reserved0;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 8;
    }

    public short getSid() {
        return 449;
    }

    public boolean isNeeded() {
        return true;
    }

    public RecalcIdRecord() {
        this._reserved0 = 0;
        this._engineId = 0;
    }

    public RecalcIdRecord(RecordInputStream recordInputStream) {
        recordInputStream.readUShort();
        this._reserved0 = recordInputStream.readUShort();
        this._engineId = recordInputStream.readInt();
    }

    public void setEngineId(int i) {
        this._engineId = i;
    }

    public int getEngineId() {
        return this._engineId;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[RECALCID]\n");
        stringBuffer.append("    .reserved = ");
        stringBuffer.append(HexDump.shortToHex(this._reserved0));
        stringBuffer.append("\n");
        stringBuffer.append("    .engineId = ");
        stringBuffer.append(HexDump.intToHex(this._engineId));
        stringBuffer.append("\n");
        stringBuffer.append("[/RECALCID]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(449);
        littleEndianOutput.writeShort(this._reserved0);
        littleEndianOutput.writeInt(this._engineId);
    }
}
