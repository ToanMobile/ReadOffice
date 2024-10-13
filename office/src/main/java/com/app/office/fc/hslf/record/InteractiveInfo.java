package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public class InteractiveInfo extends RecordContainer {
    private static long _type = 4082;
    private byte[] _header;
    private InteractiveInfoAtom infoAtom;

    public InteractiveInfoAtom getInteractiveInfoAtom() {
        return this.infoAtom;
    }

    protected InteractiveInfo(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        findInterestingChildren();
    }

    private void findInterestingChildren() {
        if (this._children[0] instanceof InteractiveInfoAtom) {
            this.infoAtom = (InteractiveInfoAtom) this._children[0];
            return;
        }
        throw new IllegalStateException("First child record wasn't a InteractiveInfoAtom, was of type " + this._children[0].getRecordType());
    }

    public InteractiveInfo() {
        this._header = new byte[8];
        this._children = new Record[1];
        byte[] bArr = this._header;
        bArr[0] = 15;
        LittleEndian.putShort(bArr, 2, (short) ((int) _type));
        this.infoAtom = new InteractiveInfoAtom();
        this._children[0] = this.infoAtom;
    }

    public long getRecordType() {
        return _type;
    }

    public void dispose() {
        this._header = null;
        InteractiveInfoAtom interactiveInfoAtom = this.infoAtom;
        if (interactiveInfoAtom != null) {
            interactiveInfoAtom.dispose();
            this.infoAtom = null;
        }
    }
}
