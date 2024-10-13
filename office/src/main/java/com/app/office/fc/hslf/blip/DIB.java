package com.app.office.fc.hslf.blip;

import com.app.office.fc.util.LittleEndian;
import java.io.IOException;

public final class DIB extends Bitmap {
    public static final int HEADER_SIZE = 14;

    public int getSignature() {
        return 31360;
    }

    public int getType() {
        return 7;
    }

    public byte[] getData() {
        return addBMPHeader(super.getData());
    }

    public static byte[] addBMPHeader(byte[] bArr) {
        byte[] bArr2 = new byte[14];
        LittleEndian.putInt(bArr2, 0, 19778);
        int i = LittleEndian.getInt(bArr, 20);
        int length = bArr.length + 14;
        LittleEndian.putInt(bArr2, 2, length);
        LittleEndian.putInt(bArr2, 6, 0);
        LittleEndian.putInt(bArr2, 10, length - i);
        byte[] bArr3 = new byte[(bArr.length + 14)];
        System.arraycopy(bArr2, 0, bArr3, 0, 14);
        System.arraycopy(bArr, 0, bArr3, 14, bArr.length);
        return bArr3;
    }

    public void setData(byte[] bArr) throws IOException {
        int length = bArr.length - 14;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 14, bArr2, 0, length);
        super.setData(bArr2);
    }
}
