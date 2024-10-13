package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.ss.util.AreaReference;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public abstract class Area2DPtgBase extends AreaPtgBase {
    private static final int SIZE = 9;

    /* access modifiers changed from: protected */
    public abstract byte getSid();

    public final int getSize() {
        return 9;
    }

    protected Area2DPtgBase(int i, int i2, int i3, int i4, boolean z, boolean z2, boolean z3, boolean z4) {
        super(i, i2, i3, i4, z, z2, z3, z4);
    }

    protected Area2DPtgBase(AreaReference areaReference) {
        super(areaReference);
    }

    protected Area2DPtgBase(LittleEndianInput littleEndianInput) {
        readCoordinates(littleEndianInput);
    }

    public final void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getSid() + getPtgClass());
        writeCoordinates(littleEndianOutput);
    }

    public final String toFormulaString() {
        return formatReferenceAsString();
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [");
        stringBuffer.append(formatReferenceAsString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
