package com.app.office.fc.hslf.record;

import java.util.ArrayList;

public final class MainMaster extends SheetContainer {
    private static long _type = 1016;
    private ColorSchemeAtom _colorScheme;
    private byte[] _header;
    private ColorSchemeAtom[] clrscheme;
    private PPDrawing ppDrawing;
    private SlideAtom slideAtom;
    private TxMasterStyleAtom[] txmasters;

    public SlideAtom getSlideAtom() {
        return this.slideAtom;
    }

    public PPDrawing getPPDrawing() {
        return this.ppDrawing;
    }

    public TxMasterStyleAtom[] getTxMasterStyleAtoms() {
        return this.txmasters;
    }

    public ColorSchemeAtom[] getColorSchemeAtoms() {
        return this.clrscheme;
    }

    protected MainMaster(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < this._children.length; i3++) {
            if (this._children[i3] instanceof SlideAtom) {
                this.slideAtom = (SlideAtom) this._children[i3];
            } else if (this._children[i3] instanceof PPDrawing) {
                this.ppDrawing = (PPDrawing) this._children[i3];
            } else if (this._children[i3] instanceof TxMasterStyleAtom) {
                arrayList.add((TxMasterStyleAtom) this._children[i3]);
            } else if (this._children[i3] instanceof ColorSchemeAtom) {
                arrayList2.add((ColorSchemeAtom) this._children[i3]);
            }
            if (this.ppDrawing != null && (this._children[i3] instanceof ColorSchemeAtom)) {
                this._colorScheme = (ColorSchemeAtom) this._children[i3];
            }
        }
        this.txmasters = (TxMasterStyleAtom[]) arrayList.toArray(new TxMasterStyleAtom[arrayList.size()]);
        this.clrscheme = (ColorSchemeAtom[]) arrayList2.toArray(new ColorSchemeAtom[arrayList2.size()]);
    }

    public long getRecordType() {
        return _type;
    }

    public ColorSchemeAtom getColorScheme() {
        return this._colorScheme;
    }

    public void dispose() {
        super.dispose();
        this._header = null;
        SlideAtom slideAtom2 = this.slideAtom;
        if (slideAtom2 != null) {
            slideAtom2.dispose();
            this.slideAtom = null;
        }
        PPDrawing pPDrawing = this.ppDrawing;
        if (pPDrawing != null) {
            pPDrawing.dispose();
            this.ppDrawing = null;
        }
        TxMasterStyleAtom[] txMasterStyleAtomArr = this.txmasters;
        if (txMasterStyleAtomArr != null) {
            for (TxMasterStyleAtom dispose : txMasterStyleAtomArr) {
                dispose.dispose();
            }
            this.txmasters = null;
        }
        ColorSchemeAtom[] colorSchemeAtomArr = this.clrscheme;
        if (colorSchemeAtomArr != null) {
            for (ColorSchemeAtom dispose2 : colorSchemeAtomArr) {
                dispose2.dispose();
            }
            this.clrscheme = null;
        }
        ColorSchemeAtom colorSchemeAtom = this._colorScheme;
        if (colorSchemeAtom != null) {
            colorSchemeAtom.dispose();
            this._colorScheme = null;
        }
    }
}
