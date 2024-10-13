package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.formula.Formula;
import com.app.office.fc.hssf.formula.SharedFormula;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.util.CellRangeAddress8Bit;
import com.app.office.fc.ss.SpreadsheetVersion;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class SharedFormulaRecord extends SharedValueRecordBase {
    public static final short sid = 1212;
    private int field_5_reserved;
    private Formula field_7_parsed_expr;

    public short getSid() {
        return sid;
    }

    public SharedFormulaRecord() {
        this(new CellRangeAddress8Bit(0, 0, 0, 0));
    }

    private SharedFormulaRecord(CellRangeAddress8Bit cellRangeAddress8Bit) {
        super(cellRangeAddress8Bit);
        this.field_7_parsed_expr = Formula.create(Ptg.EMPTY_PTG_ARRAY);
    }

    public SharedFormulaRecord(RecordInputStream recordInputStream) {
        super((LittleEndianInput) recordInputStream);
        this.field_5_reserved = recordInputStream.readShort();
        this.field_7_parsed_expr = Formula.read(recordInputStream.readShort(), recordInputStream, recordInputStream.available());
    }

    /* access modifiers changed from: protected */
    public void serializeExtraData(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_5_reserved);
        this.field_7_parsed_expr.serialize(littleEndianOutput);
    }

    /* access modifiers changed from: protected */
    public int getExtraDataSize() {
        return this.field_7_parsed_expr.getEncodedSize() + 2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[SHARED FORMULA (");
        stringBuffer.append(HexDump.intToHex(1212));
        stringBuffer.append("]\n");
        stringBuffer.append("    .range      = ");
        stringBuffer.append(getRange().toString());
        stringBuffer.append("\n");
        stringBuffer.append("    .reserved    = ");
        stringBuffer.append(HexDump.shortToHex(this.field_5_reserved));
        stringBuffer.append("\n");
        Ptg[] tokens = this.field_7_parsed_expr.getTokens();
        for (int i = 0; i < tokens.length; i++) {
            stringBuffer.append("Formula[");
            stringBuffer.append(i);
            stringBuffer.append("]");
            Ptg ptg = tokens[i];
            stringBuffer.append(ptg.toString());
            stringBuffer.append(ptg.getRVAType());
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/SHARED FORMULA]\n");
        return stringBuffer.toString();
    }

    public Ptg[] getFormulaTokens(FormulaRecord formulaRecord) {
        int row = formulaRecord.getRow();
        short column = formulaRecord.getColumn();
        if (isInRange(row, column)) {
            return new SharedFormula(SpreadsheetVersion.EXCEL97).convertSharedFormulas(this.field_7_parsed_expr.getTokens(), row, column);
        }
        throw new RuntimeException("Shared Formula Conversion: Coding Error");
    }

    public Object clone() {
        SharedFormulaRecord sharedFormulaRecord = new SharedFormulaRecord(getRange());
        sharedFormulaRecord.field_5_reserved = this.field_5_reserved;
        sharedFormulaRecord.field_7_parsed_expr = this.field_7_parsed_expr.copy();
        return sharedFormulaRecord;
    }

    public boolean isFormulaSame(SharedFormulaRecord sharedFormulaRecord) {
        return this.field_7_parsed_expr.isSame(sharedFormulaRecord.field_7_parsed_expr);
    }
}
