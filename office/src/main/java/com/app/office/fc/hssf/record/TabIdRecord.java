package com.app.office.fc.hssf.record;

import com.app.office.fc.util.LittleEndianOutput;

public final class TabIdRecord extends StandardRecord {
    private static final short[] EMPTY_SHORT_ARRAY = new short[0];
    public static final short sid = 317;
    public short[] _tabids;

    public short getSid() {
        return 317;
    }

    public TabIdRecord() {
        this._tabids = EMPTY_SHORT_ARRAY;
    }

    public TabIdRecord(RecordInputStream recordInputStream) {
        this._tabids = new short[(recordInputStream.remaining() / 2)];
        int i = 0;
        while (true) {
            short[] sArr = this._tabids;
            if (i < sArr.length) {
                sArr[i] = recordInputStream.readShort();
                i++;
            } else {
                return;
            }
        }
    }

    public void setTabIdArray(short[] sArr) {
        this._tabids = sArr;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[TABID]\n");
        stringBuffer.append("    .elements        = ");
        stringBuffer.append(this._tabids.length);
        stringBuffer.append("\n");
        for (int i = 0; i < this._tabids.length; i++) {
            stringBuffer.append("    .element_");
            stringBuffer.append(i);
            stringBuffer.append(" = ");
            stringBuffer.append(this._tabids[i]);
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/TABID]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        short[] sArr = this._tabids;
        for (short writeShort : sArr) {
            littleEndianOutput.writeShort(writeShort);
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return this._tabids.length * 2;
    }
}
