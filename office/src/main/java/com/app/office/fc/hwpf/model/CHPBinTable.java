package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.PropertyNode;
import com.app.office.fc.hwpf.model.io.HWPFFileSystem;
import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.hwpf.sprm.SprmBuffer;
import com.app.office.fc.hwpf.sprm.SprmIterator;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Internal
public class CHPBinTable {
    protected ArrayList<CHPX> _textRuns;

    public CHPBinTable() {
        this._textRuns = new ArrayList<>();
    }

    public CHPBinTable(byte[] bArr, byte[] bArr2, int i, int i2, int i3, TextPieceTable textPieceTable) {
        this(bArr, bArr2, i, i2, textPieceTable);
    }

    public CHPBinTable(byte[] bArr, byte[] bArr2, int i, int i2, CharIndexTranslator charIndexTranslator) {
        this._textRuns = new ArrayList<>();
        PlexOfCps plexOfCps = new PlexOfCps(bArr2, i, i2, 4);
        int length = plexOfCps.length();
        for (int i3 = 0; i3 < length; i3++) {
            CHPFormattedDiskPage cHPFormattedDiskPage = new CHPFormattedDiskPage(bArr, LittleEndian.getInt(plexOfCps.getProperty(i3).getBytes()) * 512, charIndexTranslator);
            int size = cHPFormattedDiskPage.size();
            for (int i4 = 0; i4 < size; i4++) {
                CHPX chpx = cHPFormattedDiskPage.getCHPX(i4);
                if (chpx != null) {
                    this._textRuns.add(chpx);
                }
            }
        }
    }

    public void rebuild(ComplexFileTable complexFileTable) {
        short igrpprl;
        boolean z;
        System.currentTimeMillis();
        if (complexFileTable != null) {
            SprmBuffer[] grpprls = complexFileTable.getGrpprls();
            for (TextPiece next : complexFileTable.getTextPieceTable().getTextPieces()) {
                PropertyModifier prm = next.getPieceDescriptor().getPrm();
                if (prm.isComplex() && (igrpprl = prm.getIgrpprl()) >= 0 && igrpprl < grpprls.length) {
                    SprmBuffer sprmBuffer = grpprls[igrpprl];
                    SprmIterator it = sprmBuffer.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().getType() == 2) {
                                z = true;
                                break;
                            }
                        } else {
                            z = false;
                            break;
                        }
                    }
                    if (z) {
                        try {
                            this._textRuns.add(new CHPX(next.getStart(), next.getEnd(), (SprmBuffer) sprmBuffer.clone()));
                        } catch (CloneNotSupportedException e) {
                            throw new Error(e);
                        }
                    }
                }
            }
        }
        ArrayList arrayList = new ArrayList(this._textRuns);
        Collections.sort(arrayList, PropertyNode.StartComparator.instance);
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator<CHPX> it2 = this._textRuns.iterator();
        int i = 0;
        while (it2.hasNext()) {
            identityHashMap.put(it2.next(), Integer.valueOf(i));
            i++;
        }
        AnonymousClass1 r3 = new Comparator<CHPX>() {
            public int compare(CHPX chpx, CHPX chpx2) {
                return ((Integer) identityHashMap.get(chpx)).compareTo((Integer) identityHashMap.get(chpx2));
            }
        };
        HashSet hashSet = new HashSet();
        Iterator<CHPX> it3 = this._textRuns.iterator();
        while (it3.hasNext()) {
            CHPX next2 = it3.next();
            hashSet.add(Integer.valueOf(next2.getStart()));
            hashSet.add(Integer.valueOf(next2.getEnd()));
        }
        hashSet.remove(0);
        ArrayList<Integer> arrayList2 = new ArrayList<>(hashSet);
        Collections.sort(arrayList2);
        LinkedList linkedList = new LinkedList();
        int i2 = 0;
        for (Integer intValue : arrayList2) {
            int intValue2 = intValue.intValue();
            int abs = Math.abs(binarySearch(arrayList, intValue2));
            while (abs >= arrayList.size()) {
                abs--;
            }
            while (abs > 0 && ((CHPX) arrayList.get(abs)).getStart() >= intValue2) {
                abs--;
            }
            LinkedList<CHPX> linkedList2 = new LinkedList<>();
            while (abs < arrayList.size()) {
                CHPX chpx = (CHPX) arrayList.get(abs);
                if (intValue2 < chpx.getStart()) {
                    break;
                }
                if (Math.max(i2, chpx.getStart()) < Math.min(intValue2, chpx.getEnd())) {
                    linkedList2.add(chpx);
                }
                abs++;
            }
            if (linkedList2.size() == 0) {
                linkedList.add(new CHPX(i2, intValue2, new SprmBuffer(0)));
            } else {
                if (linkedList2.size() == 1) {
                    CHPX chpx2 = (CHPX) linkedList2.get(0);
                    if (chpx2.getStart() == i2 && chpx2.getEnd() == intValue2) {
                        linkedList.add(chpx2);
                    }
                }
                Collections.sort(linkedList2, r3);
                SprmBuffer sprmBuffer2 = new SprmBuffer(0);
                for (CHPX grpprl : linkedList2) {
                    sprmBuffer2.append(grpprl.getGrpprl(), 0);
                }
                linkedList.add(new CHPX(i2, intValue2, sprmBuffer2));
            }
            i2 = intValue2;
        }
        ArrayList<CHPX> arrayList3 = new ArrayList<>(linkedList);
        this._textRuns = arrayList3;
        CHPX chpx3 = null;
        Iterator<CHPX> it4 = arrayList3.iterator();
        while (it4.hasNext()) {
            CHPX next3 = it4.next();
            if (chpx3 != null && chpx3.getEnd() == next3.getStart() && Arrays.equals(chpx3.getGrpprl(), next3.getGrpprl())) {
                chpx3.setEnd(next3.getEnd());
                it4.remove();
            } else {
                chpx3 = next3;
            }
        }
    }

    private static int binarySearch(List<CHPX> list, int i) {
        int size = list.size() - 1;
        int i2 = 0;
        while (i2 <= size) {
            int i3 = (i2 + size) >>> 1;
            int start = list.get(i3).getStart();
            if (start < i) {
                i2 = i3 + 1;
            } else if (start <= i) {
                return i3;
            } else {
                size = i3 - 1;
            }
        }
        return -(i2 + 1);
    }

    public void adjustForDelete(int i, int i2, int i3) {
        int size = this._textRuns.size();
        int i4 = i2 + i3;
        CHPX chpx = this._textRuns.get(i);
        int i5 = i;
        while (chpx.getEnd() < i4) {
            i5++;
            chpx = this._textRuns.get(i5);
        }
        if (i == i5) {
            CHPX chpx2 = this._textRuns.get(i5);
            chpx2.setEnd((chpx2.getEnd() - i4) + i2);
        } else {
            this._textRuns.get(i).setEnd(i2);
            while (true) {
                i++;
                if (i >= i5) {
                    break;
                }
                CHPX chpx3 = this._textRuns.get(i);
                chpx3.setStart(i2);
                chpx3.setEnd(i2);
            }
            CHPX chpx4 = this._textRuns.get(i5);
            chpx4.setEnd((chpx4.getEnd() - i4) + i2);
        }
        while (true) {
            i5++;
            if (i5 < size) {
                CHPX chpx5 = this._textRuns.get(i5);
                chpx5.setStart(chpx5.getStart() - i3);
                chpx5.setEnd(chpx5.getEnd() - i3);
            } else {
                return;
            }
        }
    }

    public void insert(int i, int i2, SprmBuffer sprmBuffer) {
        CHPX chpx = new CHPX(0, 0, sprmBuffer);
        chpx.setStart(i2);
        chpx.setEnd(i2);
        if (i == this._textRuns.size()) {
            this._textRuns.add(chpx);
            return;
        }
        CHPX chpx2 = this._textRuns.get(i);
        if (chpx2.getStart() < i2) {
            CHPX chpx3 = new CHPX(0, 0, chpx2.getSprmBuf());
            chpx3.setStart(i2);
            chpx3.setEnd(chpx2.getEnd());
            chpx2.setEnd(i2);
            this._textRuns.add(i + 1, chpx);
            this._textRuns.add(i + 2, chpx3);
            return;
        }
        this._textRuns.add(i, chpx);
    }

    public void adjustForInsert(int i, int i2) {
        int size = this._textRuns.size();
        CHPX chpx = this._textRuns.get(i);
        chpx.setEnd(chpx.getEnd() + i2);
        while (true) {
            i++;
            if (i < size) {
                CHPX chpx2 = this._textRuns.get(i);
                chpx2.setStart(chpx2.getStart() + i2);
                chpx2.setEnd(chpx2.getEnd() + i2);
            } else {
                return;
            }
        }
    }

    public List<CHPX> getTextRuns() {
        return this._textRuns;
    }

    @Deprecated
    public void writeTo(HWPFFileSystem hWPFFileSystem, int i, CharIndexTranslator charIndexTranslator) throws IOException {
        writeTo(hWPFFileSystem.getStream("WordDocument"), hWPFFileSystem.getStream("1Table"), i, charIndexTranslator);
    }

    public void writeTo(HWPFOutputStream hWPFOutputStream, HWPFOutputStream hWPFOutputStream2, int i, CharIndexTranslator charIndexTranslator) throws IOException {
        PlexOfCps plexOfCps = new PlexOfCps(4);
        int offset = hWPFOutputStream.getOffset() % 512;
        if (offset != 0) {
            hWPFOutputStream.write(new byte[(512 - offset)]);
        }
        int offset2 = hWPFOutputStream.getOffset() / 512;
        ArrayList<CHPX> arrayList = this._textRuns;
        int byteIndex = charIndexTranslator.getByteIndex(arrayList.get(arrayList.size() - 1).getEnd());
        ArrayList<CHPX> arrayList2 = this._textRuns;
        while (true) {
            int byteIndex2 = charIndexTranslator.getByteIndex(arrayList2.get(0).getStart());
            CHPFormattedDiskPage cHPFormattedDiskPage = new CHPFormattedDiskPage();
            cHPFormattedDiskPage.fill(arrayList2);
            hWPFOutputStream.write(cHPFormattedDiskPage.toByteArray(charIndexTranslator));
            arrayList2 = cHPFormattedDiskPage.getOverflow();
            int byteIndex3 = arrayList2 != null ? charIndexTranslator.getByteIndex(arrayList2.get(0).getStart()) : byteIndex;
            byte[] bArr = new byte[4];
            int i2 = offset2 + 1;
            LittleEndian.putInt(bArr, offset2);
            plexOfCps.addProperty(new GenericPropertyNode(byteIndex2, byteIndex3, bArr));
            if (arrayList2 == null) {
                hWPFOutputStream2.write(plexOfCps.toByteArray());
                return;
            }
            offset2 = i2;
        }
    }
}
