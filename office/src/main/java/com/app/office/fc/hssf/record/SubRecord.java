package com.app.office.fc.hssf.record;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.LittleEndianOutputStream;
import java.io.ByteArrayOutputStream;

public abstract class SubRecord {
    public abstract Object clone();

    /* access modifiers changed from: protected */
    public abstract int getDataSize();

    public boolean isTerminating() {
        return false;
    }

    public abstract void serialize(LittleEndianOutput littleEndianOutput);

    protected SubRecord() {
    }

    public static SubRecord createSubRecord(LittleEndianInput littleEndianInput, int i) {
        int readUShort = littleEndianInput.readUShort();
        int readUShort2 = littleEndianInput.readUShort();
        if (readUShort == 0) {
            return new EndSubRecord(littleEndianInput, readUShort2);
        }
        if (readUShort == 6) {
            return new GroupMarkerSubRecord(littleEndianInput, readUShort2);
        }
        if (readUShort == 9) {
            return new EmbeddedObjectRefSubRecord(littleEndianInput, readUShort2);
        }
        if (readUShort == 19) {
            return new LbsDataSubRecord(littleEndianInput, readUShort2, i);
        }
        if (readUShort == 21) {
            return new CommonObjectDataSubRecord(littleEndianInput, readUShort2);
        }
        if (readUShort != 12) {
            return readUShort != 13 ? new UnknownSubRecord(littleEndianInput, readUShort, readUShort2) : new NoteStructureSubRecord(littleEndianInput, readUShort2);
        }
        return new FtCblsSubRecord(littleEndianInput, readUShort2);
    }

    public byte[] serialize() {
        int dataSize = getDataSize() + 4;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(dataSize);
        serialize(new LittleEndianOutputStream(byteArrayOutputStream));
        if (byteArrayOutputStream.size() == dataSize) {
            return byteArrayOutputStream.toByteArray();
        }
        throw new RuntimeException("write size mismatch");
    }

    private static final class UnknownSubRecord extends SubRecord {
        private final byte[] _data;
        private final int _sid;

        public Object clone() {
            return this;
        }

        public UnknownSubRecord(LittleEndianInput littleEndianInput, int i, int i2) {
            this._sid = i;
            byte[] bArr = new byte[i2];
            littleEndianInput.readFully(bArr);
            this._data = bArr;
        }

        /* access modifiers changed from: protected */
        public int getDataSize() {
            return this._data.length;
        }

        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.writeShort(this._sid);
            littleEndianOutput.writeShort(this._data.length);
            littleEndianOutput.write(this._data);
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append(" [");
            stringBuffer.append("sid=");
            stringBuffer.append(HexDump.shortToHex(this._sid));
            stringBuffer.append(" size=");
            stringBuffer.append(this._data.length);
            stringBuffer.append(" : ");
            stringBuffer.append(HexDump.toHex(this._data));
            stringBuffer.append("]\n");
            return stringBuffer.toString();
        }
    }
}
