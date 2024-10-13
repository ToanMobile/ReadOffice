package com.app.office.fc.hssf.formula.ptg;

import com.app.office.constant.fc.ConstantValueParser;
import com.app.office.constant.fc.ErrorConstant;
import com.app.office.fc.ss.util.NumberToTextConverter;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;
import java.lang.reflect.Array;

public final class ArrayPtg extends Ptg {
    public static final int PLAIN_TOKEN_SIZE = 8;
    private static final int RESERVED_FIELD_LEN = 7;
    public static final byte sid = 32;
    private final Object[] _arrayValues;
    private final int _nColumns;
    private final int _nRows;
    private final int _reserved0Int;
    private final int _reserved1Short;
    private final int _reserved2Byte;

    public byte getDefaultOperandClass() {
        return 64;
    }

    public boolean isBaseToken() {
        return false;
    }

    ArrayPtg(int i, int i2, int i3, int i4, int i5, Object[] objArr) {
        this._reserved0Int = i;
        this._reserved1Short = i2;
        this._reserved2Byte = i3;
        this._nColumns = i4;
        this._nRows = i5;
        this._arrayValues = objArr;
    }

    public ArrayPtg(Object[][] objArr) {
        int length = objArr[0].length;
        int length2 = objArr.length;
        short s = (short) length;
        this._nColumns = s;
        short s2 = (short) length2;
        this._nRows = s2;
        Object[] objArr2 = new Object[(s * s2)];
        for (int i = 0; i < length2; i++) {
            Object[] objArr3 = objArr[i];
            for (int i2 = 0; i2 < length; i2++) {
                objArr2[getValueIndex(i2, i)] = objArr3[i2];
            }
        }
        this._arrayValues = objArr2;
        this._reserved0Int = 0;
        this._reserved1Short = 0;
        this._reserved2Byte = 0;
    }

    public Object[][] getTokenArrayValues() {
        if (this._arrayValues != null) {
            int i = this._nRows;
            int[] iArr = new int[2];
            iArr[1] = this._nColumns;
            iArr[0] = i;
            Object[][] objArr = (Object[][]) Array.newInstance(Object.class, iArr);
            for (int i2 = 0; i2 < this._nRows; i2++) {
                Object[] objArr2 = objArr[i2];
                for (int i3 = 0; i3 < this._nColumns; i3++) {
                    objArr2[i3] = this._arrayValues[getValueIndex(i3, i2)];
                }
            }
            return objArr;
        }
        throw new IllegalStateException("array values not read yet");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("[ArrayPtg]\n");
        stringBuffer.append("nRows = ");
        stringBuffer.append(getRowCount());
        stringBuffer.append("\n");
        stringBuffer.append("nCols = ");
        stringBuffer.append(getColumnCount());
        stringBuffer.append("\n");
        if (this._arrayValues == null) {
            stringBuffer.append("  #values#uninitialised#\n");
        } else {
            stringBuffer.append("  ");
            stringBuffer.append(toFormulaString());
        }
        return stringBuffer.toString();
    }

    /* access modifiers changed from: package-private */
    public int getValueIndex(int i, int i2) {
        int i3;
        if (i < 0 || i >= (i3 = this._nColumns)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Specified colIx (");
            sb.append(i);
            sb.append(") is outside the allowed range (0..");
            sb.append(this._nColumns - 1);
            sb.append(")");
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 >= 0 && i2 < this._nRows) {
            return (i2 * i3) + i;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Specified rowIx (");
            sb2.append(i2);
            sb2.append(") is outside the allowed range (0..");
            sb2.append(this._nRows - 1);
            sb2.append(")");
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public void write(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(getPtgClass() + 32);
        littleEndianOutput.writeInt(this._reserved0Int);
        littleEndianOutput.writeShort(this._reserved1Short);
        littleEndianOutput.writeByte(this._reserved2Byte);
    }

    public int writeTokenValueBytes(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeByte(this._nColumns - 1);
        littleEndianOutput.writeShort(this._nRows - 1);
        ConstantValueParser.encode(littleEndianOutput, this._arrayValues);
        return ConstantValueParser.getEncodedSize(this._arrayValues) + 3;
    }

    public int getRowCount() {
        return this._nRows;
    }

    public int getColumnCount() {
        return this._nColumns;
    }

    public int getSize() {
        return ConstantValueParser.getEncodedSize(this._arrayValues) + 11;
    }

    public String toFormulaString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{");
        for (int i = 0; i < getRowCount(); i++) {
            if (i > 0) {
                stringBuffer.append(";");
            }
            for (int i2 = 0; i2 < getColumnCount(); i2++) {
                if (i2 > 0) {
                    stringBuffer.append(",");
                }
                stringBuffer.append(getConstantText(this._arrayValues[getValueIndex(i2, i)]));
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    private static String getConstantText(Object obj) {
        if (obj == null) {
            throw new RuntimeException("Array item cannot be null");
        } else if (obj instanceof String) {
            return "\"" + ((String) obj) + "\"";
        } else if (obj instanceof Double) {
            return NumberToTextConverter.toText(((Double) obj).doubleValue());
        } else {
            if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue() ? "TRUE" : "FALSE";
            }
            if (obj instanceof ErrorConstant) {
                return ((ErrorConstant) obj).getText();
            }
            throw new IllegalArgumentException("Unexpected constant class (" + obj.getClass().getName() + ")");
        }
    }

    static final class Initial extends Ptg {
        private final int _reserved0;
        private final int _reserved1;
        private final int _reserved2;

        public int getSize() {
            return 8;
        }

        public boolean isBaseToken() {
            return false;
        }

        public Initial(LittleEndianInput littleEndianInput) {
            this._reserved0 = littleEndianInput.readInt();
            this._reserved1 = littleEndianInput.readUShort();
            this._reserved2 = littleEndianInput.readUByte();
        }

        private static RuntimeException invalid() {
            throw new IllegalStateException("This object is a partially initialised tArray, and cannot be used as a Ptg");
        }

        public byte getDefaultOperandClass() {
            throw invalid();
        }

        public String toFormulaString() {
            throw invalid();
        }

        public void write(LittleEndianOutput littleEndianOutput) {
            throw invalid();
        }

        public ArrayPtg finishReading(LittleEndianInput littleEndianInput) {
            int readUByte = littleEndianInput.readUByte() + 1;
            short readShort = (short) (littleEndianInput.readShort() + 1);
            ArrayPtg arrayPtg = new ArrayPtg(this._reserved0, this._reserved1, this._reserved2, readUByte, readShort, ConstantValueParser.parse(littleEndianInput, readShort * readUByte));
            arrayPtg.setClass(getPtgClass());
            return arrayPtg;
        }
    }
}
