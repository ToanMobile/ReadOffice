package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.formula.Formula;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.util.CellRangeAddress8Bit;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class ArrayRecord extends SharedValueRecordBase {
    private static final int OPT_ALWAYS_RECALCULATE = 1;
    private static final int OPT_CALCULATE_ON_OPEN = 2;
    public static final short sid = 545;
    private int _field3notUsed;
    private Formula _formula;
    private int _options;

    public short getSid() {
        return sid;
    }

    public ArrayRecord(RecordInputStream recordInputStream) {
        super((LittleEndianInput) recordInputStream);
        this._options = recordInputStream.readUShort();
        this._field3notUsed = recordInputStream.readInt();
        this._formula = Formula.read(recordInputStream.readUShort(), recordInputStream, recordInputStream.available());
    }

    public ArrayRecord(Formula formula, CellRangeAddress8Bit cellRangeAddress8Bit) {
        super(cellRangeAddress8Bit);
        this._options = 0;
        this._field3notUsed = 0;
        this._formula = formula;
    }

    public boolean isAlwaysRecalculate() {
        return (this._options & 1) != 0;
    }

    public boolean isCalculateOnOpen() {
        return (this._options & 2) != 0;
    }

    public Ptg[] getFormulaTokens() {
        return this._formula.getTokens();
    }

    /* access modifiers changed from: protected */
    public int getExtraDataSize() {
        return this._formula.getEncodedSize() + 6;
    }

    /* access modifiers changed from: protected */
    public void serializeExtraData(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this._options);
        littleEndianOutput.writeInt(this._field3notUsed);
        this._formula.serialize(littleEndianOutput);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getClass().getName());
        stringBuffer.append(" [ARRAY]\n");
        stringBuffer.append(" range=");
        stringBuffer.append(getRange().toString());
        stringBuffer.append("\n");
        stringBuffer.append(" options=");
        stringBuffer.append(HexDump.shortToHex(this._options));
        stringBuffer.append("\n");
        stringBuffer.append(" notUsed=");
        stringBuffer.append(HexDump.intToHex(this._field3notUsed));
        stringBuffer.append("\n");
        stringBuffer.append(" formula:");
        stringBuffer.append("\n");
        Ptg[] tokens = this._formula.getTokens();
        for (Ptg ptg : tokens) {
            stringBuffer.append(ptg.toString());
            stringBuffer.append(ptg.getRVAType());
            stringBuffer.append("\n");
        }
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
