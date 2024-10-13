package com.app.office.fc.hslf.record;

import com.app.office.fc.util.StringUtil;

public final class DocumentEncryptionAtom extends RecordAtom {
    private static long _type = 12052;
    private byte[] _header;
    private byte[] data;
    private String encryptionProviderName;

    protected DocumentEncryptionAtom(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[8];
        this._header = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, 8);
        int i3 = i2 - 8;
        byte[] bArr3 = new byte[i3];
        this.data = bArr3;
        int i4 = i + 8;
        System.arraycopy(bArr, i4, bArr3, 0, i3);
        int i5 = i4 + 44;
        int i6 = -1;
        for (int i7 = i5; i7 < i + i2 && i6 < 0; i7 += 2) {
            if (bArr[i7] == 0 && bArr[i7 + 1] == 0) {
                i6 = i7;
            }
        }
        this.encryptionProviderName = StringUtil.getFromUnicodeLE(bArr, i5, (i6 - i5) / 2);
    }

    public int getKeyLength() {
        return this.data[28];
    }

    public String getEncryptionProviderName() {
        return this.encryptionProviderName;
    }

    public long getRecordType() {
        return _type;
    }

    public void dispose() {
        this._header = null;
        this.data = null;
        this.encryptionProviderName = null;
    }
}
