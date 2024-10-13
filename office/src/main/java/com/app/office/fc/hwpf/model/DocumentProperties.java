package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.types.DOPAbstractType;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public final class DocumentProperties extends DOPAbstractType {
    private byte[] _preserved;

    public DocumentProperties(byte[] bArr, int i) {
        this(bArr, i, DOPAbstractType.getSize());
    }

    public DocumentProperties(byte[] bArr, int i, int i2) {
        super.fillFields(bArr, i);
        int size = DOPAbstractType.getSize();
        if (i2 != size) {
            this._preserved = LittleEndian.getByteArray(bArr, i + size, i2 - size);
        } else {
            this._preserved = new byte[0];
        }
    }

    public void serialize(byte[] bArr, int i) {
        super.serialize(bArr, i);
    }
}
