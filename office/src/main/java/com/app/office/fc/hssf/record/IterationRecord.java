package com.app.office.fc.hssf.record;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class IterationRecord extends StandardRecord {
    private static final BitField iterationOn = BitFieldFactory.getInstance(1);
    public static final short sid = 17;
    private int _flags;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return 17;
    }

    public IterationRecord(boolean z) {
        this._flags = iterationOn.setBoolean(0, z);
    }

    public IterationRecord(RecordInputStream recordInputStream) {
        this._flags = recordInputStream.readShort();
    }

    public void setIteration(boolean z) {
        this._flags = iterationOn.setBoolean(this._flags, z);
    }

    public boolean getIteration() {
        return iterationOn.isSet(this._flags);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[ITERATION]\n");
        stringBuffer.append("    .flags      = ");
        stringBuffer.append(HexDump.shortToHex(this._flags));
        stringBuffer.append("\n");
        stringBuffer.append("[/ITERATION]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._flags);
    }

    public Object clone() {
        return new IterationRecord(getIteration());
    }
}
