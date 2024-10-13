package com.app.office.fc.hslf.record;

import java.util.Hashtable;

public abstract class PositionDependentRecordContainer extends RecordContainer implements PositionDependentRecord {
    protected int myLastOnDiskOffset;
    private int sheetId;

    public void updateOtherRecordReferences(Hashtable<Integer, Integer> hashtable) {
    }

    public int getSheetId() {
        return this.sheetId;
    }

    public void setSheetId(int i) {
        this.sheetId = i;
    }

    public int getLastOnDiskOffset() {
        return this.myLastOnDiskOffset;
    }

    public void setLastOnDiskOffset(int i) {
        this.myLastOnDiskOffset = i;
    }
}
