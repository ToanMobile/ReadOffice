package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.StringUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Internal
public final class RevisionMarkAuthorTable {
    private short cData;
    private short cbExtra;
    private String[] entries;
    private short fExtend = -1;

    public RevisionMarkAuthorTable(byte[] bArr, int i, int i2) throws IOException {
        this.cData = 0;
        this.cbExtra = 0;
        this.fExtend = LittleEndian.getShort(bArr, i);
        int i3 = i + 2;
        this.cData = LittleEndian.getShort(bArr, i3);
        int i4 = i3 + 2;
        this.cbExtra = LittleEndian.getShort(bArr, i4);
        int i5 = i4 + 2;
        this.entries = new String[this.cData];
        for (int i6 = 0; i6 < this.cData; i6++) {
            short s = LittleEndian.getShort(bArr, i5);
            int i7 = i5 + 2;
            String fromUnicodeLE = StringUtil.getFromUnicodeLE(bArr, i7, s);
            i5 = i7 + (s * 2);
            this.entries[i6] = fromUnicodeLE;
        }
    }

    public List<String> getEntries() {
        return Collections.unmodifiableList(Arrays.asList(this.entries));
    }

    public String getAuthor(int i) {
        if (i >= 0) {
            String[] strArr = this.entries;
            if (i < strArr.length) {
                return strArr[i];
            }
        }
        return null;
    }

    public int getSize() {
        return this.cData;
    }

    public void writeTo(HWPFOutputStream hWPFOutputStream) throws IOException {
        byte[] bArr = new byte[6];
        LittleEndian.putShort(bArr, 0, this.fExtend);
        LittleEndian.putShort(bArr, 2, this.cData);
        LittleEndian.putShort(bArr, 4, this.cbExtra);
        hWPFOutputStream.write(bArr);
        for (String str : this.entries) {
            byte[] bArr2 = new byte[((str.length() * 2) + 2)];
            LittleEndian.putShort(bArr2, 0, (short) str.length());
            StringUtil.putUnicodeLE(str, bArr2, 2);
            hWPFOutputStream.write(bArr2);
        }
    }
}
