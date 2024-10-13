package com.app.office.fc.hssf.eventusermodel;

import com.app.office.fc.hssf.record.Record;

public abstract class AbortableHSSFListener implements HSSFListener {
    public abstract short abortableProcessRecord(Record record) throws HSSFUserException;

    public void processRecord(Record record) {
    }
}
