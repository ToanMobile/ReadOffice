package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class DrawingSelectionRecord extends StandardRecord {
    public static final short sid = 237;
    private int _cpsp;
    private int _dgslk;
    private OfficeArtRecordHeader _header;
    private int[] _shapeIds;
    private int _spidFocus;

    public Object clone() {
        return this;
    }

    public short getSid() {
        return sid;
    }

    private static final class OfficeArtRecordHeader {
        public static final int ENCODED_SIZE = 8;
        private final int _length;
        private final int _type;
        private final int _verAndInstance;

        public OfficeArtRecordHeader(LittleEndianInput littleEndianInput) {
            this._verAndInstance = littleEndianInput.readUShort();
            this._type = littleEndianInput.readUShort();
            this._length = littleEndianInput.readInt();
        }

        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeShort(this._verAndInstance);
            littleEndianOutput.writeShort(this._type);
            littleEndianOutput.writeInt(this._length);
        }

        public String debugFormatAsString() {
            StringBuffer stringBuffer = new StringBuffer(32);
            stringBuffer.append("ver+inst=");
            stringBuffer.append(HexDump.shortToHex(this._verAndInstance));
            stringBuffer.append(" type=");
            stringBuffer.append(HexDump.shortToHex(this._type));
            stringBuffer.append(" len=");
            stringBuffer.append(HexDump.intToHex(this._length));
            return stringBuffer.toString();
        }
    }

    public DrawingSelectionRecord(RecordInputStream recordInputStream) {
        this._header = new OfficeArtRecordHeader(recordInputStream);
        this._cpsp = recordInputStream.readInt();
        this._dgslk = recordInputStream.readInt();
        this._spidFocus = recordInputStream.readInt();
        int available = recordInputStream.available() / 4;
        int[] iArr = new int[available];
        for (int i = 0; i < available; i++) {
            iArr[i] = recordInputStream.readInt();
        }
        this._shapeIds = iArr;
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this._shapeIds.length * 4) + 20;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        this._header.serialize(littleEndianOutput);
        littleEndianOutput.writeInt(this._cpsp);
        littleEndianOutput.writeInt(this._dgslk);
        littleEndianOutput.writeInt(this._spidFocus);
        int i = 0;
        while (true) {
            int[] iArr = this._shapeIds;
            if (i < iArr.length) {
                littleEndianOutput.writeInt(iArr[i]);
                i++;
            } else {
                return;
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[MSODRAWINGSELECTION]\n");
        stringBuffer.append("    .rh       =(");
        stringBuffer.append(this._header.debugFormatAsString());
        stringBuffer.append(")\n");
        stringBuffer.append("    .cpsp     =");
        stringBuffer.append(HexDump.intToHex(this._cpsp));
        stringBuffer.append(10);
        stringBuffer.append("    .dgslk    =");
        stringBuffer.append(HexDump.intToHex(this._dgslk));
        stringBuffer.append(10);
        stringBuffer.append("    .spidFocus=");
        stringBuffer.append(HexDump.intToHex(this._spidFocus));
        stringBuffer.append(10);
        stringBuffer.append("    .shapeIds =(");
        for (int i = 0; i < this._shapeIds.length; i++) {
            if (i > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(HexDump.intToHex(this._shapeIds[i]));
        }
        stringBuffer.append(")\n");
        stringBuffer.append("[/MSODRAWINGSELECTION]\n");
        return stringBuffer.toString();
    }
}
