package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.types.FSPAAbstractType;
import com.app.office.fc.util.Internal;

@Internal
public final class FSPA extends FSPAAbstractType {
    @Deprecated
    public static final int FSPA_SIZE = getSize();

    public FSPA() {
    }

    public FSPA(byte[] bArr, int i) {
        fillFields(bArr, i);
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[FSPA_SIZE];
        serialize(bArr, 0);
        return bArr;
    }
}
