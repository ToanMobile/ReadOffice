package com.app.office.fc.hwpf.model;

import androidx.core.app.FrameMetricsAggregator;
import com.app.office.fc.hwpf.sprm.SprmBuffer;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.ArrayList;
import java.util.List;

@Internal
public final class CHPFormattedDiskPage extends FormattedDiskPage {
    private static final int FC_SIZE = 4;
    private ArrayList<CHPX> _chpxList;
    private ArrayList<CHPX> _overFlow;

    public CHPFormattedDiskPage() {
        this._chpxList = new ArrayList<>();
    }

    public CHPFormattedDiskPage(byte[] bArr, int i, int i2, TextPieceTable textPieceTable) {
        this(bArr, i, textPieceTable);
    }

    public CHPFormattedDiskPage(byte[] bArr, int i, CharIndexTranslator charIndexTranslator) {
        super(bArr, i);
        this._chpxList = new ArrayList<>();
        for (int i2 = 0; i2 < this._crun; i2++) {
            int start = getStart(i2);
            int end = getEnd(i2);
            int charIndex = charIndexTranslator.getCharIndex(start);
            int charIndex2 = charIndexTranslator.getCharIndex(end, charIndex);
            if (charIndex <= charIndex2) {
                this._chpxList.add(new CHPX(charIndex, charIndex2, new SprmBuffer(getGrpprl(i2), 0)));
            }
        }
        this._crun = this._chpxList.size();
    }

    public CHPX getCHPX(int i) {
        return this._chpxList.get(i);
    }

    public void fill(List<CHPX> list) {
        this._chpxList.addAll(list);
    }

    public ArrayList<CHPX> getOverflow() {
        return this._overFlow;
    }

    /* access modifiers changed from: protected */
    public byte[] getGrpprl(int i) {
        int unsignedByte = LittleEndian.getUnsignedByte(this._fkp, this._offset + ((this._crun + 1) * 4) + i) * 2;
        if (unsignedByte == 0) {
            return new byte[0];
        }
        int unsignedByte2 = LittleEndian.getUnsignedByte(this._fkp, this._offset + unsignedByte);
        byte[] bArr = new byte[unsignedByte2];
        System.arraycopy(this._fkp, this._offset + unsignedByte + 1, bArr, 0, unsignedByte2);
        return bArr;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public byte[] toByteArray(CharIndexTranslator charIndexTranslator, int i) {
        return toByteArray(charIndexTranslator);
    }

    /* access modifiers changed from: protected */
    public byte[] toByteArray(CharIndexTranslator charIndexTranslator) {
        int i;
        byte[] bArr = new byte[512];
        int size = this._chpxList.size();
        int i2 = 6;
        int i3 = 0;
        while (true) {
            i = FrameMetricsAggregator.EVERY_DURATION;
            if (i3 >= size) {
                break;
            }
            int length = this._chpxList.get(i3).getGrpprl().length;
            i2 += length + 6;
            if (i2 > (i3 % 2) + FrameMetricsAggregator.EVERY_DURATION) {
                break;
            }
            if ((length + 1) % 2 > 0) {
                i2++;
            }
            i3++;
        }
        if (i3 != size) {
            ArrayList<CHPX> arrayList = new ArrayList<>();
            this._overFlow = arrayList;
            arrayList.addAll(this._chpxList.subList(i3, size));
        }
        bArr[511] = (byte) i3;
        int i4 = (i3 * 4) + 4;
        CHPX chpx = null;
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            chpx = this._chpxList.get(i6);
            byte[] grpprl = chpx.getGrpprl();
            LittleEndian.putInt(bArr, i5, charIndexTranslator.getByteIndex(chpx.getStart()));
            int length2 = i - (grpprl.length + 1);
            i = length2 - (length2 % 2);
            bArr[i4] = (byte) (i / 2);
            bArr[i] = (byte) grpprl.length;
            System.arraycopy(grpprl, 0, bArr, i + 1, grpprl.length);
            i4++;
            i5 += 4;
        }
        LittleEndian.putInt(bArr, i5, charIndexTranslator.getByteIndex(chpx.getEnd()));
        return bArr;
    }
}
