package com.app.office.fc.hslf.record;

public final class Environment extends PositionDependentRecordContainer {
    private static long _type = 1010;
    private byte[] _header;
    private FontCollection fontCollection;
    private TxMasterStyleAtom txmaster;

    public FontCollection getFontCollection() {
        return this.fontCollection;
    }

    protected Environment(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        for (int i3 = 0; i3 < this._children.length; i3++) {
            if (this._children[i3] instanceof FontCollection) {
                this.fontCollection = (FontCollection) this._children[i3];
            } else if (this._children[i3] instanceof TxMasterStyleAtom) {
                this.txmaster = (TxMasterStyleAtom) this._children[i3];
            }
        }
        if (this.fontCollection == null) {
            throw new IllegalStateException("Environment didn't contain a FontCollection record!");
        }
    }

    public TxMasterStyleAtom getTxMasterStyleAtom() {
        return this.txmaster;
    }

    public long getRecordType() {
        return _type;
    }

    public void dispose() {
        this._header = null;
        FontCollection fontCollection2 = this.fontCollection;
        if (fontCollection2 != null) {
            fontCollection2.dispose();
            this.fontCollection = null;
        }
        TxMasterStyleAtom txMasterStyleAtom = this.txmaster;
        if (txMasterStyleAtom != null) {
            txMasterStyleAtom.dispose();
            this.txmaster = null;
        }
    }
}
