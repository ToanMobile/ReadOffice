package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.ss.util.CellReference;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class RefPtg extends Ref2DPtgBase {
    public static final byte sid = 36;

    /* access modifiers changed from: protected */
    public byte getSid() {
        return 36;
    }

    public /* bridge */ /* synthetic */ void write(LittleEndianOutput littleEndianOutput) {
        super.write(littleEndianOutput);
    }

    public RefPtg(String str) {
        super(new CellReference(str));
    }

    public RefPtg(int i, int i2, boolean z, boolean z2) {
        super(i, i2, z, z2);
    }

    public RefPtg(LittleEndianInput littleEndianInput) {
        super(littleEndianInput);
    }

    public RefPtg(CellReference cellReference) {
        super(cellReference);
    }
}
