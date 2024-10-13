package com.app.office.fc.hslf.record;

import com.app.office.fc.util.LittleEndian;
import java.util.Vector;

public final class SlideListWithText extends RecordContainer {
    public static final int MASTER = 1;
    public static final int NOTES = 2;
    public static final int SLIDES = 0;
    private static long _type = 4080;
    private byte[] _header;
    private SlideAtomsSet[] slideAtomsSets;

    protected SlideListWithText(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        this._children = Record.findChildRecords(bArr, i + 8, i2 - 8);
        Vector vector = new Vector();
        int i3 = 0;
        while (i3 < this._children.length) {
            if (this._children[i3] instanceof SlidePersistAtom) {
                int i4 = i3 + 1;
                int i5 = i4;
                while (i5 < this._children.length && !(this._children[i5] instanceof SlidePersistAtom)) {
                    i5++;
                }
                int i6 = (i5 - i3) - 1;
                Record[] recordArr = new Record[i6];
                System.arraycopy(this._children, i4, recordArr, 0, i6);
                vector.add(new SlideAtomsSet((SlidePersistAtom) this._children[i3], recordArr));
                i3 += i6;
            }
            i3++;
        }
        this.slideAtomsSets = (SlideAtomsSet[]) vector.toArray(new SlideAtomsSet[vector.size()]);
    }

    public SlideListWithText() {
        byte[] bArr = new byte[8];
        this._header = bArr;
        LittleEndian.putUShort(bArr, 0, 15);
        LittleEndian.putUShort(this._header, 2, (int) _type);
        LittleEndian.putInt(this._header, 4, 0);
        this._children = new Record[0];
        this.slideAtomsSets = new SlideAtomsSet[0];
    }

    public void addSlidePersistAtom(SlidePersistAtom slidePersistAtom) {
        appendChildRecord(slidePersistAtom);
        SlideAtomsSet slideAtomsSet = new SlideAtomsSet(slidePersistAtom, new Record[0]);
        SlideAtomsSet[] slideAtomsSetArr = this.slideAtomsSets;
        int length = slideAtomsSetArr.length + 1;
        SlideAtomsSet[] slideAtomsSetArr2 = new SlideAtomsSet[length];
        System.arraycopy(slideAtomsSetArr, 0, slideAtomsSetArr2, 0, slideAtomsSetArr.length);
        slideAtomsSetArr2[length - 1] = slideAtomsSet;
        this.slideAtomsSets = slideAtomsSetArr2;
    }

    public int getInstance() {
        return LittleEndian.getShort(this._header, 0) >> 4;
    }

    public void setInstance(int i) {
        LittleEndian.putShort(this._header, (short) ((i << 4) | 15));
    }

    public SlideAtomsSet[] getSlideAtomsSets() {
        return this.slideAtomsSets;
    }

    public void setSlideAtomsSets(SlideAtomsSet[] slideAtomsSetArr) {
        this.slideAtomsSets = slideAtomsSetArr;
    }

    public long getRecordType() {
        return _type;
    }

    public class SlideAtomsSet {
        private SlidePersistAtom slidePersistAtom;
        private Record[] slideRecords;

        public SlidePersistAtom getSlidePersistAtom() {
            return this.slidePersistAtom;
        }

        public Record[] getSlideRecords() {
            return this.slideRecords;
        }

        public SlideAtomsSet(SlidePersistAtom slidePersistAtom2, Record[] recordArr) {
            this.slidePersistAtom = slidePersistAtom2;
            this.slideRecords = recordArr;
        }

        public void dispose() {
            SlidePersistAtom slidePersistAtom2 = this.slidePersistAtom;
            if (slidePersistAtom2 != null) {
                slidePersistAtom2.dispose();
            }
            Record[] recordArr = this.slideRecords;
            if (recordArr != null) {
                for (Record dispose : recordArr) {
                    dispose.dispose();
                }
                this.slideRecords = null;
            }
        }
    }

    public void dispose() {
        this._header = null;
        SlideAtomsSet[] slideAtomsSetArr = this.slideAtomsSets;
        if (slideAtomsSetArr != null) {
            for (SlideAtomsSet dispose : slideAtomsSetArr) {
                dispose.dispose();
            }
            this.slideAtomsSets = null;
        }
    }
}
