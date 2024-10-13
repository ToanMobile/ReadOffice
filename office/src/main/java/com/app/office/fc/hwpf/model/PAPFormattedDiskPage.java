package com.app.office.fc.hwpf.model;

import androidx.core.app.FrameMetricsAggregator;
import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Internal
public final class PAPFormattedDiskPage extends FormattedDiskPage {
    private static final int BX_SIZE = 13;
    private static final int FC_SIZE = 4;
    private ArrayList<PAPX> _overFlow;
    private ArrayList<PAPX> _papxList;

    public PAPFormattedDiskPage(byte[] bArr) {
        this();
    }

    public PAPFormattedDiskPage() {
        this._papxList = new ArrayList<>();
    }

    public PAPFormattedDiskPage(byte[] bArr, byte[] bArr2, int i, int i2, TextPieceTable textPieceTable) {
        this(bArr, bArr2, i, textPieceTable);
    }

    public PAPFormattedDiskPage(byte[] bArr, byte[] bArr2, int i, CharIndexTranslator charIndexTranslator) {
        super(bArr, i);
        this._papxList = new ArrayList<>();
        for (int i2 = 0; i2 < this._crun; i2++) {
            int start = getStart(i2);
            int end = getEnd(i2);
            int charIndex = charIndexTranslator.getCharIndex(start);
            int charIndex2 = charIndexTranslator.getCharIndex(end, charIndex);
            if (charIndex <= charIndex2) {
                this._papxList.add(new PAPX(charIndex, charIndex2, getGrpprl(i2), getParagraphHeight(i2), bArr2));
            }
        }
        this._crun = this._papxList.size();
        this._fkp = null;
    }

    public void fill(List<PAPX> list) {
        this._papxList.addAll(list);
    }

    /* access modifiers changed from: package-private */
    public ArrayList<PAPX> getOverflow() {
        return this._overFlow;
    }

    public PAPX getPAPX(int i) {
        return this._papxList.get(i);
    }

    public List<PAPX> getPAPXs() {
        return Collections.unmodifiableList(this._papxList);
    }

    /* access modifiers changed from: protected */
    public byte[] getGrpprl(int i) {
        int i2;
        int unsignedByte = LittleEndian.getUnsignedByte(this._fkp, this._offset + ((this._crun + 1) * 4) + (i * 13)) * 2;
        int unsignedByte2 = LittleEndian.getUnsignedByte(this._fkp, this._offset + unsignedByte) * 2;
        if (unsignedByte2 == 0) {
            unsignedByte++;
            i2 = LittleEndian.getUnsignedByte(this._fkp, this._offset + unsignedByte) * 2;
        } else {
            i2 = unsignedByte2 - 1;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this._fkp, this._offset + unsignedByte + 1, bArr, 0, i2);
        return bArr;
    }

    /* access modifiers changed from: protected */
    public byte[] toByteArray(HWPFOutputStream hWPFOutputStream, CharIndexTranslator charIndexTranslator) throws IOException {
        int i;
        int i2;
        int i3;
        byte[] bArr;
        int i4;
        PAPFormattedDiskPage pAPFormattedDiskPage = this;
        CharIndexTranslator charIndexTranslator2 = charIndexTranslator;
        byte[] bArr2 = new byte[512];
        int size = pAPFormattedDiskPage._papxList.size();
        byte[] bArr3 = new byte[0];
        int i5 = 0;
        int i6 = 4;
        while (true) {
            i = 488;
            i2 = FrameMetricsAggregator.EVERY_DURATION;
            if (i5 >= size) {
                break;
            }
            byte[] grpprl = pAPFormattedDiskPage._papxList.get(i5).getGrpprl();
            int length = grpprl.length;
            if (length > 488) {
                length = 8;
            }
            int i7 = i6 + (!Arrays.equals(grpprl, bArr3) ? length + 17 + 1 : 17);
            if (i7 > (i5 % 2) + FrameMetricsAggregator.EVERY_DURATION) {
                break;
            }
            i6 = length % 2 > 0 ? i7 + 1 : i7 + 2;
            i5++;
            bArr3 = grpprl;
        }
        if (i5 != size) {
            ArrayList<PAPX> arrayList = new ArrayList<>();
            pAPFormattedDiskPage._overFlow = arrayList;
            arrayList.addAll(pAPFormattedDiskPage._papxList.subList(i5, size));
        }
        bArr2[511] = (byte) i5;
        int i8 = (i5 * 4) + 4;
        PAPX papx = null;
        byte[] bArr4 = new byte[0];
        int i9 = 0;
        int i10 = 0;
        while (i9 < i5) {
            papx = pAPFormattedDiskPage._papxList.get(i9);
            byte[] byteArray = papx.getParagraphHeight().toByteArray();
            byte[] grpprl2 = papx.getGrpprl();
            if (grpprl2.length > i) {
                byte[] bArr5 = new byte[(grpprl2.length - 2)];
                System.arraycopy(grpprl2, 2, bArr5, 0, grpprl2.length - 2);
                int offset = hWPFOutputStream.getOffset();
                hWPFOutputStream.write(bArr5);
                bArr = new byte[8];
                LittleEndian.putUShort(bArr, 0, LittleEndian.getUShort(grpprl2, 0));
                i3 = 2;
                LittleEndian.putUShort(bArr, 2, 26182);
                LittleEndian.putInt(bArr, 4, offset);
            } else {
                HWPFOutputStream hWPFOutputStream2 = hWPFOutputStream;
                bArr = grpprl2;
                i3 = 2;
            }
            boolean equals = Arrays.equals(bArr4, bArr);
            if (!equals) {
                int length2 = i2 - (bArr.length + (2 - (bArr.length % i3)));
                i2 = length2 - (length2 % 2);
            }
            LittleEndian.putInt(bArr2, i10, charIndexTranslator2.getByteIndex(papx.getStart()));
            bArr2[i8] = (byte) (i2 / 2);
            System.arraycopy(byteArray, 0, bArr2, i8 + 1, byteArray.length);
            if (!equals) {
                if (bArr.length % 2 > 0) {
                    i4 = i2 + 1;
                    bArr2[i2] = (byte) ((bArr.length + 1) / 2);
                } else {
                    int i11 = i2 + 1;
                    bArr2[i11] = (byte) (bArr.length / 2);
                    i4 = i11 + 1;
                }
                System.arraycopy(bArr, 0, bArr2, i4, bArr.length);
                bArr4 = bArr;
            }
            i8 += 13;
            i10 += 4;
            i9++;
            pAPFormattedDiskPage = this;
            i = 488;
        }
        LittleEndian.putInt(bArr2, i10, charIndexTranslator2.getByteIndex(papx.getEnd()));
        return bArr2;
    }

    private ParagraphHeight getParagraphHeight(int i) {
        return new ParagraphHeight(this._fkp, this._offset + 1 + ((this._crun + 1) * 4) + (i * 13));
    }
}
