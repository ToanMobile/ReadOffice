package com.app.office.fc.hssf.record.pivottable;

import com.app.office.fc.hssf.record.RecordFormatException;
import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class PageItemRecord extends StandardRecord {
    public static final short sid = 182;
    private final FieldInfo[] _fieldInfos;

    public short getSid() {
        return sid;
    }

    private static final class FieldInfo {
        public static final int ENCODED_SIZE = 6;
        private int _idObj;
        private int _isxvd;
        private int _isxvi;

        public FieldInfo(RecordInputStream recordInputStream) {
            this._isxvi = recordInputStream.readShort();
            this._isxvd = recordInputStream.readShort();
            this._idObj = recordInputStream.readShort();
        }

        /* access modifiers changed from: protected */
        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeShort(this._isxvi);
            littleEndianOutput.writeShort(this._isxvd);
            littleEndianOutput.writeShort(this._idObj);
        }

        public void appendDebugInfo(StringBuffer stringBuffer) {
            stringBuffer.append('(');
            stringBuffer.append("isxvi=");
            stringBuffer.append(HexDump.shortToHex(this._isxvi));
            stringBuffer.append(" isxvd=");
            stringBuffer.append(HexDump.shortToHex(this._isxvd));
            stringBuffer.append(" idObj=");
            stringBuffer.append(HexDump.shortToHex(this._idObj));
            stringBuffer.append(')');
        }
    }

    public PageItemRecord(RecordInputStream recordInputStream) {
        int remaining = recordInputStream.remaining();
        if (remaining % 6 == 0) {
            int i = remaining / 6;
            FieldInfo[] fieldInfoArr = new FieldInfo[i];
            for (int i2 = 0; i2 < i; i2++) {
                fieldInfoArr[i2] = new FieldInfo(recordInputStream);
            }
            this._fieldInfos = fieldInfoArr;
            return;
        }
        throw new RecordFormatException("Bad data size " + remaining);
    }

    /* access modifiers changed from: protected */
    public void serialize(LittleEndianOutput littleEndianOutput) {
        int i = 0;
        while (true) {
            FieldInfo[] fieldInfoArr = this._fieldInfos;
            if (i < fieldInfoArr.length) {
                fieldInfoArr[i].serialize(littleEndianOutput);
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return this._fieldInfos.length * 6;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SXPI]\n");
        for (int i = 0; i < this._fieldInfos.length; i++) {
            stringBuffer.append("    item[");
            stringBuffer.append(i);
            stringBuffer.append("]=");
            this._fieldInfos[i].appendDebugInfo(stringBuffer);
            stringBuffer.append(10);
        }
        stringBuffer.append("[/SXPI]\n");
        return stringBuffer.toString();
    }
}
