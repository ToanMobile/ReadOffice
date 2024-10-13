package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.util.LittleEndianOutput;

public abstract class ValueOperatorPtg extends OperationPtg {
    public final byte getDefaultOperandClass() {
        return 32;
    }

    /* access modifiers changed from: protected */
    public abstract byte getSid();

    public final int getSize() {
        return 1;
    }

    public final boolean isBaseToken() {
        return true;
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getSid());
    }

    public final String toFormulaString() {
        throw new RuntimeException("toFormulaString(String[] operands) should be used for subclasses of OperationPtgs");
    }
}
