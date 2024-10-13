package com.app.office.fc.hssf.record;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class RefreshAllRecord extends StandardRecord {
    private static final BitField refreshFlag = BitFieldFactory.getInstance(1);
    public static final short sid = 439;
    private int _options;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    private RefreshAllRecord(int i) {
        this._options = i;
    }

    public RefreshAllRecord(RecordInputStream recordInputStream) {
        this(recordInputStream.readUShort());
    }

    public RefreshAllRecord(boolean z) {
        this(0);
        setRefreshAll(z);
    }

    public void setRefreshAll(boolean z) {
        this._options = refreshFlag.setBoolean(this._options, z);
    }

    public boolean getRefreshAll() {
        return refreshFlag.isSet(this._options);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[REFRESHALL]\n");
        stringBuffer.append("    .options      = ");
        stringBuffer.append(HexDump.shortToHex(this._options));
        stringBuffer.append("\n");
        stringBuffer.append("[/REFRESHALL]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._options);
    }

    public Object clone() {
        return new RefreshAllRecord(this._options);
    }
}
