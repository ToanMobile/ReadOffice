package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogger;

public final class ExVideoContainer extends RecordContainer {
    private byte[] _header;
    private ExMediaAtom mediaAtom;
    private CString pathAtom;

    protected ExVideoContainer(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        findInterestingChildren();
    }

    private void findInterestingChildren() {
        if (this._children[0] instanceof ExMediaAtom) {
            this.mediaAtom = (ExMediaAtom) this._children[0];
        } else {
            POILogger pOILogger = this.logger;
            int i = POILogger.ERROR;
            pOILogger.log(i, (Object) "First child record wasn't a ExMediaAtom, was of type " + this._children[0].getRecordType());
        }
        if (this._children[1] instanceof CString) {
            this.pathAtom = (CString) this._children[1];
            return;
        }
        POILogger pOILogger2 = this.logger;
        int i2 = POILogger.ERROR;
        pOILogger2.log(i2, (Object) "Second child record wasn't a CString, was of type " + this._children[1].getRecordType());
    }

    public ExVideoContainer() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        bArr[0] = 15;
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        this._children = new Record[2];
        Record[] recordArr = this._children;
        ExMediaAtom exMediaAtom = new ExMediaAtom();
        this.mediaAtom = exMediaAtom;
        recordArr[0] = exMediaAtom;
        Record[] recordArr2 = this._children;
        CString cString = new CString();
        this.pathAtom = cString;
        recordArr2[1] = cString;
    }

    public long getRecordType() {
        return (long) RecordTypes.ExVideoContainer.typeID;
    }

    public ExMediaAtom getExMediaAtom() {
        return this.mediaAtom;
    }

    public CString getPathAtom() {
        return this.pathAtom;
    }

    public void dispose() {
        super.dispose();
        this._header = null;
        CString cString = this.pathAtom;
        if (cString != null) {
            cString.dispose();
            this.pathAtom = null;
        }
        ExMediaAtom exMediaAtom = this.mediaAtom;
        if (exMediaAtom != null) {
            exMediaAtom.dispose();
            this.mediaAtom = null;
        }
    }
}
