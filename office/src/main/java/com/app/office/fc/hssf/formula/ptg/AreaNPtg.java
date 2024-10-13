package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.hwpf.usermodel.Field;
import com.app.office.fc.util.LittleEndianInput;

public final class AreaNPtg extends Area2DPtgBase {
    public static final short sid = 45;

    /* access modifiers changed from: protected */
    public byte getSid() {
        return Field.DDE;
    }

    public AreaNPtg(LittleEndianInput littleEndianInput) {
        super(littleEndianInput);
    }
}
