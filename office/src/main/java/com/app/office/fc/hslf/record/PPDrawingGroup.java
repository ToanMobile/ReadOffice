package com.app.office.fc.hslf.record;

import com.app.office.fc.ddf.DefaultEscherRecordFactory;
import com.app.office.fc.ddf.EscherBSERecord;
import com.app.office.fc.ddf.EscherContainerRecord;
import com.app.office.fc.ddf.EscherDggRecord;
import com.app.office.fc.ddf.EscherRecord;
import com.app.office.fc.util.LittleEndian;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

public final class PPDrawingGroup extends RecordAtom {
    private byte[] _header;
    private EscherDggRecord dgg;
    private EscherContainerRecord dggContainer;

    public Record[] getChildRecords() {
        return null;
    }

    protected PPDrawingGroup(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        byte[] bArr3 = new byte[i2];
        System.arraycopy(bArr, i, bArr3, 0, i2);
        DefaultEscherRecordFactory defaultEscherRecordFactory = new DefaultEscherRecordFactory();
        EscherRecord createRecord = defaultEscherRecordFactory.createRecord(bArr3, 0);
        createRecord.fillFields(bArr3, 0, defaultEscherRecordFactory);
        this.dggContainer = (EscherContainerRecord) createRecord.getChild(0);
    }

    public long getRecordType() {
        return (long) RecordTypes.PPDrawingGroup.typeID;
    }

    public void writeOut(OutputStream outputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Iterator<EscherRecord> childIterator = this.dggContainer.getChildIterator();
        while (childIterator.hasNext()) {
            EscherRecord next = childIterator.next();
            if (next.getRecordId() == -4095) {
                EscherContainerRecord escherContainerRecord = (EscherContainerRecord) next;
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                Iterator<EscherRecord> childIterator2 = escherContainerRecord.getChildIterator();
                while (childIterator2.hasNext()) {
                    byte[] bArr = new byte[44];
                    ((EscherBSERecord) childIterator2.next()).serialize(0, bArr);
                    byteArrayOutputStream2.write(bArr);
                }
                byte[] bArr2 = new byte[8];
                LittleEndian.putShort(bArr2, 0, escherContainerRecord.getOptions());
                LittleEndian.putShort(bArr2, 2, escherContainerRecord.getRecordId());
                LittleEndian.putInt(bArr2, 4, byteArrayOutputStream2.size());
                byteArrayOutputStream.write(bArr2);
                byteArrayOutputStream.write(byteArrayOutputStream2.toByteArray());
            } else {
                byteArrayOutputStream.write(next.serialize());
            }
        }
        int size = byteArrayOutputStream.size();
        LittleEndian.putInt(this._header, 4, size + 8);
        outputStream.write(this._header);
        byte[] bArr3 = new byte[8];
        LittleEndian.putShort(bArr3, 0, this.dggContainer.getOptions());
        LittleEndian.putShort(bArr3, 2, this.dggContainer.getRecordId());
        LittleEndian.putInt(bArr3, 4, size);
        outputStream.write(bArr3);
        outputStream.write(byteArrayOutputStream.toByteArray());
    }

    public EscherContainerRecord getDggContainer() {
        return this.dggContainer;
    }

    public EscherDggRecord getEscherDggRecord() {
        if (this.dgg == null) {
            Iterator<EscherRecord> childIterator = this.dggContainer.getChildIterator();
            while (true) {
                if (!childIterator.hasNext()) {
                    break;
                }
                EscherRecord next = childIterator.next();
                if (next instanceof EscherDggRecord) {
                    this.dgg = (EscherDggRecord) next;
                    break;
                }
            }
        }
        return this.dgg;
    }

    public void dispose() {
        this._header = null;
        EscherContainerRecord escherContainerRecord = this.dggContainer;
        if (escherContainerRecord != null) {
            escherContainerRecord.dispose();
        }
        EscherDggRecord escherDggRecord = this.dgg;
        if (escherDggRecord != null) {
            escherDggRecord.dispose();
            this.dgg = null;
        }
    }
}
