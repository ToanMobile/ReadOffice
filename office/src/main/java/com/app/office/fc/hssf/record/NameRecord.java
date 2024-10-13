package com.app.office.fc.hssf.record;

import com.app.office.fc.hssf.formula.Formula;
import com.app.office.fc.hssf.formula.ptg.Area3DPtg;
import com.app.office.fc.hssf.formula.ptg.Ptg;
import com.app.office.fc.hssf.formula.ptg.Ref3DPtg;
import com.app.office.fc.hssf.record.cont.ContinuableRecord;
import com.app.office.fc.hssf.record.cont.ContinuableRecordOutput;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianByteArrayInputStream;
import com.app.office.fc.util.StringUtil;

public final class NameRecord extends ContinuableRecord {
    public static final byte BUILTIN_AUTO_ACTIVATE = 10;
    public static final byte BUILTIN_AUTO_CLOSE = 3;
    public static final byte BUILTIN_AUTO_DEACTIVATE = 11;
    public static final byte BUILTIN_AUTO_OPEN = 2;
    public static final byte BUILTIN_CONSOLIDATE_AREA = 1;
    public static final byte BUILTIN_CRITERIA = 5;
    public static final byte BUILTIN_DATABASE = 4;
    public static final byte BUILTIN_DATA_FORM = 9;
    public static final byte BUILTIN_FILTER_DB = 13;
    public static final byte BUILTIN_PRINT_AREA = 6;
    public static final byte BUILTIN_PRINT_TITLE = 7;
    public static final byte BUILTIN_RECORDER = 8;
    public static final byte BUILTIN_SHEET_TITLE = 12;
    public static final short sid = 24;
    private boolean field_11_nameIsMultibyte;
    private byte field_12_built_in_code;
    private String field_12_name_text;
    private Formula field_13_name_definition;
    private String field_14_custom_menu_text;
    private String field_15_description_text;
    private String field_16_help_topic_text;
    private String field_17_status_bar_text;
    private short field_1_option_flag;
    private byte field_2_keyboard_shortcut;
    private short field_5_externSheetIndex_plus1;
    private int field_6_sheetNumber;

    private static String translateBuiltInName(byte b) {
        switch (b) {
            case 1:
                return "Consolidate_Area";
            case 2:
                return "Auto_Open";
            case 3:
                return "Auto_Close";
            case 4:
                return "Database";
            case 5:
                return "Criteria";
            case 6:
                return "Print_Area";
            case 7:
                return "Print_Titles";
            case 8:
                return "Recorder";
            case 9:
                return "Data_Form";
            case 10:
                return "Auto_Activate";
            case 11:
                return "Auto_Deactivate";
            case 12:
                return "Sheet_Title";
            case 13:
                return "_FilterDatabase";
            default:
                return "Unknown";
        }
    }

    public short getSid() {
        return 24;
    }

    private static final class Option {
        public static final int OPT_BINDATA = 4096;
        public static final int OPT_BUILTIN = 32;
        public static final int OPT_COMMAND_NAME = 4;
        public static final int OPT_COMPLEX = 16;
        public static final int OPT_FUNCTION_NAME = 2;
        public static final int OPT_HIDDEN_NAME = 1;
        public static final int OPT_MACRO = 8;

        public static final boolean isFormula(int i) {
            return (i & 15) == 0;
        }

        private Option() {
        }
    }

    public NameRecord() {
        this.field_13_name_definition = Formula.create(Ptg.EMPTY_PTG_ARRAY);
        this.field_12_name_text = "";
        this.field_14_custom_menu_text = "";
        this.field_15_description_text = "";
        this.field_16_help_topic_text = "";
        this.field_17_status_bar_text = "";
    }

    public NameRecord(byte b, int i) {
        this();
        this.field_12_built_in_code = b;
        setOptionFlag((short) (this.field_1_option_flag | 32));
        this.field_6_sheetNumber = i;
    }

    public void setOptionFlag(short s) {
        this.field_1_option_flag = s;
    }

    public void setKeyboardShortcut(byte b) {
        this.field_2_keyboard_shortcut = b;
    }

    public int getSheetNumber() {
        return this.field_6_sheetNumber;
    }

    public byte getFnGroup() {
        return (byte) ((this.field_1_option_flag & 4032) >> 4);
    }

    public void setSheetNumber(int i) {
        this.field_6_sheetNumber = i;
    }

    public void setNameText(String str) {
        this.field_12_name_text = str;
        this.field_11_nameIsMultibyte = StringUtil.hasMultibyte(str);
    }

    public void setCustomMenuText(String str) {
        this.field_14_custom_menu_text = str;
    }

    public void setDescriptionText(String str) {
        this.field_15_description_text = str;
    }

    public void setHelpTopicText(String str) {
        this.field_16_help_topic_text = str;
    }

    public void setStatusBarText(String str) {
        this.field_17_status_bar_text = str;
    }

    public short getOptionFlag() {
        return this.field_1_option_flag;
    }

    public byte getKeyboardShortcut() {
        return this.field_2_keyboard_shortcut;
    }

    private int getNameTextLength() {
        if (isBuiltInName()) {
            return 1;
        }
        return this.field_12_name_text.length();
    }

    public boolean isHiddenName() {
        return (this.field_1_option_flag & 1) != 0;
    }

    public void setHidden(boolean z) {
        if (z) {
            this.field_1_option_flag = (short) (this.field_1_option_flag | 1);
        } else {
            this.field_1_option_flag = (short) (this.field_1_option_flag & -2);
        }
    }

    public boolean isFunctionName() {
        return (this.field_1_option_flag & 2) != 0;
    }

    public void setFunction(boolean z) {
        if (z) {
            this.field_1_option_flag = (short) (this.field_1_option_flag | 2);
        } else {
            this.field_1_option_flag = (short) (this.field_1_option_flag & -3);
        }
    }

    public boolean hasFormula() {
        return Option.isFormula(this.field_1_option_flag) && this.field_13_name_definition.getEncodedTokenSize() > 0;
    }

    public boolean isCommandName() {
        return (this.field_1_option_flag & 4) != 0;
    }

    public boolean isMacro() {
        return (this.field_1_option_flag & 8) != 0;
    }

    public boolean isComplexFunction() {
        return (this.field_1_option_flag & 16) != 0;
    }

    public boolean isBuiltInName() {
        return (this.field_1_option_flag & 32) != 0;
    }

    public String getNameText() {
        return isBuiltInName() ? translateBuiltInName(getBuiltInName()) : this.field_12_name_text;
    }

    public byte getBuiltInName() {
        return this.field_12_built_in_code;
    }

    public Ptg[] getNameDefinition() {
        return this.field_13_name_definition.getTokens();
    }

    public void setNameDefinition(Ptg[] ptgArr) {
        this.field_13_name_definition = Formula.create(ptgArr);
    }

    public String getCustomMenuText() {
        return this.field_14_custom_menu_text;
    }

    public String getDescriptionText() {
        return this.field_15_description_text;
    }

    public String getHelpTopicText() {
        return this.field_16_help_topic_text;
    }

    public String getStatusBarText() {
        return this.field_17_status_bar_text;
    }

    public void serialize(ContinuableRecordOutput continuableRecordOutput) {
        int length = this.field_14_custom_menu_text.length();
        int length2 = this.field_15_description_text.length();
        int length3 = this.field_16_help_topic_text.length();
        int length4 = this.field_17_status_bar_text.length();
        continuableRecordOutput.writeShort(getOptionFlag());
        continuableRecordOutput.writeByte(getKeyboardShortcut());
        continuableRecordOutput.writeByte(getNameTextLength());
        continuableRecordOutput.writeShort(this.field_13_name_definition.getEncodedTokenSize());
        continuableRecordOutput.writeShort(this.field_5_externSheetIndex_plus1);
        continuableRecordOutput.writeShort(this.field_6_sheetNumber);
        continuableRecordOutput.writeByte(length);
        continuableRecordOutput.writeByte(length2);
        continuableRecordOutput.writeByte(length3);
        continuableRecordOutput.writeByte(length4);
        continuableRecordOutput.writeByte(this.field_11_nameIsMultibyte ? 1 : 0);
        if (isBuiltInName()) {
            continuableRecordOutput.writeByte(this.field_12_built_in_code);
        } else {
            String str = this.field_12_name_text;
            if (this.field_11_nameIsMultibyte) {
                StringUtil.putUnicodeLE(str, continuableRecordOutput);
            } else {
                StringUtil.putCompressedUnicode(str, continuableRecordOutput);
            }
        }
        this.field_13_name_definition.serializeTokens(continuableRecordOutput);
        this.field_13_name_definition.serializeArrayConstantData(continuableRecordOutput);
        StringUtil.putCompressedUnicode(getCustomMenuText(), continuableRecordOutput);
        StringUtil.putCompressedUnicode(getDescriptionText(), continuableRecordOutput);
        StringUtil.putCompressedUnicode(getHelpTopicText(), continuableRecordOutput);
        StringUtil.putCompressedUnicode(getStatusBarText(), continuableRecordOutput);
    }

    private int getNameRawSize() {
        if (isBuiltInName()) {
            return 1;
        }
        int length = this.field_12_name_text.length();
        return this.field_11_nameIsMultibyte ? length * 2 : length;
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return getNameRawSize() + 13 + this.field_14_custom_menu_text.length() + this.field_15_description_text.length() + this.field_16_help_topic_text.length() + this.field_17_status_bar_text.length() + this.field_13_name_definition.getEncodedSize();
    }

    public int getExternSheetNumber() {
        if (this.field_13_name_definition.getEncodedSize() < 1) {
            return 0;
        }
        Ptg ptg = this.field_13_name_definition.getTokens()[0];
        if (ptg.getClass() == Area3DPtg.class) {
            return ((Area3DPtg) ptg).getExternSheetIndex();
        }
        if (ptg.getClass() == Ref3DPtg.class) {
            return ((Ref3DPtg) ptg).getExternSheetIndex();
        }
        return 0;
    }

    public NameRecord(RecordInputStream recordInputStream) {
        LittleEndianByteArrayInputStream littleEndianByteArrayInputStream = new LittleEndianByteArrayInputStream(recordInputStream.readAllContinuedRemainder());
        this.field_1_option_flag = littleEndianByteArrayInputStream.readShort();
        this.field_2_keyboard_shortcut = littleEndianByteArrayInputStream.readByte();
        int readUByte = littleEndianByteArrayInputStream.readUByte();
        short readShort = littleEndianByteArrayInputStream.readShort();
        this.field_5_externSheetIndex_plus1 = littleEndianByteArrayInputStream.readShort();
        this.field_6_sheetNumber = littleEndianByteArrayInputStream.readUShort();
        int readUByte2 = littleEndianByteArrayInputStream.readUByte();
        int readUByte3 = littleEndianByteArrayInputStream.readUByte();
        int readUByte4 = littleEndianByteArrayInputStream.readUByte();
        int readUByte5 = littleEndianByteArrayInputStream.readUByte();
        this.field_11_nameIsMultibyte = littleEndianByteArrayInputStream.readByte() != 0;
        if (isBuiltInName()) {
            this.field_12_built_in_code = littleEndianByteArrayInputStream.readByte();
        } else if (this.field_11_nameIsMultibyte) {
            this.field_12_name_text = StringUtil.readUnicodeLE(littleEndianByteArrayInputStream, readUByte);
        } else {
            this.field_12_name_text = StringUtil.readCompressedUnicode(littleEndianByteArrayInputStream, readUByte);
        }
        this.field_13_name_definition = Formula.read(readShort, littleEndianByteArrayInputStream, littleEndianByteArrayInputStream.available() - (((readUByte2 + readUByte3) + readUByte4) + readUByte5));
        this.field_14_custom_menu_text = StringUtil.readCompressedUnicode(littleEndianByteArrayInputStream, readUByte2);
        this.field_15_description_text = StringUtil.readCompressedUnicode(littleEndianByteArrayInputStream, readUByte3);
        this.field_16_help_topic_text = StringUtil.readCompressedUnicode(littleEndianByteArrayInputStream, readUByte4);
        this.field_17_status_bar_text = StringUtil.readCompressedUnicode(littleEndianByteArrayInputStream, readUByte5);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[NAME]\n");
        stringBuffer.append("    .option flags           = ");
        stringBuffer.append(HexDump.shortToHex(this.field_1_option_flag));
        stringBuffer.append("\n");
        stringBuffer.append("    .keyboard shortcut      = ");
        stringBuffer.append(HexDump.byteToHex(this.field_2_keyboard_shortcut));
        stringBuffer.append("\n");
        stringBuffer.append("    .length of the name     = ");
        stringBuffer.append(getNameTextLength());
        stringBuffer.append("\n");
        stringBuffer.append("    .extSheetIx(1-based, 0=Global)= ");
        stringBuffer.append(this.field_5_externSheetIndex_plus1);
        stringBuffer.append("\n");
        stringBuffer.append("    .sheetTabIx             = ");
        stringBuffer.append(this.field_6_sheetNumber);
        stringBuffer.append("\n");
        stringBuffer.append("    .Menu text length       = ");
        stringBuffer.append(this.field_14_custom_menu_text.length());
        stringBuffer.append("\n");
        stringBuffer.append("    .Description text length= ");
        stringBuffer.append(this.field_15_description_text.length());
        stringBuffer.append("\n");
        stringBuffer.append("    .Help topic text length = ");
        stringBuffer.append(this.field_16_help_topic_text.length());
        stringBuffer.append("\n");
        stringBuffer.append("    .Status bar text length = ");
        stringBuffer.append(this.field_17_status_bar_text.length());
        stringBuffer.append("\n");
        stringBuffer.append("    .NameIsMultibyte        = ");
        stringBuffer.append(this.field_11_nameIsMultibyte);
        stringBuffer.append("\n");
        stringBuffer.append("    .Name (Unicode text)    = ");
        stringBuffer.append(getNameText());
        stringBuffer.append("\n");
        Ptg[] tokens = this.field_13_name_definition.getTokens();
        stringBuffer.append("    .Formula (nTokens=");
        stringBuffer.append(tokens.length);
        stringBuffer.append("):");
        stringBuffer.append("\n");
        for (Ptg ptg : tokens) {
            stringBuffer.append("       " + ptg.toString());
            stringBuffer.append(ptg.getRVAType());
            stringBuffer.append("\n");
        }
        stringBuffer.append("    .Menu text       = ");
        stringBuffer.append(this.field_14_custom_menu_text);
        stringBuffer.append("\n");
        stringBuffer.append("    .Description text= ");
        stringBuffer.append(this.field_15_description_text);
        stringBuffer.append("\n");
        stringBuffer.append("    .Help topic text = ");
        stringBuffer.append(this.field_16_help_topic_text);
        stringBuffer.append("\n");
        stringBuffer.append("    .Status bar text = ");
        stringBuffer.append(this.field_17_status_bar_text);
        stringBuffer.append("\n");
        stringBuffer.append("[/NAME]\n");
        return stringBuffer.toString();
    }
}
