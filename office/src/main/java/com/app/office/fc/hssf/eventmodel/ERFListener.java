package com.app.office.fc.hssf.eventmodel;

import com.app.office.fc.hssf.record.Record;

public interface ERFListener {
    boolean processRecord(Record record);
}
