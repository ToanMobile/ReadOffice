package com.app.office.fc.hslf.record;

import java.util.Hashtable;

public abstract class PositionDependentRecordAtom extends RecordAtom implements PositionDependentRecord {
    protected int myLastOnDiskOffset;

    public abstract void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable);

    public int getLastOnDiskOffset() {
        return this.myLastOnDiskOffset;
    }

    public void setLastOnDiskOffset(int i) {
        this.myLastOnDiskOffset = i;
    }
}
