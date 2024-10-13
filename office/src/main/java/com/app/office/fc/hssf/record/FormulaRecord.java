package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.formula.Formula;
import com.app.office.fc.hssf.formula.eval.ErrorEval;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class FormulaRecord extends CellRecord {
    private static int FIXED_SIZE = 14;
    private static final BitField alwaysCalc = BitFieldFactory.getInstance(1);
    private static final BitField calcOnLoad = BitFieldFactory.getInstance(2);
    private static final BitField sharedFormula = BitFieldFactory.getInstance(8);
    public static final short sid = 6;
    private double field_4_value;
    private short field_5_options;
    private int field_6_zero;
    private Formula field_8_parsed_expr;
    private SpecialCachedValue specialCachedValue;

    /* access modifiers changed from: protected */
    public String getRecordName() {
        return "FORMULA";
    }

    public short getSid() {
        return 6;
    }

    private static final class SpecialCachedValue {
        private static final long BIT_MARKER = -281474976710656L;
        public static final int BOOLEAN = 1;
        private static final int DATA_INDEX = 2;
        public static final int EMPTY = 3;
        public static final int ERROR_CODE = 2;
        public static final int STRING = 0;
        private static final int VARIABLE_DATA_LENGTH = 6;
        private final byte[] _variableData;

        private SpecialCachedValue(byte[] bArr) {
            this._variableData = bArr;
        }

        public int getTypeCode() {
            return this._variableData[0];
        }

        public static SpecialCachedValue create(long j) {
            if ((j & BIT_MARKER) != BIT_MARKER) {
                return null;
            }
            byte[] bArr = new byte[6];
            for (int i = 0; i < 6; i++) {
                bArr[i] = (byte) ((int) j);
                j >>= 8;
            }
            byte b = bArr[0];
            if (b == 0 || b == 1 || b == 2 || b == 3) {
                return new SpecialCachedValue(bArr);
            }
            throw new RecordFormatException("Bad special value code (" + bArr[0] + ")");
        }

        public void serialize(LittleEndianOutput littleEndianOutput) {
            littleEndianOutput.write(this._variableData);
            littleEndianOutput.writeShort(65535);
        }

        public String formatDebugString() {
            return formatValue() + ' ' + HexDump.toHex(this._variableData);
        }

        private String formatValue() {
            int typeCode = getTypeCode();
            if (typeCode == 0) {
                return "<string>";
            }
            if (typeCode == 1) {
                return getDataValue() == 0 ? "FALSE" : "TRUE";
            }
            if (typeCode == 2) {
                return ErrorEval.getText(getDataValue());
            }
            if (typeCode == 3) {
                return "<empty>";
            }
            return "#error(type=" + typeCode + ")#";
        }

        private int getDataValue() {
            return this._variableData[2];
        }

        public static SpecialCachedValue createCachedEmptyValue() {
            return create(3, 0);
        }

        public static SpecialCachedValue createForString() {
            return create(0, 0);
        }

        public static SpecialCachedValue createCachedBoolean(boolean z) {
            return create(1, z ? 1 : 0);
        }

        public static SpecialCachedValue createCachedErrorCode(int i) {
            return create(2, i);
        }

        private static SpecialCachedValue create(int i, int i2) {
            return new SpecialCachedValue(new byte[]{(byte) i, 0, (byte) i2, 0, 0, 0});
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(getClass().getName());
            stringBuffer.append('[');
            stringBuffer.append(formatValue());
            stringBuffer.append(']');
            return stringBuffer.toString();
        }

        public int getValueType() {
            int typeCode = getTypeCode();
            if (typeCode == 0) {
                return 1;
            }
            if (typeCode == 1) {
                return 4;
            }
            if (typeCode == 2) {
                return 5;
            }
            if (typeCode == 3) {
                return 1;
            }
            throw new IllegalStateException("Unexpected type id (" + typeCode + ")");
        }

        public boolean getBooleanValue() {
            if (getTypeCode() != 1) {
                throw new IllegalStateException("Not a boolean cached value - " + formatValue());
            } else if (getDataValue() != 0) {
                return true;
            } else {
                return false;
            }
        }

        public int getErrorValue() {
            if (getTypeCode() == 2) {
                return getDataValue();
            }
            throw new IllegalStateException("Not an error cached value - " + formatValue());
        }
    }

    public FormulaRecord() {
        this.field_8_parsed_expr = Formula.create(Ptg.EMPTY_PTG_ARRAY);
    }

    public FormulaRecord(RecordInputStream recordInputStream) {
        super(recordInputStream);
        long readLong = recordInputStream.readLong();
        this.field_5_options = recordInputStream.readShort();
        SpecialCachedValue create = SpecialCachedValue.create(readLong);
        this.specialCachedValue = create;
        if (create == null) {
            this.field_4_value = Double.longBitsToDouble(readLong);
        }
        this.field_6_zero = recordInputStream.readInt();
        this.field_8_parsed_expr = Formula.read(recordInputStream.readShort(), recordInputStream, recordInputStream.available());
    }

    public void setValue(double d) {
        this.field_4_value = d;
        this.specialCachedValue = null;
    }

    public void setCachedResultTypeEmptyString() {
        this.specialCachedValue = SpecialCachedValue.createCachedEmptyValue();
    }

    public void setCachedResultTypeString() {
        this.specialCachedValue = SpecialCachedValue.createForString();
    }

    public void setCachedResultErrorCode(int i) {
        this.specialCachedValue = SpecialCachedValue.createCachedErrorCode(i);
    }

    public void setCachedResultBoolean(boolean z) {
        this.specialCachedValue = SpecialCachedValue.createCachedBoolean(z);
    }

    public boolean hasCachedResultString() {
        SpecialCachedValue specialCachedValue2 = this.specialCachedValue;
        if (specialCachedValue2 != null && specialCachedValue2.getTypeCode() == 0) {
            return true;
        }
        return false;
    }

    public int getCachedResultType() {
        SpecialCachedValue specialCachedValue2 = this.specialCachedValue;
        if (specialCachedValue2 == null) {
            return 0;
        }
        return specialCachedValue2.getValueType();
    }

    public boolean getCachedBooleanValue() {
        return this.specialCachedValue.getBooleanValue();
    }

    public int getCachedErrorValue() {
        return this.specialCachedValue.getErrorValue();
    }

    public void setOptions(short s) {
        this.field_5_options = s;
    }

    public double getValue() {
        return this.field_4_value;
    }

    public short getOptions() {
        return this.field_5_options;
    }

    public boolean isSharedFormula() {
        return sharedFormula.isSet(this.field_5_options);
    }

    public void setSharedFormula(boolean z) {
        this.field_5_options = sharedFormula.setShortBoolean(this.field_5_options, z);
    }

    public boolean isAlwaysCalc() {
        return alwaysCalc.isSet(this.field_5_options);
    }

    public void setAlwaysCalc(boolean z) {
        this.field_5_options = alwaysCalc.setShortBoolean(this.field_5_options, z);
    }

    public boolean isCalcOnLoad() {
        return calcOnLoad.isSet(this.field_5_options);
    }

    public void setCalcOnLoad(boolean z) {
        this.field_5_options = calcOnLoad.setShortBoolean(this.field_5_options, z);
    }

    public Ptg[] getParsedExpression() {
        return this.field_8_parsed_expr.getTokens();
    }

    public Formula getFormula() {
        return this.field_8_parsed_expr;
    }

    public void setParsedExpression(Ptg[] ptgArr) {
        this.field_8_parsed_expr = Formula.create(ptgArr);
    }

    /* access modifiers changed from: protected */
    public int getValueDataSize() {
        return FIXED_SIZE + this.field_8_parsed_expr.getEncodedSize();
    }

    /* access modifiers changed from: protected */
    public void serializeValue(LittleEndianOutput littleEndianOutput) {
        SpecialCachedValue specialCachedValue2 = this.specialCachedValue;
        if (specialCachedValue2 == null) {
            littleEndianOutput.writeDouble(this.field_4_value);
        } else {
            specialCachedValue2.serialize(littleEndianOutput);
        }
        littleEndianOutput.writeShort(getOptions());
        littleEndianOutput.writeInt(this.field_6_zero);
        this.field_8_parsed_expr.serialize(littleEndianOutput);
    }

    /* access modifiers changed from: protected */
    public void appendValueText(StringBuilder sb) {
        sb.append("  .value\t = ");
        SpecialCachedValue specialCachedValue2 = this.specialCachedValue;
        if (specialCachedValue2 == null) {
            sb.append(this.field_4_value);
            sb.append("\n");
        } else {
            sb.append(specialCachedValue2.formatDebugString());
            sb.append("\n");
        }
        sb.append("  .options   = ");
        sb.append(HexDump.shortToHex(getOptions()));
        sb.append("\n");
        sb.append("    .alwaysCalc= ");
        sb.append(isAlwaysCalc());
        sb.append("\n");
        sb.append("    .calcOnLoad= ");
        sb.append(isCalcOnLoad());
        sb.append("\n");
        sb.append("    .shared    = ");
        sb.append(isSharedFormula());
        sb.append("\n");
        sb.append("  .zero      = ");
        sb.append(HexDump.intToHex(this.field_6_zero));
        sb.append("\n");
        Ptg[] tokens = this.field_8_parsed_expr.getTokens();
        for (int i = 0; i < tokens.length; i++) {
            if (i > 0) {
                sb.append("\n");
            }
            sb.append("    Ptg[");
            sb.append(i);
            sb.append("]=");
            Ptg ptg = tokens[i];
            sb.append(ptg.toString());
            sb.append(ptg.getRVAType());
        }
    }

    public Object clone() {
        FormulaRecord formulaRecord = new FormulaRecord();
        copyBaseFields(formulaRecord);
        formulaRecord.field_4_value = this.field_4_value;
        formulaRecord.field_5_options = this.field_5_options;
        formulaRecord.field_6_zero = this.field_6_zero;
        formulaRecord.field_8_parsed_expr = this.field_8_parsed_expr;
        formulaRecord.specialCachedValue = this.specialCachedValue;
        return formulaRecord;
    }
}
