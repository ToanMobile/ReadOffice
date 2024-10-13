package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.formula.ptg.OperandPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.record.cont.ContinuableRecord;
import com.app.office.fc.hssf.record.cont.ContinuableRecordOutput;
import com.app.office.fc.hssf.usermodel.HSSFRichTextString;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;

public final class TextObjectRecord extends ContinuableRecord {
    private static final int FORMAT_RUN_ENCODED_SIZE = 8;
    public static final short HORIZONTAL_TEXT_ALIGNMENT_CENTERED = 2;
    public static final short HORIZONTAL_TEXT_ALIGNMENT_JUSTIFIED = 4;
    public static final short HORIZONTAL_TEXT_ALIGNMENT_LEFT_ALIGNED = 1;
    public static final short HORIZONTAL_TEXT_ALIGNMENT_RIGHT_ALIGNED = 3;
    private static final BitField HorizontalTextAlignment = BitFieldFactory.getInstance(14);
    public static final short TEXT_ORIENTATION_NONE = 0;
    public static final short TEXT_ORIENTATION_ROT_LEFT = 3;
    public static final short TEXT_ORIENTATION_ROT_RIGHT = 2;
    public static final short TEXT_ORIENTATION_TOP_TO_BOTTOM = 1;
    public static final short VERTICAL_TEXT_ALIGNMENT_BOTTOM = 3;
    public static final short VERTICAL_TEXT_ALIGNMENT_CENTER = 2;
    public static final short VERTICAL_TEXT_ALIGNMENT_JUSTIFY = 4;
    public static final short VERTICAL_TEXT_ALIGNMENT_TOP = 1;
    private static final BitField VerticalTextAlignment = BitFieldFactory.getInstance(112);
    public static final short sid = 438;
    private static final BitField textLocked = BitFieldFactory.getInstance(512);
    private OperandPtg _linkRefPtg;
    private HSSFRichTextString _text;
    private Byte _unknownPostFormulaByte;
    private int _unknownPreFormulaInt;
    private int field_1_options;
    private int field_2_textOrientation;
    private int field_3_reserved4;
    private int field_4_reserved5;
    private int field_5_reserved6;
    private int field_8_reserved7;

    public short getSid() {
        return sid;
    }

    public TextObjectRecord() {
    }

    public TextObjectRecord(RecordInputStream recordInputStream) {
        this.field_1_options = recordInputStream.readUShort();
        this.field_2_textOrientation = recordInputStream.readUShort();
        this.field_3_reserved4 = recordInputStream.readUShort();
        this.field_4_reserved5 = recordInputStream.readUShort();
        this.field_5_reserved6 = recordInputStream.readUShort();
        int readUShort = recordInputStream.readUShort();
        int readUShort2 = recordInputStream.readUShort();
        this.field_8_reserved7 = recordInputStream.readInt();
        if (recordInputStream.remaining() <= 0) {
            this._linkRefPtg = null;
        } else if (recordInputStream.remaining() >= 11) {
            int readUShort3 = recordInputStream.readUShort();
            this._unknownPreFormulaInt = recordInputStream.readInt();
            Ptg[] readTokens = Ptg.readTokens(readUShort3, recordInputStream);
            if (readTokens.length == 1) {
                this._linkRefPtg = (OperandPtg) readTokens[0];
                if (recordInputStream.remaining() > 0) {
                    this._unknownPostFormulaByte = Byte.valueOf(recordInputStream.readByte());
                } else {
                    this._unknownPostFormulaByte = null;
                }
            } else {
                throw new RecordFormatException("Read " + readTokens.length + " tokens but expected exactly 1");
            }
        } else {
            throw new RecordFormatException("Not enough remaining data for a link formula");
        }
        if (recordInputStream.remaining() <= 0) {
            HSSFRichTextString hSSFRichTextString = new HSSFRichTextString(readUShort > 0 ? readRawString(recordInputStream, readUShort) : "");
            this._text = hSSFRichTextString;
            if (readUShort2 > 0) {
                processFontRuns(recordInputStream, hSSFRichTextString, readUShort2);
                return;
            }
            return;
        }
        throw new RecordFormatException("Unused " + recordInputStream.remaining() + " bytes at end of record");
    }

    private static String readRawString(RecordInputStream recordInputStream, int i) {
        boolean z = true;
        if ((recordInputStream.readByte() & 1) != 0) {
            z = false;
        }
        if (z) {
            return recordInputStream.readCompressedUnicode(i);
        }
        return recordInputStream.readUnicodeLEString(i);
    }

    private static void processFontRuns(RecordInputStream recordInputStream, HSSFRichTextString hSSFRichTextString, int i) {
        if (i % 8 == 0) {
            int i2 = i / 8;
            for (int i3 = 0; i3 < i2; i3++) {
                short readShort = recordInputStream.readShort();
                short readShort2 = recordInputStream.readShort();
                recordInputStream.readInt();
                hSSFRichTextString.applyFont((int) readShort, hSSFRichTextString.length(), readShort2);
            }
            return;
        }
        throw new RecordFormatException("Bad format run data length " + i + ")");
    }

    private void serializeTXORecord(ContinuableRecordOutput continuableRecordOutput) {
        continuableRecordOutput.writeShort(this.field_1_options);
        continuableRecordOutput.writeShort(this.field_2_textOrientation);
        continuableRecordOutput.writeShort(this.field_3_reserved4);
        continuableRecordOutput.writeShort(this.field_4_reserved5);
        continuableRecordOutput.writeShort(this.field_5_reserved6);
        continuableRecordOutput.writeShort(this._text.length());
        continuableRecordOutput.writeShort(getFormattingDataLength());
        continuableRecordOutput.writeInt(this.field_8_reserved7);
        OperandPtg operandPtg = this._linkRefPtg;
        if (operandPtg != null) {
            continuableRecordOutput.writeShort(operandPtg.getSize());
            continuableRecordOutput.writeInt(this._unknownPreFormulaInt);
            this._linkRefPtg.write(continuableRecordOutput);
            Byte b = this._unknownPostFormulaByte;
            if (b != null) {
                continuableRecordOutput.writeByte(b.byteValue());
            }
        }
    }

    private void serializeTrailingRecords(ContinuableRecordOutput continuableRecordOutput) {
        continuableRecordOutput.writeContinue();
        continuableRecordOutput.writeStringData(this._text.getString());
        continuableRecordOutput.writeContinue();
        writeFormatData(continuableRecordOutput, this._text);
    }

    /* access modifiers changed from: protected */
    public void serialize(ContinuableRecordOutput continuableRecordOutput) {
        serializeTXORecord(continuableRecordOutput);
        if (this._text.getString().length() > 0) {
            serializeTrailingRecords(continuableRecordOutput);
        }
    }

    private int getFormattingDataLength() {
        if (this._text.length() < 1) {
            return 0;
        }
        return (this._text.numFormattingRuns() + 1) * 8;
    }

    private static void writeFormatData(ContinuableRecordOutput continuableRecordOutput, HSSFRichTextString hSSFRichTextString) {
        int numFormattingRuns = hSSFRichTextString.numFormattingRuns();
        for (int i = 0; i < numFormattingRuns; i++) {
            continuableRecordOutput.writeShort(hSSFRichTextString.getIndexOfFormattingRun(i));
            short fontOfFormattingRun = hSSFRichTextString.getFontOfFormattingRun(i);
            if (fontOfFormattingRun == 0) {
                fontOfFormattingRun = 0;
            }
            continuableRecordOutput.writeShort(fontOfFormattingRun);
            continuableRecordOutput.writeInt(0);
        }
        continuableRecordOutput.writeShort(hSSFRichTextString.length());
        continuableRecordOutput.writeShort(0);
        continuableRecordOutput.writeInt(0);
    }

    public void setHorizontalTextAlignment(int i) {
        this.field_1_options = HorizontalTextAlignment.setValue(this.field_1_options, i);
    }

    public int getHorizontalTextAlignment() {
        return HorizontalTextAlignment.getValue(this.field_1_options);
    }

    public void setVerticalTextAlignment(int i) {
        this.field_1_options = VerticalTextAlignment.setValue(this.field_1_options, i);
    }

    public int getVerticalTextAlignment() {
        return VerticalTextAlignment.getValue(this.field_1_options);
    }

    public void setTextLocked(boolean z) {
        this.field_1_options = textLocked.setBoolean(this.field_1_options, z);
    }

    public boolean isTextLocked() {
        return textLocked.isSet(this.field_1_options);
    }

    public int getTextOrientation() {
        return this.field_2_textOrientation;
    }

    public void setTextOrientation(int i) {
        this.field_2_textOrientation = i;
    }

    public HSSFRichTextString getStr() {
        return this._text;
    }

    public void setStr(HSSFRichTextString hSSFRichTextString) {
        this._text = hSSFRichTextString;
    }

    public Ptg getLinkRefPtg() {
        return this._linkRefPtg;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[TXO]\n");
        stringBuffer.append("    .options        = ");
        stringBuffer.append(HexDump.shortToHex(this.field_1_options));
        stringBuffer.append("\n");
        stringBuffer.append("         .isHorizontal = ");
        stringBuffer.append(getHorizontalTextAlignment());
        stringBuffer.append(10);
        stringBuffer.append("         .isVertical   = ");
        stringBuffer.append(getVerticalTextAlignment());
        stringBuffer.append(10);
        stringBuffer.append("         .textLocked   = ");
        stringBuffer.append(isTextLocked());
        stringBuffer.append(10);
        stringBuffer.append("    .textOrientation= ");
        stringBuffer.append(HexDump.shortToHex(getTextOrientation()));
        stringBuffer.append("\n");
        stringBuffer.append("    .reserved4      = ");
        stringBuffer.append(HexDump.shortToHex(this.field_3_reserved4));
        stringBuffer.append("\n");
        stringBuffer.append("    .reserved5      = ");
        stringBuffer.append(HexDump.shortToHex(this.field_4_reserved5));
        stringBuffer.append("\n");
        stringBuffer.append("    .reserved6      = ");
        stringBuffer.append(HexDump.shortToHex(this.field_5_reserved6));
        stringBuffer.append("\n");
        stringBuffer.append("    .textLength     = ");
        stringBuffer.append(HexDump.shortToHex(this._text.length()));
        stringBuffer.append("\n");
        stringBuffer.append("    .reserved7      = ");
        stringBuffer.append(HexDump.intToHex(this.field_8_reserved7));
        stringBuffer.append("\n");
        stringBuffer.append("    .string = ");
        stringBuffer.append(this._text);
        stringBuffer.append(10);
        for (int i = 0; i < this._text.numFormattingRuns(); i++) {
            stringBuffer.append("    .textrun = ");
            stringBuffer.append(this._text.getFontOfFormattingRun(i));
            stringBuffer.append(10);
        }
        stringBuffer.append("[/TXO]\n");
        return stringBuffer.toString();
    }

    public Object clone() {
        TextObjectRecord textObjectRecord = new TextObjectRecord();
        textObjectRecord._text = this._text;
        textObjectRecord.field_1_options = this.field_1_options;
        textObjectRecord.field_2_textOrientation = this.field_2_textOrientation;
        textObjectRecord.field_3_reserved4 = this.field_3_reserved4;
        textObjectRecord.field_4_reserved5 = this.field_4_reserved5;
        textObjectRecord.field_5_reserved6 = this.field_5_reserved6;
        textObjectRecord.field_8_reserved7 = this.field_8_reserved7;
        textObjectRecord._text = this._text;
        OperandPtg operandPtg = this._linkRefPtg;
        if (operandPtg != null) {
            textObjectRecord._unknownPreFormulaInt = this._unknownPreFormulaInt;
            textObjectRecord._linkRefPtg = operandPtg.copy();
            textObjectRecord._unknownPostFormulaByte = this._unknownPostFormulaByte;
        }
        return textObjectRecord;
    }
}
