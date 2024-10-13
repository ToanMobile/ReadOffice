package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.record.cont.ContinuableRecord;
import com.app.office.fc.hssf.record.cont.ContinuableRecordOutput;
import com.app.office.fc.util.StringUtil;

public final class StringRecord extends ContinuableRecord {
    public static final short sid = 519;
    private boolean _is16bitUnicode;
    private String _text;

    public short getSid() {
        return 519;
    }

    public StringRecord() {
    }

    public StringRecord(RecordInputStream recordInputStream) {
        int readUShort = recordInputStream.readUShort();
        boolean z = recordInputStream.readByte() != 0;
        this._is16bitUnicode = z;
        if (z) {
            this._text = recordInputStream.readUnicodeLEString(readUShort);
        } else {
            this._text = recordInputStream.readCompressedUnicode(readUShort);
        }
    }

    /* access modifiers changed from: protected */
    public void serialize(ContinuableRecordOutput continuableRecordOutput) {
        continuableRecordOutput.writeShort(this._text.length());
        continuableRecordOutput.writeStringData(this._text);
    }

    public String getString() {
        return this._text;
    }

    public void setString(String str) {
        this._text = str;
        this._is16bitUnicode = StringUtil.hasMultibyte(str);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[STRING]\n");
        stringBuffer.append("    .string            = ");
        stringBuffer.append(this._text);
        stringBuffer.append("\n");
        stringBuffer.append("[/STRING]\n");
        return stringBuffer.toString();
    }

    public Object clone() {
        StringRecord stringRecord = new StringRecord();
        stringRecord._is16bitUnicode = this._is16bitUnicode;
        stringRecord._text = this._text;
        return stringRecord;
    }
}
