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
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;

@Internal
public class PAPBinTable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected ArrayList<PAPX> _paragraphs;

    public PAPBinTable() {
        this._paragraphs = new ArrayList<>();
    }

    public PAPBinTable(byte[] bArr, byte[] bArr2, byte[] bArr3, int i, int i2, int i3, TextPieceTable textPieceTable) {
        this(bArr, bArr2, bArr3, i, i2, textPieceTable);
    }

    public PAPBinTable(byte[] bArr, byte[] bArr2, byte[] bArr3, int i, int i2, CharIndexTranslator charIndexTranslator) {
        this._paragraphs = new ArrayList<>();
        System.currentTimeMillis();
        PlexOfCps plexOfCps = new PlexOfCps(bArr2, i, i2, 4);
        int length = plexOfCps.length();
        for (int i3 = 0; i3 < length; i3++) {
            PAPFormattedDiskPage pAPFormattedDiskPage = new PAPFormattedDiskPage(bArr, bArr3, LittleEndian.getInt(plexOfCps.getProperty(i3).getBytes()) * 512, charIndexTranslator);
            int size = pAPFormattedDiskPage.size();
            for (int i4 = 0; i4 < size; i4++) {
                PAPX papx = pAPFormattedDiskPage.getPAPX(i4);
                if (papx != null) {
                    this._paragraphs.add(papx);
                }
            }
        }
    }

    public void rebuild(StringBuilder sb, ComplexFileTable complexFileTable) {
        boolean z;
        short igrpprl;
        boolean z2;
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
                            if (it.next().getType() == 1) {
                                z2 = true;
                                break;
                            }
                        } else {
                            z2 = false;
                            break;
                        }
                    }
                    if (z2) {
                        SprmBuffer sprmBuffer2 = new SprmBuffer(2);
                        sprmBuffer2.append(sprmBuffer.toByteArray());
                        this._paragraphs.add(new PAPX(next.getStart(), next.getEnd(), sprmBuffer2));
                    }
                }
            }
        }
        ArrayList arrayList = new ArrayList(this._paragraphs);
        Collections.sort(arrayList, PropertyNode.EndComparator.instance);
        System.currentTimeMillis();
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator<PAPX> it2 = this._paragraphs.iterator();
        int i = 0;
        while (it2.hasNext()) {
            identityHashMap.put(it2.next(), Integer.valueOf(i));
            i++;
        }
        AnonymousClass1 r4 = new Comparator<PAPX>() {
            public int compare(PAPX papx, PAPX papx2) {
                return ((Integer) identityHashMap.get(papx)).compareTo((Integer) identityHashMap.get(papx2));
            }
        };
        System.currentTimeMillis();
        LinkedList linkedList = new LinkedList();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < sb.length(); i4++) {
            char charAt = sb.charAt(i4);
            if (charAt == 13 || charAt == 7 || charAt == 12) {
                int i5 = i4 + 1;
                LinkedList<PAPX> linkedList2 = new LinkedList<>();
                int i6 = i3;
                while (true) {
                    if (i6 >= arrayList.size()) {
                        z = false;
                        break;
                    }
                    PAPX papx = (PAPX) arrayList.get(i6);
                    if (papx.getEnd() - 1 > i4) {
                        i3 = i6;
                        z = true;
                        break;
                    }
                    linkedList2.add(papx);
                    i6++;
                }
                if (!z) {
                    i3 = arrayList.size() - 1;
                }
                if (linkedList2.size() == 0) {
                    linkedList.add(new PAPX(i2, i5, new SprmBuffer(2)));
                } else {
                    if (linkedList2.size() == 1) {
                        PAPX papx2 = (PAPX) linkedList2.get(0);
                        if (papx2.getStart() == i2 && papx2.getEnd() == i5) {
                            linkedList.add(papx2);
                        }
                    }
                    Collections.sort(linkedList2, r4);
                    SprmBuffer sprmBuffer3 = null;
                    for (PAPX papx3 : linkedList2) {
                        if (sprmBuffer3 == null) {
                            try {
                                sprmBuffer3 = (SprmBuffer) papx3.getSprmBuf().clone();
                            } catch (CloneNotSupportedException e) {
                                throw new Error(e);
                            }
                        } else {
                            sprmBuffer3.append(papx3.getGrpprl(), 2);
                        }
                    }
                    linkedList.add(new PAPX(i2, i5, sprmBuffer3));
                }
                i2 = i5;
            }
        }
        this._paragraphs = new ArrayList<>(linkedList);
        System.currentTimeMillis();
    }

    public void insert(int i, int i2, SprmBuffer sprmBuffer) {
        PAPX papx = new PAPX(0, 0, sprmBuffer);
        papx.setStart(i2);
        papx.setEnd(i2);
        if (i == this._paragraphs.size()) {
            this._paragraphs.add(papx);
            return;
        }
        PAPX papx2 = this._paragraphs.get(i);
        if (papx2 == null || papx2.getStart() >= i2) {
            this._paragraphs.add(i, papx);
            return;
        }
        SprmBuffer sprmBuffer2 = null;
        try {
            sprmBuffer2 = (SprmBuffer) papx2.getSprmBuf().clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        PAPX papx3 = new PAPX(0, 0, sprmBuffer2);
        papx3.setStart(i2);
        papx3.setEnd(papx2.getEnd());
        papx2.setEnd(i2);
        this._paragraphs.add(i + 1, papx);
        this._paragraphs.add(i + 2, papx3);
    }

    public void adjustForDelete(int i, int i2, int i3) {
        int size = this._paragraphs.size();
        int i4 = i2 + i3;
        PAPX papx = this._paragraphs.get(i);
        int i5 = i;
        while (papx.getEnd() < i4) {
            i5++;
            papx = this._paragraphs.get(i5);
        }
        if (i == i5) {
            PAPX papx2 = this._paragraphs.get(i5);
            papx2.setEnd((papx2.getEnd() - i4) + i2);
        } else {
            this._paragraphs.get(i).setEnd(i2);
            while (true) {
                i++;
                if (i >= i5) {
                    break;
                }
                PAPX papx3 = this._paragraphs.get(i);
                papx3.setStart(i2);
                papx3.setEnd(i2);
            }
            PAPX papx4 = this._paragraphs.get(i5);
            papx4.setEnd((papx4.getEnd() - i4) + i2);
        }
        while (true) {
            i5++;
            if (i5 < size) {
                PAPX papx5 = this._paragraphs.get(i5);
                papx5.setStart(papx5.getStart() - i3);
                papx5.setEnd(papx5.getEnd() - i3);
            } else {
                return;
            }
        }
    }

    public void adjustForInsert(int i, int i2) {
        int size = this._paragraphs.size();
        PAPX papx = this._paragraphs.get(i);
        papx.setEnd(papx.getEnd() + i2);
        while (true) {
            i++;
            if (i < size) {
                PAPX papx2 = this._paragraphs.get(i);
                papx2.setStart(papx2.getStart() + i2);
                papx2.setEnd(papx2.getEnd() + i2);
            } else {
                return;
            }
        }
    }

    public ArrayList<PAPX> getParagraphs() {
        return this._paragraphs;
    }

    @Deprecated
    public void writeTo(HWPFFileSystem hWPFFileSystem, CharIndexTranslator charIndexTranslator) throws IOException {
        writeTo(hWPFFileSystem.getStream("WordDocument"), hWPFFileSystem.getStream("1Table"), charIndexTranslator);
    }

    public void writeTo(HWPFOutputStream hWPFOutputStream, HWPFOutputStream hWPFOutputStream2, CharIndexTranslator charIndexTranslator) throws IOException {
        PlexOfCps plexOfCps = new PlexOfCps(4);
        int offset = hWPFOutputStream.getOffset() % 512;
        if (offset != 0) {
            hWPFOutputStream.write(new byte[(512 - offset)]);
        }
        int offset2 = hWPFOutputStream.getOffset() / 512;
        ArrayList<PAPX> arrayList = this._paragraphs;
        int byteIndex = charIndexTranslator.getByteIndex(arrayList.get(arrayList.size() - 1).getEnd());
        ArrayList<PAPX> arrayList2 = this._paragraphs;
        while (true) {
            int byteIndex2 = charIndexTranslator.getByteIndex(arrayList2.get(0).getStart());
            PAPFormattedDiskPage pAPFormattedDiskPage = new PAPFormattedDiskPage();
            pAPFormattedDiskPage.fill(arrayList2);
            hWPFOutputStream.write(pAPFormattedDiskPage.toByteArray(hWPFOutputStream2, charIndexTranslator));
            arrayList2 = pAPFormattedDiskPage.getOverflow();
            int byteIndex3 = arrayList2 != null ? charIndexTranslator.getByteIndex(arrayList2.get(0).getStart()) : byteIndex;
            byte[] bArr = new byte[4];
            int i = offset2 + 1;
            LittleEndian.putInt(bArr, offset2);
            plexOfCps.addProperty(new GenericPropertyNode(byteIndex2, byteIndex3, bArr));
            if (arrayList2 == null) {
                hWPFOutputStream2.write(plexOfCps.toByteArray());
                return;
            }
            offset2 = i;
        }
    }
}
