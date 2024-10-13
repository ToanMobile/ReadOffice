package com.app.office.fc.hssf.record.cont;

import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.util.LittleEndianByteArrayOutputStream;

public abstract class ContinuableRecord extends Record {
    /* access modifiers changed from: protected */
    public abstract void serialize(ContinuableRecordOutput continuableRecordOutput);

    protected ContinuableRecord() {
    }

    public final int getRecordSize() {
        ContinuableRecordOutput createForCountingOnly = ContinuableRecordOutput.createForCountingOnly();
        serialize(createForCountingOnly);
        createForCountingOnly.terminate();
        return createForCountingOnly.getTotalSize();
    }

    public final int serialize(int i, byte[] bArr) {
        ContinuableRecordOutput continuableRecordOutput = new ContinuableRecordOutput(new LittleEndianByteArrayOutputStream(bArr, i), getSid());
        serialize(continuableRecordOutput);
        continuableRecordOutput.terminate();
        return continuableRecordOutput.getTotalSize();
    }
}
