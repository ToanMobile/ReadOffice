package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.StringUtil;
import java.io.IOException;

@Internal
class SttbfUtils {
    SttbfUtils() {
    }

    public static String[] read(byte[] bArr, int i) {
        if (LittleEndian.getShort(bArr, i) == -1) {
            int i2 = i + 2;
            int i3 = LittleEndian.getInt(bArr, i2);
            int i4 = i2 + 4;
            String[] strArr = new String[i3];
            for (int i5 = 0; i5 < i3; i5++) {
                short s = LittleEndian.getShort(bArr, i4);
                int i6 = i4 + 2;
                String fromUnicodeLE = StringUtil.getFromUnicodeLE(bArr, i6, s);
                i4 = i6 + (s * 2);
                strArr[i5] = fromUnicodeLE;
            }
            return strArr;
        }
        throw new UnsupportedOperationException("Non-extended character Pascal strings are not supported right now. Please, contact POI developers for update.");
    }

    public static int write(HWPFOutputStream hWPFOutputStream, String[] strArr) throws IOException {
        int i = 6;
        byte[] bArr = new byte[6];
        LittleEndian.putShort(bArr, 0, -1);
        if (strArr == null || strArr.length == 0) {
            LittleEndian.putInt(bArr, 2, 0);
            hWPFOutputStream.write(bArr);
            return 6;
        }
        LittleEndian.putInt(bArr, 2, strArr.length);
        hWPFOutputStream.write(bArr);
        for (String str : strArr) {
            int length = (str.length() * 2) + 2;
            byte[] bArr2 = new byte[length];
            LittleEndian.putShort(bArr2, 0, (short) str.length());
            StringUtil.putUnicodeLE(str, bArr2, 2);
            hWPFOutputStream.write(bArr2);
            i += length;
        }
        return i;
    }
}
