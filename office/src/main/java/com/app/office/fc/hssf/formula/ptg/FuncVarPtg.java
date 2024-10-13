package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.hssf.formula.function.FunctionMetadata;
import com.app.office.fc.hssf.formula.function.FunctionMetadataRegistry;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class FuncVarPtg extends AbstractFunctionPtg {
    private static final int SIZE = 4;
    public static final OperationPtg SUM = create("SUM", 1);
    public static final byte sid = 34;

    public int getSize() {
        return 4;
    }

    private FuncVarPtg(int i, int i2, byte[] bArr, int i3) {
        super(i, i2, bArr, i3);
    }

    public static FuncVarPtg create(LittleEndianInput littleEndianInput) {
        return create((int) littleEndianInput.readByte(), (int) littleEndianInput.readShort());
    }

    public static FuncVarPtg create(String str, int i) {
        return create(i, (int) lookupIndex(str));
    }

    private static FuncVarPtg create(int i, int i2) {
        FunctionMetadata functionByIndex = FunctionMetadataRegistry.getFunctionByIndex(i2);
        if (functionByIndex != null) {
            return new FuncVarPtg(i2, functionByIndex.getReturnClassCode(), functionByIndex.getParameterClassCodes(), i);
        }
        return new FuncVarPtg(i2, 32, new byte[]{32}, i);
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 34);
        littleEndianOutput.writeByte(getNumberOfOperands());
        littleEndianOutput.writeShort(getFunctionIndex());
    }
}
