package com.app.office.fc.hslf.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class ExtendedPresRuleContainer extends PositionDependentRecordContainer {
    private static long _type = 4014;
    private ExtendedParaAtomsSet[] _extendedAtomsSets;
    private byte[] _header;

    public void writeOut(OutputStream outputStream) throws IOException {
    }

    protected ExtendedPresRuleContainer(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        Vector vector = new Vector();
        for (int i3 = 0; i3 < this._children.length; i3++) {
            if (this._children[i3] instanceof ExtendedParagraphAtom) {
                int i4 = i3 - 1;
                while (true) {
                    if (i4 < 0) {
                        break;
                    } else if (this._children[i4] instanceof ExtendedParagraphHeaderAtom) {
                        vector.add(new ExtendedParaAtomsSet((ExtendedParagraphHeaderAtom) this._children[i4], (ExtendedParagraphAtom) this._children[i3]));
                        break;
                    } else {
                        i4--;
                    }
                }
            }
        }
        this._extendedAtomsSets = (ExtendedParaAtomsSet[]) vector.toArray(new ExtendedParaAtomsSet[vector.size()]);
    }

    public ExtendedParaAtomsSet[] getExtendedParaAtomsSets() {
        return this._extendedAtomsSets;
    }

    public long getRecordType() {
        return _type;
    }

    public class ExtendedParaAtomsSet {
        private ExtendedParagraphAtom _extendedParaAtom;
        private ExtendedParagraphHeaderAtom _extendedParaHeaderAtom;

        public ExtendedParaAtomsSet(ExtendedParagraphHeaderAtom extendedParagraphHeaderAtom, ExtendedParagraphAtom extendedParagraphAtom) {
            this._extendedParaHeaderAtom = extendedParagraphHeaderAtom;
            this._extendedParaAtom = extendedParagraphAtom;
        }

        public ExtendedParagraphHeaderAtom getExtendedParaHeaderAtom() {
            return this._extendedParaHeaderAtom;
        }

        public ExtendedParagraphAtom getExtendedParaAtom() {
            return this._extendedParaAtom;
        }

        public void dispose() {
            ExtendedParagraphHeaderAtom extendedParagraphHeaderAtom = this._extendedParaHeaderAtom;
            if (extendedParagraphHeaderAtom != null) {
                extendedParagraphHeaderAtom.dispose();
                this._extendedParaHeaderAtom = null;
            }
            ExtendedParagraphAtom extendedParagraphAtom = this._extendedParaAtom;
            if (extendedParagraphAtom != null) {
                extendedParagraphAtom.dispose();
                this._extendedParaAtom = null;
            }
        }
    }

    public void dispose() {
        this._header = null;
        ExtendedParaAtomsSet[] extendedParaAtomsSetArr = this._extendedAtomsSets;
        if (extendedParaAtomsSetArr != null) {
            for (ExtendedParaAtomsSet dispose : extendedParaAtomsSetArr) {
                dispose.dispose();
            }
            this._extendedAtomsSets = null;
        }
    }
}
