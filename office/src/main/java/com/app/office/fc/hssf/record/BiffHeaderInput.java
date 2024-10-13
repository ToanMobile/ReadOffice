package com.app.office.fc.hssf.record;

public interface BiffHeaderInput {
    int available();

    int readDataSize();

    int readRecordSID();
}
