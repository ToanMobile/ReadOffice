package com.app.office.fc.hwpf.model;

import com.app.office.fc.util.Internal;
import java.util.Arrays;

@Internal
public final class UPX {
    private byte[] _upx;

    public UPX(byte[] bArr) {
        this._upx = bArr;
    }

    public byte[] getUPX() {
        return this._upx;
    }

    public int size() {
        return this._upx.length;
    }

    public boolean equals(Object obj) {
        return Arrays.equals(this._upx, ((UPX) obj)._upx);
    }
}
