package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.hssf.formula.function.FunctionMetadata;
import com.app.office.fc.hssf.formula.function.FunctionMetadataRegistry;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class FuncPtg extends AbstractFunctionPtg {
    public static final int SIZE = 3;
    public static final byte sid = 33;

    public int getSize() {
        return 3;
    }

    public static FuncPtg create(LittleEndianInput littleEndianInput) {
        return create(littleEndianInput.readUShort());
    }

    private FuncPtg(int i, FunctionMetadata functionMetadata) {
        super(i, functionMetadata.getReturnClassCode(), functionMetadata.getParameterClassCodes(), functionMetadata.getMinParams());
    }

    public static FuncPtg create(int i) {
        FunctionMetadata functionByIndex = FunctionMetadataRegistry.getFunctionByIndex(i);
        if (functionByIndex != null) {
            return new FuncPtg(i, functionByIndex);
        }
        throw new RuntimeException("Invalid built-in function index (" + i + ")");
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 33);
        littleEndianOutput.writeShort(getFunctionIndex());
    }
}
