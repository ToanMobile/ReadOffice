package com.app.office.fc.hssf.record;

import java.io.ByteArrayInputStream;

public abstract class Record extends RecordBase {
    public abstract short getSid();

    protected Record() {
    }

    public final byte[] serialize() {
        byte[] bArr = new byte[getRecordSize()];
        serialize(0, bArr);
        return bArr;
    }

    public String toString() {
        return super.toString();
    }

    public Object clone() {
        throw new RuntimeException("The class " + getClass().getName() + " needs to define a clone method");
    }

    public Record cloneViaReserialise() {
        RecordInputStream recordInputStream = new RecordInputStream(new ByteArrayInputStream(serialize()));
        recordInputStream.nextRecord();
        Record[] createRecord = RecordFactory.createRecord(recordInputStream);
        if (createRecord.length == 1) {
            return createRecord[0];
        }
        throw new IllegalStateException("Re-serialised a record to clone it, but got " + createRecord.length + " records back!");
    }
}
