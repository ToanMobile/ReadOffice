package com.app.office.fc.hpsf;

import com.app.office.fc.util.HexDump;

public class IllegalVariantTypeException extends VariantTypeException {
    public IllegalVariantTypeException(long j, Object obj, String str) {
        super(j, obj, str);
    }

    public IllegalVariantTypeException(long j, Object obj) {
        this(j, obj, "The variant type " + j + " (" + Variant.getVariantName(j) + ", " + HexDump.toHex(j) + ") is illegal in this context.");
    }
}
