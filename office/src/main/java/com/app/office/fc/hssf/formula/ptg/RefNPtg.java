package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class RefNPtg extends Ref2DPtgBase {
    public static final byte sid = 44;

    /* access modifiers changed from: protected */
    public byte getSid() {
        return 44;
    }

    public /* bridge */ /* synthetic */ void write(LittleEndianOutput littleEndianOutput) {
        super.write(littleEndianOutput);
    }

    public RefNPtg(LittleEndianInput littleEndianInput) {
        super(littleEndianInput);
    }
}
