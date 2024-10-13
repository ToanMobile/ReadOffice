package com.app.office.fc.hslf.record;

import java.util.Hashtable;

public interface PositionDependentRecord {
    void dispose();

    int getLastOnDiskOffset();

    void setLastOnDiskOffset(int i);

    void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable);
}
