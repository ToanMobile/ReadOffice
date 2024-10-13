package com.app.office.fc.hssf.record.common;

import com.app.office.fc.util.LittleEndianOutput;

public interface SharedFeature {
    int getDataSize();

    void serialize(LittleEndianOutput littleEndianOutput);

    String toString();
}
