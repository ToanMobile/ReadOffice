package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;

public class ExObjList extends RecordContainer {
    private static long _type = 1033;
    private byte[] _header;
    private ExObjListAtom exObjListAtom;

    public ExObjListAtom getExObjListAtom() {
        return this.exObjListAtom;
    }

    public ExHyperlink[] getExHyperlinks() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this._children.length; i++) {
            if (this._children[i] instanceof ExHyperlink) {
                arrayList.add((ExHyperlink) this._children[i]);
            }
        }
        return (ExHyperlink[]) arrayList.toArray(new ExHyperlink[arrayList.size()]);
    }

    protected ExObjList(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        findInterestingChildren();
    }

    private void findInterestingChildren() {
        if (this._children[0] instanceof ExObjListAtom) {
            this.exObjListAtom = (ExObjListAtom) this._children[0];
            return;
        }
        throw new IllegalStateException("First child record wasn't a ExObjListAtom, was of type " + this._children[0].getRecordType());
    }

    public ExObjList() {
        this._header = new byte[8];
        this._children = new Record[1];
        byte[] bArr = this._header;
        bArr[0] = 15;
        LittleEndian.putShort(bArr, 2, (short) ((int) _type));
        this._children[0] = new ExObjListAtom();
        findInterestingChildren();
    }

    public long getRecordType() {
        return _type;
    }

    public ExHyperlink get(int i) {
        for (int i2 = 0; i2 < this._children.length; i2++) {
            if (this._children[i2] instanceof ExHyperlink) {
                ExHyperlink exHyperlink = (ExHyperlink) this._children[i2];
                if (exHyperlink.getExHyperlinkAtom().getNumber() == i) {
                    return exHyperlink;
                }
            }
        }
        return null;
    }

    public void dispose() {
        super.dispose();
        this._header = null;
        ExObjListAtom exObjListAtom2 = this.exObjListAtom;
        if (exObjListAtom2 != null) {
            exObjListAtom2.dispose();
            this.exObjListAtom = null;
        }
    }
}
