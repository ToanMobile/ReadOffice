package com.app.office.fc.hslf.record;

public interface ParentAwareRecord {
    RecordContainer getParentRecord();

    void setParentRecord(RecordContainer recordContainer);
}
