package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.ss.util.CellReference;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

abstract class Ref2DPtgBase extends RefPtgBase {
    private static final int SIZE = 5;

    /* access modifiers changed from: protected */
    public abstract byte getSid();

    public final int getSize() {
        return 5;
    }

    protected Ref2DPtgBase(int i, int i2, boolean z, boolean z2) {
        setRow(i);
        setColumn(i2);
        setRowRelative(z);
        setColRelative(z2);
    }

    protected Ref2DPtgBase(LittleEndianInput littleEndianInput) {
        readCoordinates(littleEndianInput);
    }

    protected Ref2DPtgBase(CellReference cellReference) {
        super(cellReference);
    }

    public void write(LittleEndianOutput littleEndianOutput) {
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
