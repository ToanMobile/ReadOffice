package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.ss.util.AreaReference;
import com.app.office.fc.util.LittleEndianInput;

public final class AreaPtg extends Area2DPtgBase {
    public static final short sid = 37;

    /* access modifiers changed from: protected */
    public byte getSid() {
        return Field.PAGEREF;
    }

    public AreaPtg(int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3, boolean z4) {
        super(i, i2, i3, i4, z, z2, z3, z4);
    }

    public AreaPtg(LittleEndianInput littleEndianInput) {
        super(littleEndianInput);
    }

    public AreaPtg(String str) {
        super(new AreaReference(str));
    }

    public AreaPtg(AreaReference areaReference) {
        super(areaReference);
    }
}
