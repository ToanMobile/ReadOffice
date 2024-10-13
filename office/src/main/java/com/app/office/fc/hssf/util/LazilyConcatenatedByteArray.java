package com.app.office.fc.hssf.util;

import java.util.ArrayList;
import java.util.List;

public class LazilyConcatenatedByteArray {
    private final List<byte[]> arrays = new ArrayList(1);

    public void clear() {
        this.arrays.clear();
    }

    public void concatenate(byte[] bArr) {
        if (bArr != null) {
            this.arrays.add(bArr);
            return;
        }
        throw new IllegalArgumentException("array cannot be null");
    }

    public byte[] toArray() {
        if (this.arrays.isEmpty()) {
            return null;
        }
        if (this.arrays.size() > 1) {
            int i = 0;
            for (byte[] length : this.arrays) {
                i += length.length;
            }
            byte[] bArr = new byte[i];
            int i2 = 0;
            for (byte[] next : this.arrays) {
                System.arraycopy(next, 0, bArr, i2, next.length);
                i2 += next.length;
            }
            this.arrays.clear();
            this.arrays.add(bArr);
        }
        return this.arrays.get(0);
    }
}
