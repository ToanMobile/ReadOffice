package com.app.office.fc.hssf.record.pivottable;

import com.app.office.fc.hssf.record.RecordFormatException;
import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class ExtendedPivotTableViewFieldsRecord extends StandardRecord {
    private static final int STRING_NOT_PRESENT_LEN = 65535;
    public static final short sid = 256;
    private int _citmShow;
    private int _grbit1;
    private int _grbit2;
    private int _isxdiShow;
    private int _isxdiSort;
    private int _reserved1;
    private int _reserved2;
    private String _subtotalName;

    public short getSid() {
        return 256;
    }

    public ExtendedPivotTableViewFieldsRecord(RecordInputStream recordInputStream) {
        this._grbit1 = recordInputStream.readInt();
        this._grbit2 = recordInputStream.readUByte();
        this._citmShow = recordInputStream.readUByte();
        this._isxdiSort = recordInputStream.readUShort();
        this._isxdiShow = recordInputStream.readUShort();
        int remaining = recordInputStream.remaining();
        if (remaining == 0) {
            this._reserved1 = 0;
            this._reserved2 = 0;
            this._subtotalName = null;
        } else if (remaining == 10) {
            int readUShort = recordInputStream.readUShort();
            this._reserved1 = recordInputStream.readInt();
            this._reserved2 = recordInputStream.readInt();
            if (readUShort != 65535) {
                this._subtotalName = recordInputStream.readUnicodeLEString(readUShort);
            }
        } else {
            throw new RecordFormatException("Unexpected remaining size (" + recordInputStream.remaining() + ")");
        }
    }

    /* access modifiers changed from: protected */
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeInt(this._grbit1);
        littleEndianOutput.writeByte(this._grbit2);
        littleEndianOutput.writeByte(this._citmShow);
        littleEndianOutput.writeShort(this._isxdiSort);
        littleEndianOutput.writeShort(this._isxdiShow);
        String str = this._subtotalName;
        if (str == null) {
            littleEndianOutput.writeShort(65535);
        } else {
            littleEndianOutput.writeShort(str.length());
        }
        littleEndianOutput.writeInt(this._reserved1);
        littleEndianOutput.writeInt(this._reserved2);
        String str2 = this._subtotalName;
        if (str2 != null) {
            StringUtil.putUnicodeLE(str2, littleEndianOutput);
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        String str = this._subtotalName;
        return (str == null ? 0 : str.length() * 2) + 20;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SXVDEX]\n");
        stringBuffer.append("    .grbit1 =");
        stringBuffer.append(HexDump.intToHex(this._grbit1));
        stringBuffer.append("\n");
        stringBuffer.append("    .grbit2 =");
        stringBuffer.append(HexDump.byteToHex(this._grbit2));
        stringBuffer.append("\n");
        stringBuffer.append("    .citmShow =");
        stringBuffer.append(HexDump.byteToHex(this._citmShow));
        stringBuffer.append("\n");
        stringBuffer.append("    .isxdiSort =");
        stringBuffer.append(HexDump.shortToHex(this._isxdiSort));
        stringBuffer.append("\n");
        stringBuffer.append("    .isxdiShow =");
        stringBuffer.append(HexDump.shortToHex(this._isxdiShow));
        stringBuffer.append("\n");
        stringBuffer.append("    .subtotalName =");
        stringBuffer.append(this._subtotalName);
        stringBuffer.append("\n");
        stringBuffer.append("[/SXVDEX]\n");
        return stringBuffer.toString();
    }
}
