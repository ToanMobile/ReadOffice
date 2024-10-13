package com.app.office.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;

public final class List extends PositionDependentRecordContainer {
    private ExtendedPresRuleContainer _extendedPresRuleContainer;
    private byte[] _header;

    public void writeOut(OutputStream outputStream) throws IOException {
    }

    protected List(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        findExtendedPreRuleRecord(this._children);
    }

    private void findExtendedPreRuleRecord(Record[] recordArr) {
        for (int i = 0; i < recordArr.length; i++) {
            if (recordArr[i] instanceof ExtendedPresRuleContainer) {
                this._extendedPresRuleContainer = recordArr[i];
            } else if (!recordArr[i].isAnAtom()) {
                findExtendedPreRuleRecord(recordArr[i].getChildRecords());
            }
        }
    }

    public ExtendedPresRuleContainer getExtendedPresRuleContainer() {
        return this._extendedPresRuleContainer;
    }

    public long getRecordType() {
        return (long) RecordTypes.List.typeID;
    }

    public void dispose() {
        this._header = null;
        ExtendedPresRuleContainer extendedPresRuleContainer = this._extendedPresRuleContainer;
        if (extendedPresRuleContainer != null) {
            extendedPresRuleContainer.dispose();
            this._extendedPresRuleContainer = null;
        }
    }
}
