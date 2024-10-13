package com.app.office.fc.hssf.record;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;
import com.app.office.fc.util.StringUtil;

public final class StyleRecord extends StandardRecord {
    private static final BitField isBuiltinFlag = BitFieldFactory.getInstance(32768);
    public static final short sid = 659;
    private static final BitField styleIndexMask = BitFieldFactory.getInstance(4095);
    private int field_1_xf_index;
    private int field_2_builtin_style;
    private int field_3_outline_style_level;
    private boolean field_3_stringHasMultibyte;
    private String field_4_name;

    public short getSid() {
        return sid;
    }

    public StyleRecord() {
        this.field_1_xf_index = isBuiltinFlag.set(this.field_1_xf_index);
    }

    public StyleRecord(RecordInputStream recordInputStream) {
        this.field_1_xf_index = recordInputStream.readShort();
        if (isBuiltin()) {
            this.field_2_builtin_style = recordInputStream.readByte();
            this.field_3_outline_style_level = recordInputStream.readByte();
            return;
        }
        short readShort = recordInputStream.readShort();
        boolean z = true;
        if (recordInputStream.remaining() >= 1) {
            z = recordInputStream.readByte() == 0 ? false : z;
            this.field_3_stringHasMultibyte = z;
            if (z) {
                this.field_4_name = StringUtil.readUnicodeLE(recordInputStream, readShort);
            } else {
                this.field_4_name = StringUtil.readCompressedUnicode(recordInputStream, readShort);
            }
        } else if (readShort == 0) {
            this.field_4_name = "";
        } else {
            throw new RecordFormatException("Ran out of data reading style record");
        }
    }

    public void setXFIndex(int i) {
        this.field_1_xf_index = styleIndexMask.setValue(this.field_1_xf_index, i);
    }

    public int getXFIndex() {
        return styleIndexMask.getValue(this.field_1_xf_index);
    }

    public void setName(String str) {
        this.field_4_name = str;
        this.field_3_stringHasMultibyte = StringUtil.hasMultibyte(str);
        this.field_1_xf_index = isBuiltinFlag.clear(this.field_1_xf_index);
    }

    public void setBuiltinStyle(int i) {
        this.field_1_xf_index = isBuiltinFlag.set(this.field_1_xf_index);
        this.field_2_builtin_style = i;
    }

    public void setOutlineStyleLevel(int i) {
        this.field_3_outline_style_level = i & 255;
    }

    public boolean isBuiltin() {
        return isBuiltinFlag.isSet(this.field_1_xf_index);
    }

    public String getName() {
        return this.field_4_name;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[STYLE]\n");
        stringBuffer.append("    .xf_index_raw =");
        stringBuffer.append(HexDump.shortToHex(this.field_1_xf_index));
        stringBuffer.append("\n");
        stringBuffer.append("        .type     =");
        stringBuffer.append(isBuiltin() ? "built-in" : "user-defined");
        stringBuffer.append("\n");
        stringBuffer.append("        .xf_index =");
        stringBuffer.append(HexDump.shortToHex(getXFIndex()));
        stringBuffer.append("\n");
        if (isBuiltin()) {
            stringBuffer.append("    .builtin_style=");
            stringBuffer.append(HexDump.byteToHex(this.field_2_builtin_style));
            stringBuffer.append("\n");
            stringBuffer.append("    .outline_level=");
            stringBuffer.append(HexDump.byteToHex(this.field_3_outline_style_level));
            stringBuffer.append("\n");
        } else {
            stringBuffer.append("    .name        =");
            stringBuffer.append(getName());
            stringBuffer.append("\n");
        }
        stringBuffer.append("[/STYLE]\n");
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        if (isBuiltin()) {
            return 4;
        }
        return (this.field_4_name.length() * (this.field_3_stringHasMultibyte ? 2 : 1)) + 5;
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_xf_index);
        if (isBuiltin()) {
            littleEndianOutput.writeByte(this.field_2_builtin_style);
            littleEndianOutput.writeByte(this.field_3_outline_style_level);
            return;
        }
        littleEndianOutput.writeShort(this.field_4_name.length());
        littleEndianOutput.writeByte(this.field_3_stringHasMultibyte ? 1 : 0);
        if (this.field_3_stringHasMultibyte) {
            StringUtil.putUnicodeLE(getName(), littleEndianOutput);
        } else {
            StringUtil.putCompressedUnicode(getName(), littleEndianOutput);
        }
    }
}
