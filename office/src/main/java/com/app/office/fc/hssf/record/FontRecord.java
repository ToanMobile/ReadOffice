package com.app.office.fc.hssf.record;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class FontRecord extends StandardRecord {
    public static final short SS_NONE = 0;
    public static final short SS_SUB = 2;
    public static final short SS_SUPER = 1;
    public static final byte U_DOUBLE = 2;
    public static final byte U_DOUBLE_ACCOUNTING = 34;
    public static final byte U_NONE = 0;
    public static final byte U_SINGLE = 1;
    public static final byte U_SINGLE_ACCOUNTING = 33;
    private static final BitField italic = BitFieldFactory.getInstance(2);
    private static final BitField macoutline = BitFieldFactory.getInstance(16);
    private static final BitField macshadow = BitFieldFactory.getInstance(32);
    public static final short sid = 49;
    private static final BitField strikeout = BitFieldFactory.getInstance(8);
    private String field_11_font_name;
    private short field_1_font_height;
    private short field_2_attributes;
    private short field_3_color_palette_index;
    private short field_4_bold_weight;
    private short field_5_super_sub_script;
    private byte field_6_underline;
    private byte field_7_family;
    private byte field_8_charset;
    private byte field_9_zero = 0;

    public short getSid() {
        return 49;
    }

    public FontRecord() {
    }

    public FontRecord(RecordInputStream recordInputStream) {
        this.field_1_font_height = recordInputStream.readShort();
        this.field_2_attributes = recordInputStream.readShort();
        this.field_3_color_palette_index = recordInputStream.readShort();
        this.field_4_bold_weight = recordInputStream.readShort();
        this.field_5_super_sub_script = recordInputStream.readShort();
        this.field_6_underline = recordInputStream.readByte();
        this.field_7_family = recordInputStream.readByte();
        this.field_8_charset = recordInputStream.readByte();
        this.field_9_zero = recordInputStream.readByte();
        int readUByte = recordInputStream.readUByte();
        int readUByte2 = recordInputStream.readUByte();
        if (readUByte <= 0) {
            this.field_11_font_name = "";
        } else if (readUByte2 == 0) {
            this.field_11_font_name = recordInputStream.readCompressedUnicode(readUByte);
        } else {
            this.field_11_font_name = recordInputStream.readUnicodeLEString(readUByte);
        }
    }

    public void setFontHeight(short s) {
        this.field_1_font_height = s;
    }

    public void setAttributes(short s) {
        this.field_2_attributes = s;
    }

    public void setItalic(boolean z) {
        this.field_2_attributes = italic.setShortBoolean(this.field_2_attributes, z);
    }

    public void setStrikeout(boolean z) {
        this.field_2_attributes = strikeout.setShortBoolean(this.field_2_attributes, z);
    }

    public void setMacoutline(boolean z) {
        this.field_2_attributes = macoutline.setShortBoolean(this.field_2_attributes, z);
    }

    public void setMacshadow(boolean z) {
        this.field_2_attributes = macshadow.setShortBoolean(this.field_2_attributes, z);
    }

    public void setColorPaletteIndex(short s) {
        this.field_3_color_palette_index = s;
    }

    public void setBoldWeight(short s) {
        this.field_4_bold_weight = s;
    }

    public void setSuperSubScript(short s) {
        this.field_5_super_sub_script = s;
    }

    public void setUnderline(byte b) {
        this.field_6_underline = b;
    }

    public void setFamily(byte b) {
        this.field_7_family = b;
    }

    public void setCharset(byte b) {
        this.field_8_charset = b;
    }

    public void setFontName(String str) {
        this.field_11_font_name = str;
    }

    public short getFontHeight() {
        return this.field_1_font_height;
    }

    public short getAttributes() {
        return this.field_2_attributes;
    }

    public boolean isItalic() {
        return italic.isSet(this.field_2_attributes);
    }

    public boolean isStruckout() {
        return strikeout.isSet(this.field_2_attributes);
    }

    public boolean isMacoutlined() {
        return macoutline.isSet(this.field_2_attributes);
    }

    public boolean isMacshadowed() {
        return macshadow.isSet(this.field_2_attributes);
    }

    public short getColorPaletteIndex() {
        return this.field_3_color_palette_index;
    }

    public short getBoldWeight() {
        return this.field_4_bold_weight;
    }

    public short getSuperSubScript() {
        return this.field_5_super_sub_script;
    }

    public byte getUnderline() {
        return this.field_6_underline;
    }

    public byte getFamily() {
        return this.field_7_family;
    }

    public byte getCharset() {
        return this.field_8_charset;
    }

    public String getFontName() {
        return this.field_11_font_name;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FONT]\n");
        stringBuffer.append("    .fontheight    = ");
        stringBuffer.append(HexDump.shortToHex(getFontHeight()));
        stringBuffer.append("\n");
        stringBuffer.append("    .attributes    = ");
        stringBuffer.append(HexDump.shortToHex(getAttributes()));
        stringBuffer.append("\n");
        stringBuffer.append("       .italic     = ");
        stringBuffer.append(isItalic());
        stringBuffer.append("\n");
        stringBuffer.append("       .strikout   = ");
        stringBuffer.append(isStruckout());
        stringBuffer.append("\n");
        stringBuffer.append("       .macoutlined= ");
        stringBuffer.append(isMacoutlined());
        stringBuffer.append("\n");
        stringBuffer.append("       .macshadowed= ");
        stringBuffer.append(isMacshadowed());
        stringBuffer.append("\n");
        stringBuffer.append("    .colorpalette  = ");
        stringBuffer.append(HexDump.shortToHex(getColorPaletteIndex()));
        stringBuffer.append("\n");
        stringBuffer.append("    .boldweight    = ");
        stringBuffer.append(HexDump.shortToHex(getBoldWeight()));
        stringBuffer.append("\n");
        stringBuffer.append("    .supersubscript= ");
        stringBuffer.append(HexDump.shortToHex(getSuperSubScript()));
        stringBuffer.append("\n");
        stringBuffer.append("    .underline     = ");
        stringBuffer.append(HexDump.byteToHex(getUnderline()));
        stringBuffer.append("\n");
        stringBuffer.append("    .family        = ");
        stringBuffer.append(HexDump.byteToHex(getFamily()));
        stringBuffer.append("\n");
        stringBuffer.append("    .charset       = ");
        stringBuffer.append(HexDump.byteToHex(getCharset()));
        stringBuffer.append("\n");
        stringBuffer.append("    .fontname      = ");
        stringBuffer.append(getFontName());
        stringBuffer.append("\n");
        stringBuffer.append("[/FONT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(getFontHeight());
        littleEndianOutput.writeShort(getAttributes());
        littleEndianOutput.writeShort(getColorPaletteIndex());
        littleEndianOutput.writeShort(getBoldWeight());
        littleEndianOutput.writeShort(getSuperSubScript());
        littleEndianOutput.writeByte(getUnderline());
        littleEndianOutput.writeByte(getFamily());
        littleEndianOutput.writeByte(getCharset());
        littleEndianOutput.writeByte(this.field_9_zero);
        int length = this.field_11_font_name.length();
        littleEndianOutput.writeByte(length);
        boolean hasMultibyte = StringUtil.hasMultibyte(this.field_11_font_name);
        littleEndianOutput.writeByte(hasMultibyte ? 1 : 0);
        if (length <= 0) {
            return;
        }
        if (hasMultibyte) {
            StringUtil.putUnicodeLE(this.field_11_font_name, littleEndianOutput);
        } else {
            StringUtil.putCompressedUnicode(this.field_11_font_name, littleEndianOutput);
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        int length = this.field_11_font_name.length();
        int i = 1;
        if (length < 1) {
            return 16;
        }
        if (StringUtil.hasMultibyte(this.field_11_font_name)) {
            i = 2;
        }
        return (length * i) + 16;
    }

    public void cloneStyleFrom(FontRecord fontRecord) {
        this.field_1_font_height = fontRecord.field_1_font_height;
        this.field_2_attributes = fontRecord.field_2_attributes;
        this.field_3_color_palette_index = fontRecord.field_3_color_palette_index;
        this.field_4_bold_weight = fontRecord.field_4_bold_weight;
        this.field_5_super_sub_script = fontRecord.field_5_super_sub_script;
        this.field_6_underline = fontRecord.field_6_underline;
        this.field_7_family = fontRecord.field_7_family;
        this.field_8_charset = fontRecord.field_8_charset;
        this.field_9_zero = fontRecord.field_9_zero;
        this.field_11_font_name = fontRecord.field_11_font_name;
    }

    public int hashCode() {
        int i;
        String str = this.field_11_font_name;
        if (str == null) {
            i = 0;
        } else {
            i = str.hashCode();
        }
        return ((((((((((((((((((i + 31) * 31) + this.field_1_font_height) * 31) + this.field_2_attributes) * 31) + this.field_3_color_palette_index) * 31) + this.field_4_bold_weight) * 31) + this.field_5_super_sub_script) * 31) + this.field_6_underline) * 31) + this.field_7_family) * 31) + this.field_8_charset) * 31) + this.field_9_zero;
    }

    public boolean sameProperties(FontRecord fontRecord) {
        return this.field_1_font_height == fontRecord.field_1_font_height && this.field_2_attributes == fontRecord.field_2_attributes && this.field_3_color_palette_index == fontRecord.field_3_color_palette_index && this.field_4_bold_weight == fontRecord.field_4_bold_weight && this.field_5_super_sub_script == fontRecord.field_5_super_sub_script && this.field_6_underline == fontRecord.field_6_underline && this.field_7_family == fontRecord.field_7_family && this.field_8_charset == fontRecord.field_8_charset && this.field_9_zero == fontRecord.field_9_zero && this.field_11_font_name.equals(fontRecord.field_11_font_name);
    }
}
