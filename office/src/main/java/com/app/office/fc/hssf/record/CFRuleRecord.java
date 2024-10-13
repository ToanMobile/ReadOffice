package com.app.office.fc.hssf.record;

import com.app.office.constant.EventConstant;
import com.app.office.fc.hssf.formula.Formula;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.model.HSSFFormulaParser;
import com.app.office.fc.hssf.record.cf.BorderFormatting;
import com.app.office.fc.hssf.record.cf.FontFormatting;
import com.app.office.fc.hssf.record.cf.PatternFormatting;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.ss.model.XLSModel.ASheet;
import com.app.office.ss.model.XLSModel.AWorkbook;
import com.app.office.ss.model.baseModel.Sheet;

public final class CFRuleRecord extends StandardRecord {
    public static final byte CONDITION_TYPE_CELL_VALUE_IS = 1;
    public static final byte CONDITION_TYPE_FORMULA = 2;
    private static final BitField align = bf(134217728);
    private static final BitField alignHor = bf(1);
    private static final BitField alignIndent = bf(32);
    private static final BitField alignJustLast = bf(16);
    private static final BitField alignRot = bf(8);
    private static final BitField alignShrin = bf(64);
    private static final BitField alignTextDir = bf(Integer.MIN_VALUE);
    private static final BitField alignVer = bf(2);
    private static final BitField alignWrap = bf(4);
    private static final BitField bord = bf(EventConstant.FILE_CREATE_FOLDER_ID);
    private static final BitField bordBlTr = bf(32768);
    private static final BitField bordBot = bf(8192);
    private static final BitField bordLeft = bf(1024);
    private static final BitField bordRight = bf(2048);
    private static final BitField bordTlBr = bf(16384);
    private static final BitField bordTop = bf(4096);
    private static final BitField fmtBlockBits = bf(2080374784);
    private static final BitField font = bf(PagedChannelRandomAccessSource.DEFAULT_TOTAL_BUFSIZE);
    private static final BitField modificationBits = bf(4194303);
    private static final BitField notUsed1 = bf(128);
    private static final BitField notUsed2 = bf(3670016);
    private static final BitField patt = bf(536870912);
    private static final BitField pattBgCol = bf(262144);
    private static final BitField pattCol = bf(131072);
    private static final BitField pattStyle = bf(65536);
    private static final BitField prot = bf(1073741824);
    private static final BitField protHidden = bf(512);
    private static final BitField protLocked = bf(256);
    public static final short sid = 433;
    private static final BitField undocumented = bf(62914560);
    private BorderFormatting _borderFormatting;
    private FontFormatting _fontFormatting;
    private PatternFormatting _patternFormatting;
    private Formula field_17_formula1;
    private Formula field_18_formula2;
    private byte field_1_condition_type;
    private byte field_2_comparison_operator;
    private int field_5_options;
    private short field_6_not_used;

    public static final class ComparisonOperator {
        public static final byte BETWEEN = 1;
        public static final byte EQUAL = 3;
        public static final byte GE = 7;
        public static final byte GT = 5;
        public static final byte LE = 8;
        public static final byte LT = 6;
        public static final byte NOT_BETWEEN = 2;
        public static final byte NOT_EQUAL = 4;
        public static final byte NO_COMPARISON = 0;
    }

    public short getSid() {
        return sid;
    }

    private static BitField bf(int i) {
        return BitFieldFactory.getInstance(i);
    }

    private CFRuleRecord(byte b, byte b2) {
        this.field_1_condition_type = b;
        this.field_2_comparison_operator = b2;
        int value = modificationBits.setValue(this.field_5_options, -1);
        this.field_5_options = value;
        int value2 = fmtBlockBits.setValue(value, 0);
        this.field_5_options = value2;
        this.field_5_options = undocumented.clear(value2);
        this.field_6_not_used = -32766;
        this._fontFormatting = null;
        this._borderFormatting = null;
        this._patternFormatting = null;
        this.field_17_formula1 = Formula.create(Ptg.EMPTY_PTG_ARRAY);
        this.field_18_formula2 = Formula.create(Ptg.EMPTY_PTG_ARRAY);
    }

    private CFRuleRecord(byte b, byte b2, Ptg[] ptgArr, Ptg[] ptgArr2) {
        this(b, b2);
        this.field_17_formula1 = Formula.create(ptgArr);
        this.field_18_formula2 = Formula.create(ptgArr2);
    }

    public static CFRuleRecord create(ASheet aSheet, String str) {
        return new CFRuleRecord((byte) 2, (byte) 0, parseFormula(str, aSheet), (Ptg[]) null);
    }

    public static CFRuleRecord create(ASheet aSheet, byte b, String str, String str2) {
        return new CFRuleRecord((byte) 1, b, parseFormula(str, aSheet), parseFormula(str2, aSheet));
    }

    public CFRuleRecord(RecordInputStream recordInputStream) {
        this.field_1_condition_type = recordInputStream.readByte();
        this.field_2_comparison_operator = recordInputStream.readByte();
        int readUShort = recordInputStream.readUShort();
        int readUShort2 = recordInputStream.readUShort();
        this.field_5_options = recordInputStream.readInt();
        this.field_6_not_used = recordInputStream.readShort();
        if (containsFontFormattingBlock()) {
            this._fontFormatting = new FontFormatting(recordInputStream);
        }
        if (containsBorderFormattingBlock()) {
            this._borderFormatting = new BorderFormatting(recordInputStream);
        }
        if (containsPatternFormattingBlock()) {
            this._patternFormatting = new PatternFormatting(recordInputStream);
        }
        this.field_17_formula1 = Formula.read(readUShort, recordInputStream);
        this.field_18_formula2 = Formula.read(readUShort2, recordInputStream);
    }

    public byte getConditionType() {
        return this.field_1_condition_type;
    }

    public boolean containsFontFormattingBlock() {
        return getOptionFlag(font);
    }

    public void setFontFormatting(FontFormatting fontFormatting) {
        this._fontFormatting = fontFormatting;
        setOptionFlag(fontFormatting != null, font);
    }

    public FontFormatting getFontFormatting() {
        if (containsFontFormattingBlock()) {
            return this._fontFormatting;
        }
        return null;
    }

    public boolean containsAlignFormattingBlock() {
        return getOptionFlag(align);
    }

    public void setAlignFormattingUnchanged() {
        setOptionFlag(false, align);
    }

    public boolean containsBorderFormattingBlock() {
        return getOptionFlag(bord);
    }

    public void setBorderFormatting(BorderFormatting borderFormatting) {
        this._borderFormatting = borderFormatting;
        setOptionFlag(borderFormatting != null, bord);
    }

    public BorderFormatting getBorderFormatting() {
        if (containsBorderFormattingBlock()) {
            return this._borderFormatting;
        }
        return null;
    }

    public boolean containsPatternFormattingBlock() {
        return getOptionFlag(patt);
    }

    public void setPatternFormatting(PatternFormatting patternFormatting) {
        this._patternFormatting = patternFormatting;
        setOptionFlag(patternFormatting != null, patt);
    }

    public PatternFormatting getPatternFormatting() {
        if (containsPatternFormattingBlock()) {
            return this._patternFormatting;
        }
        return null;
    }

    public boolean containsProtectionFormattingBlock() {
        return getOptionFlag(prot);
    }

    public void setProtectionFormattingUnchanged() {
        setOptionFlag(false, prot);
    }

    public void setComparisonOperation(byte b) {
        this.field_2_comparison_operator = b;
    }

    public byte getComparisonOperation() {
        return this.field_2_comparison_operator;
    }

    public int getOptions() {
        return this.field_5_options;
    }

    private boolean isModified(BitField bitField) {
        return !bitField.isSet(this.field_5_options);
    }

    private void setModified(boolean z, BitField bitField) {
        this.field_5_options = bitField.setBoolean(this.field_5_options, !z);
    }

    public boolean isLeftBorderModified() {
        return isModified(bordLeft);
    }

    public void setLeftBorderModified(boolean z) {
        setModified(z, bordLeft);
    }

    public boolean isRightBorderModified() {
        return isModified(bordRight);
    }

    public void setRightBorderModified(boolean z) {
        setModified(z, bordRight);
    }

    public boolean isTopBorderModified() {
        return isModified(bordTop);
    }

    public void setTopBorderModified(boolean z) {
        setModified(z, bordTop);
    }

    public boolean isBottomBorderModified() {
        return isModified(bordBot);
    }

    public void setBottomBorderModified(boolean z) {
        setModified(z, bordBot);
    }

    public boolean isTopLeftBottomRightBorderModified() {
        return isModified(bordTlBr);
    }

    public void setTopLeftBottomRightBorderModified(boolean z) {
        setModified(z, bordTlBr);
    }

    public boolean isBottomLeftTopRightBorderModified() {
        return isModified(bordBlTr);
    }

    public void setBottomLeftTopRightBorderModified(boolean z) {
        setModified(z, bordBlTr);
    }

    public boolean isPatternStyleModified() {
        return isModified(pattStyle);
    }

    public void setPatternStyleModified(boolean z) {
        setModified(z, pattStyle);
    }

    public boolean isPatternColorModified() {
        return isModified(pattCol);
    }

    public void setPatternColorModified(boolean z) {
        setModified(z, pattCol);
    }

    public boolean isPatternBackgroundColorModified() {
        return isModified(pattBgCol);
    }

    public void setPatternBackgroundColorModified(boolean z) {
        setModified(z, pattBgCol);
    }

    private boolean getOptionFlag(BitField bitField) {
        return bitField.isSet(this.field_5_options);
    }

    private void setOptionFlag(boolean z, BitField bitField) {
        this.field_5_options = bitField.setBoolean(this.field_5_options, z);
    }

    public Ptg[] getParsedExpression1() {
        return this.field_17_formula1.getTokens();
    }

    public void setParsedExpression1(Ptg[] ptgArr) {
        this.field_17_formula1 = Formula.create(ptgArr);
    }

    public Ptg[] getParsedExpression2() {
        return Formula.getTokens(this.field_18_formula2);
    }

    public void setParsedExpression2(Ptg[] ptgArr) {
        this.field_18_formula2 = Formula.create(ptgArr);
    }

    private static int getFormulaSize(Formula formula) {
        return formula.getEncodedTokenSize();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        int formulaSize = getFormulaSize(this.field_17_formula1);
        int formulaSize2 = getFormulaSize(this.field_18_formula2);
        littleEndianOutput.writeByte(this.field_1_condition_type);
        littleEndianOutput.writeByte(this.field_2_comparison_operator);
        littleEndianOutput.writeShort(formulaSize);
        littleEndianOutput.writeShort(formulaSize2);
        littleEndianOutput.writeInt(this.field_5_options);
        littleEndianOutput.writeShort(this.field_6_not_used);
        if (containsFontFormattingBlock()) {
            littleEndianOutput.write(this._fontFormatting.getRawRecord());
        }
        if (containsBorderFormattingBlock()) {
            this._borderFormatting.serialize(littleEndianOutput);
        }
        if (containsPatternFormattingBlock()) {
            this._patternFormatting.serialize(littleEndianOutput);
        }
        this.field_17_formula1.serializeTokens(littleEndianOutput);
        this.field_18_formula2.serializeTokens(littleEndianOutput);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        int i = 0;
        int length = (containsFontFormattingBlock() ? this._fontFormatting.getRawRecord().length : 0) + 12 + (containsBorderFormattingBlock() ? 8 : 0);
        if (containsPatternFormattingBlock()) {
            i = 4;
        }
        return length + i + getFormulaSize(this.field_17_formula1) + getFormulaSize(this.field_18_formula2);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[CFRULE]\n");
        stringBuffer.append("    .condition_type   =" + this.field_1_condition_type);
        stringBuffer.append("    OPTION FLAGS=0x" + Integer.toHexString(getOptions()));
        return stringBuffer.toString();
    }

    public Object clone() {
        CFRuleRecord cFRuleRecord = new CFRuleRecord(this.field_1_condition_type, this.field_2_comparison_operator);
        cFRuleRecord.field_5_options = this.field_5_options;
        cFRuleRecord.field_6_not_used = this.field_6_not_used;
        if (containsFontFormattingBlock()) {
            cFRuleRecord._fontFormatting = (FontFormatting) this._fontFormatting.clone();
        }
        if (containsBorderFormattingBlock()) {
            cFRuleRecord._borderFormatting = (BorderFormatting) this._borderFormatting.clone();
        }
        if (containsPatternFormattingBlock()) {
            cFRuleRecord._patternFormatting = (PatternFormatting) this._patternFormatting.clone();
        }
        cFRuleRecord.field_17_formula1 = this.field_17_formula1.copy();
        cFRuleRecord.field_18_formula2 = this.field_17_formula1.copy();
        return cFRuleRecord;
    }

    private static Ptg[] parseFormula(String str, ASheet aSheet) {
        if (str == null) {
            return null;
        }
        return HSSFFormulaParser.parse(str, (AWorkbook) aSheet.getWorkbook(), 0, ((AWorkbook) aSheet.getWorkbook()).getSheetIndex((Sheet) aSheet));
    }
}
