package com.app.office.fc.hslf.record;

public final class Sound extends RecordContainer {
    private SoundData _data;
    private byte[] _header;
    private CString _name;
    private CString _type;

    protected Sound(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        findInterestingChildren();
    }

    private void findInterestingChildren() {
        if (this._children[0] instanceof CString) {
            this._name = (CString) this._children[0];
        }
        if (this._children[1] instanceof CString) {
            this._type = (CString) this._children[1];
        }
        for (int i = 2; i < this._children.length; i++) {
            if (this._children[i] instanceof SoundData) {
                this._data = (SoundData) this._children[i];
                return;
            }
        }
    }

    public long getRecordType() {
        return (long) RecordTypes.Sound.typeID;
    }

    public String getSoundName() {
        return this._name.getText();
    }

    public String getSoundType() {
        return this._type.getText();
    }

    public byte[] getSoundData() {
        SoundData soundData = this._data;
        if (soundData == null) {
            return null;
        }
        return soundData.getData();
    }

    public void dispose() {
        super.dispose();
        this._header = null;
        CString cString = this._name;
        if (cString != null) {
            cString.dispose();
            this._name = null;
        }
        CString cString2 = this._type;
        if (cString2 != null) {
            cString2.dispose();
            this._type = null;
        }
        SoundData soundData = this._data;
        if (soundData != null) {
            soundData.dispose();
            this._data = null;
        }
    }
}
