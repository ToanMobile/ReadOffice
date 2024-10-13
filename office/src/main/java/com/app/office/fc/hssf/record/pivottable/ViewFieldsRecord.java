package com.app.office.fc.hssf.record.pivottable;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class ViewFieldsRecord extends StandardRecord {
    private static final int BASE_SIZE = 10;
    private static final int STRING_NOT_PRESENT_LEN = 65535;
    public static final short sid = 177;
    private int _cItm;
    private int _cSub;
    private int _grbitSub;
    private String _name;
    private int _sxaxis;

    public short getSid() {
        return sid;
    }

    private static final class Axis {
        public static final int COLUMN = 2;
        public static final int DATA = 8;
        public static final int NO_AXIS = 0;
        public static final int PAGE = 4;
        public static final int ROW = 1;

        private Axis() {
        }
    }

    public ViewFieldsRecord(RecordInputStream recordInputStream) {
        this._sxaxis = recordInputStream.readShort();
        this._cSub = recordInputStream.readShort();
        this._grbitSub = recordInputStream.readShort();
        this._cItm = recordInputStream.readShort();
        int readUShort = recordInputStream.readUShort();
        if (readUShort == 65535) {
            return;
        }
        if ((recordInputStream.readByte() & 1) != 0) {
            this._name = recordInputStream.readUnicodeLEString(readUShort);
        } else {
            this._name = recordInputStream.readCompressedUnicode(readUShort);
        }
    }

    /* access modifiers changed from: protected */
    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._sxaxis);
        littleEndianOutput.writeShort(this._cSub);
        littleEndianOutput.writeShort(this._grbitSub);
        littleEndianOutput.writeShort(this._cItm);
        String str = this._name;
        if (str != null) {
            StringUtil.writeUnicodeString(littleEndianOutput, str);
        } else {
            littleEndianOutput.writeShort(65535);
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        String str = this._name;
        if (str == null) {
            return 10;
        }
        return (str.length() * (StringUtil.hasMultibyte(this._name) ? 2 : 1)) + 11;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SXVD]\n");
        stringBuffer.append("    .sxaxis    = ");
        stringBuffer.append(HexDump.shortToHex(this._sxaxis));
        stringBuffer.append(10);
        stringBuffer.append("    .cSub      = ");
        stringBuffer.append(HexDump.shortToHex(this._cSub));
        stringBuffer.append(10);
        stringBuffer.append("    .grbitSub  = ");
        stringBuffer.append(HexDump.shortToHex(this._grbitSub));
        stringBuffer.append(10);
        stringBuffer.append("    .cItm      = ");
        stringBuffer.append(HexDump.shortToHex(this._cItm));
        stringBuffer.append(10);
        stringBuffer.append("    .name      = ");
        stringBuffer.append(this._name);
        stringBuffer.append(10);
        stringBuffer.append("[/SXVD]\n");
        return stringBuffer.toString();
    }
}
