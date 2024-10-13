package com.app.office.fc.hwpf.model;

import androidx.core.view.MotionEventCompat;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.io.IOException;
import java.io.OutputStream;

@Internal
public final class ParagraphHeight {
    private BitField clMac = BitFieldFactory.getInstance(MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    private int dxaCol;
    private int dymLineOrHeight;
    private BitField fDiffLines = BitFieldFactory.getInstance(4);
    private BitField fSpare = BitFieldFactory.getInstance(1);
    private BitField fUnk = BitFieldFactory.getInstance(2);
    private short infoField;
    private short reserved;

    public ParagraphHeight(byte[] bArr, int i) {
        this.infoField = LittleEndian.getShort(bArr, i);
        int i2 = i + 2;
        this.reserved = LittleEndian.getShort(bArr, i2);
        int i3 = i2 + 2;
        this.dxaCol = LittleEndian.getInt(bArr, i3);
        this.dymLineOrHeight = LittleEndian.getInt(bArr, i3 + 4);
    }

    public ParagraphHeight() {
    }

    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(toByteArray());
    }

    /* access modifiers changed from: protected */
    public byte[] toByteArray() {
        byte[] bArr = new byte[12];
        LittleEndian.putShort(bArr, 0, this.infoField);
        LittleEndian.putShort(bArr, 2, this.reserved);
        LittleEndian.putInt(bArr, 4, this.dxaCol);
        LittleEndian.putInt(bArr, 8, this.dymLineOrHeight);
        return bArr;
    }

    public boolean equals(Object obj) {
        ParagraphHeight paragraphHeight = (ParagraphHeight) obj;
        return this.infoField == paragraphHeight.infoField && this.reserved == paragraphHeight.reserved && this.dxaCol == paragraphHeight.dxaCol && this.dymLineOrHeight == paragraphHeight.dymLineOrHeight;
    }
}
