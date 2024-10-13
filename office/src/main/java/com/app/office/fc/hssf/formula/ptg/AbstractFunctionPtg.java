package com.app.office.fc.hssf.formula.ptg;

import com.app.office.fc.hssf.formula.function.FunctionMetadata;
import com.app.office.fc.hssf.formula.function.FunctionMetadataRegistry;

public abstract class AbstractFunctionPtg extends OperationPtg {
    private static final short FUNCTION_INDEX_EXTERNAL = 255;
    public static final String FUNCTION_NAME_IF = "IF";
    private final short _functionIndex;
    private final byte _numberOfArgs;
    private final byte[] paramClass;
    private final byte returnClass;

    public abstract int getSize();

    public final boolean isBaseToken() {
        return false;
    }

    protected AbstractFunctionPtg(int i, int i2, byte[] bArr, int i3) {
        this._numberOfArgs = (byte) i3;
        this._functionIndex = (short) i;
        this.returnClass = (byte) i2;
        this.paramClass = bArr;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(getClass().getName());
        sb.append(" [");
        sb.append(lookupName(this._functionIndex));
        sb.append(" nArgs=");
        sb.append(this._numberOfArgs);
        sb.append("]");
        return sb.toString();
    }

    public final short getFunctionIndex() {
        return this._functionIndex;
    }

    public final int getNumberOfOperands() {
        return this._numberOfArgs;
    }

    public final String getName() {
        return lookupName(this._functionIndex);
    }

    public final boolean isExternalFunction() {
        return this._functionIndex == 255;
    }

    public final String toFormulaString() {
        return getName();
    }

    public String toFormulaString(String[] strArr) {
        StringBuilder sb = new StringBuilder();
        if (isExternalFunction()) {
            sb.append(strArr[0]);
            appendArgs(sb, 1, strArr);
        } else {
            sb.append(getName());
            appendArgs(sb, 0, strArr);
        }
        return sb.toString();
    }

    private static void appendArgs(StringBuilder sb, int i, String[] strArr) {
        sb.append('(');
        for (int i2 = i; i2 < strArr.length; i2++) {
            if (i2 > i) {
                sb.append(',');
            }
            sb.append(strArr[i2]);
        }
        sb.append(")");
    }

    public static final boolean isBuiltInFunctionName(String str) {
        return FunctionMetadataRegistry.lookupIndexByName(str.toUpperCase()) >= 0;
    }

    /* access modifiers changed from: protected */
    public final String lookupName(short s) {
        if (s == 255) {
            return "#external#";
        }
        FunctionMetadata functionByIndex = FunctionMetadataRegistry.getFunctionByIndex(s);
        if (functionByIndex != null) {
            return functionByIndex.getName();
        }
        throw new RuntimeException("bad function index (" + s + ")");
    }

    protected static short lookupIndex(String str) {
        short lookupIndexByName = FunctionMetadataRegistry.lookupIndexByName(str.toUpperCase());
        if (lookupIndexByName < 0) {
            return 255;
        }
        return lookupIndexByName;
    }

    public byte getDefaultOperandClass() {
        return this.returnClass;
    }

    public final byte getParameterClass(int i) {
        byte[] bArr = this.paramClass;
        if (i >= bArr.length) {
            return bArr[bArr.length - 1];
        }
        return bArr[i];
    }
}
