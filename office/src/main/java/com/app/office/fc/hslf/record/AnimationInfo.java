package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;

public final class AnimationInfo extends RecordContainer {
    private byte[] _header;
    private AnimationInfoAtom animationAtom;

    protected AnimationInfo(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        findInterestingChildren();
    }

    private void findInterestingChildren() {
        if (this._children[0] instanceof AnimationInfoAtom) {
            this.animationAtom = (AnimationInfoAtom) this._children[0];
        }
    }

    public AnimationInfo() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        bArr[0] = 15;
        LittleEndian.putShort(bArr, 2, (short) ((int) getRecordType()));
        this._children = new Record[1];
        Record[] recordArr = this._children;
        AnimationInfoAtom animationInfoAtom = new AnimationInfoAtom();
        this.animationAtom = animationInfoAtom;
        recordArr[0] = animationInfoAtom;
    }

    public long getRecordType() {
        return (long) RecordTypes.AnimationInfo.typeID;
    }

    public AnimationInfoAtom getAnimationInfoAtom() {
        return this.animationAtom;
    }

    public void dispose() {
        super.dispose();
        this._header = null;
        AnimationInfoAtom animationInfoAtom = this.animationAtom;
        if (animationInfoAtom != null) {
            animationInfoAtom.dispose();
            this.animationAtom = null;
        }
    }
}
