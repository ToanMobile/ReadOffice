package com.app.office.fc.hslf.record;

public final class ExControl extends ExEmbed {
    protected ExControl(byte[] bArr, int i, int i2) {
        super(bArr, i, i2);
    }

    public ExControl() {
        Record[] recordArr = this._children;
        ExControlAtom exControlAtom = new ExControlAtom();
        this.embedAtom = exControlAtom;
        recordArr[0] = exControlAtom;
    }

    public ExControlAtom getExControlAtom() {
        return (ExControlAtom) this._children[0];
    }

    public long getRecordType() {
        return (long) RecordTypes.ExControl.typeID;
    }
}
