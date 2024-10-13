package com.app.office.fc.ddf;

import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;
import java.util.List;

public final class UnknownEscherRecord extends EscherRecord {
    private static final byte[] NO_BYTES = new byte[0];
    private List<EscherRecord> _childRecords = new ArrayList();
    private byte[] thedata = NO_BYTES;

    public int fillFields(byte[] bArr, int i, EscherRecordFactory escherRecordFactory) {
        int readHeader = readHeader(bArr, i);
        int i2 = 8;
        int i3 = i + 8;
        int length = bArr.length - i3;
        if (readHeader > length) {
            readHeader = length;
        }
        if (isContainerRecord()) {
            this.thedata = new byte[0];
            while (readHeader > 0) {
                EscherRecord createRecord = escherRecordFactory.createRecord(bArr, i3);
                int fillFields = createRecord.fillFields(bArr, i3, escherRecordFactory);
                i2 += fillFields;
                i3 += fillFields;
                readHeader -= fillFields;
                getChildRecords().add(createRecord);
            }
            return i2;
        }
        byte[] bArr2 = new byte[readHeader];
        this.thedata = bArr2;
        System.arraycopy(bArr, i3, bArr2, 0, readHeader);
        return readHeader + 8;
    }

    public int serialize(int i, byte[] bArr, EscherSerializationListener escherSerializationListener) {
        escherSerializationListener.beforeRecordSerialize(i, getRecordId(), this);
        LittleEndian.putShort(bArr, i, getOptions());
        LittleEndian.putShort(bArr, i + 2, getRecordId());
        int length = this.thedata.length;
        for (EscherRecord recordSize : this._childRecords) {
            length += recordSize.getRecordSize();
        }
        LittleEndian.putInt(bArr, i + 4, length);
        byte[] bArr2 = this.thedata;
        int i2 = i + 8;
        System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
        int length2 = i2 + this.thedata.length;
        for (EscherRecord serialize : this._childRecords) {
            length2 += serialize.serialize(length2, bArr, escherSerializationListener);
        }
        int i3 = length2 - i;
        escherSerializationListener.afterRecordSerialize(length2, getRecordId(), i3, this);
        return i3;
    }

    public byte[] getData() {
        return this.thedata;
    }

    public int getRecordSize() {
        return this.thedata.length + 8;
    }

    public List<EscherRecord> getChildRecords() {
        return this._childRecords;
    }

    public void setChildRecords(List<EscherRecord> list) {
        this._childRecords = list;
    }

    public Object clone() {
        return super.clone();
    }

    public String getRecordName() {
        return "Unknown 0x" + HexDump.toHex(getRecordId());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (getChildRecords().size() > 0) {
            stringBuffer.append("  children: \n");
            for (EscherRecord obj : this._childRecords) {
                stringBuffer.append(obj.toString());
                stringBuffer.append(10);
            }
        }
        String hex = HexDump.toHex(this.thedata, 32);
        return getClass().getName() + ":" + 10 + "  isContainer: " + isContainerRecord() + 10 + "  options: 0x" + HexDump.toHex(getOptions()) + 10 + "  recordId: 0x" + HexDump.toHex(getRecordId()) + 10 + "  numchildren: " + getChildRecords().size() + 10 + hex + stringBuffer.toString();
    }

    public void addChildRecord(EscherRecord escherRecord) {
        getChildRecords().add(escherRecord);
    }

    public void dispose() {
        List<EscherRecord> list = this._childRecords;
        if (list != null) {
            for (EscherRecord dispose : list) {
                dispose.dispose();
            }
            this._childRecords.clear();
            this._childRecords = null;
        }
    }
}
