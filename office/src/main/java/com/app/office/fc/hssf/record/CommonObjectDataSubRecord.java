package com.app.office.fc.hssf.record;

import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianInput;
import com.app.office.fc.util.LittleEndianOutput;

public final class CommonObjectDataSubRecord extends SubRecord {
    public static final short OBJECT_TYPE_ARC = 4;
    public static final short OBJECT_TYPE_BUTTON = 7;
    public static final short OBJECT_TYPE_CHART = 5;
    public static final short OBJECT_TYPE_CHECKBOX = 11;
    public static final short OBJECT_TYPE_COMBO_BOX = 20;
    public static final short OBJECT_TYPE_COMMENT = 25;
    public static final short OBJECT_TYPE_DIALOG_BOX = 15;
    public static final short OBJECT_TYPE_EDIT_BOX = 13;
    public static final short OBJECT_TYPE_GROUP = 0;
    public static final short OBJECT_TYPE_GROUP_BOX = 19;
    public static final short OBJECT_TYPE_LABEL = 14;
    public static final short OBJECT_TYPE_LINE = 1;
    public static final short OBJECT_TYPE_LIST_BOX = 18;
    public static final short OBJECT_TYPE_MICROSOFT_OFFICE_DRAWING = 30;
    public static final short OBJECT_TYPE_OPTION_BUTTON = 12;
    public static final short OBJECT_TYPE_OVAL = 3;
    public static final short OBJECT_TYPE_PICTURE = 8;
    public static final short OBJECT_TYPE_POLYGON = 9;
    public static final short OBJECT_TYPE_RECTANGLE = 2;
    public static final short OBJECT_TYPE_RESERVED1 = 10;
    public static final short OBJECT_TYPE_RESERVED2 = 21;
    public static final short OBJECT_TYPE_RESERVED3 = 22;
    public static final short OBJECT_TYPE_RESERVED4 = 23;
    public static final short OBJECT_TYPE_RESERVED5 = 24;
    public static final short OBJECT_TYPE_RESERVED6 = 26;
    public static final short OBJECT_TYPE_RESERVED7 = 27;
    public static final short OBJECT_TYPE_RESERVED8 = 28;
    public static final short OBJECT_TYPE_RESERVED9 = 29;
    public static final short OBJECT_TYPE_SCROLL_BAR = 17;
    public static final short OBJECT_TYPE_SPINNER = 16;
    public static final short OBJECT_TYPE_TEXT = 6;
    private static final BitField autofill = BitFieldFactory.getInstance(8192);
    private static final BitField autoline = BitFieldFactory.getInstance(16384);
    private static final BitField locked = BitFieldFactory.getInstance(1);
    private static final BitField printable = BitFieldFactory.getInstance(16);
    public static final short sid = 21;
    private short field_1_objectType;
    private int field_2_objectId;
    private short field_3_option;
    private int field_4_reserved1;
    private int field_5_reserved2;
    private int field_6_reserved3;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 18;
    }

    public short getSid() {
        return 21;
    }

    public CommonObjectDataSubRecord() {
    }

    public CommonObjectDataSubRecord(LittleEndianInput littleEndianInput, int i) {
        if (i == 18) {
            this.field_1_objectType = littleEndianInput.readShort();
            this.field_2_objectId = littleEndianInput.readUShort();
            this.field_3_option = littleEndianInput.readShort();
            this.field_4_reserved1 = littleEndianInput.readInt();
            this.field_5_reserved2 = littleEndianInput.readInt();
            this.field_6_reserved3 = littleEndianInput.readInt();
            return;
        }
        throw new RecordFormatException("Expected size 18 but got (" + i + ")");
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[ftCmo]\n");
        stringBuffer.append("    .objectType           = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getObjectType()));
        stringBuffer.append(" (");
        stringBuffer.append(getObjectType());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .objectId             = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getObjectId()));
        stringBuffer.append(" (");
        stringBuffer.append(getObjectId());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .option               = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getOption()));
        stringBuffer.append(" (");
        stringBuffer.append(getOption());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .locked                   = ");
        stringBuffer.append(isLocked());
        stringBuffer.append(10);
        stringBuffer.append("         .printable                = ");
        stringBuffer.append(isPrintable());
        stringBuffer.append(10);
        stringBuffer.append("         .autofill                 = ");
        stringBuffer.append(isAutofill());
        stringBuffer.append(10);
        stringBuffer.append("         .autoline                 = ");
        stringBuffer.append(isAutoline());
        stringBuffer.append(10);
        stringBuffer.append("    .reserved1            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getReserved1()));
        stringBuffer.append(" (");
        stringBuffer.append(getReserved1());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .reserved2            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getReserved2()));
        stringBuffer.append(" (");
        stringBuffer.append(getReserved2());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .reserved3            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getReserved3()));
        stringBuffer.append(" (");
        stringBuffer.append(getReserved3());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("[/ftCmo]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(21);
        littleEndianOutput.writeShort(getDataSize());
        littleEndianOutput.writeShort(this.field_1_objectType);
        littleEndianOutput.writeShort(this.field_2_objectId);
        littleEndianOutput.writeShort(this.field_3_option);
        littleEndianOutput.writeInt(this.field_4_reserved1);
        littleEndianOutput.writeInt(this.field_5_reserved2);
        littleEndianOutput.writeInt(this.field_6_reserved3);
    }

    public Object clone() {
        CommonObjectDataSubRecord commonObjectDataSubRecord = new CommonObjectDataSubRecord();
        commonObjectDataSubRecord.field_1_objectType = this.field_1_objectType;
        commonObjectDataSubRecord.field_2_objectId = this.field_2_objectId;
        commonObjectDataSubRecord.field_3_option = this.field_3_option;
        commonObjectDataSubRecord.field_4_reserved1 = this.field_4_reserved1;
        commonObjectDataSubRecord.field_5_reserved2 = this.field_5_reserved2;
        commonObjectDataSubRecord.field_6_reserved3 = this.field_6_reserved3;
        return commonObjectDataSubRecord;
    }

    public short getObjectType() {
        return this.field_1_objectType;
    }

    public void setObjectType(short s) {
        this.field_1_objectType = s;
    }

    public int getObjectId() {
        return this.field_2_objectId;
    }

    public void setObjectId(int i) {
        this.field_2_objectId = i;
    }

    public short getOption() {
        return this.field_3_option;
    }

    public void setOption(short s) {
        this.field_3_option = s;
    }

    public int getReserved1() {
        return this.field_4_reserved1;
    }

    public void setReserved1(int i) {
        this.field_4_reserved1 = i;
    }

    public int getReserved2() {
        return this.field_5_reserved2;
    }

    public void setReserved2(int i) {
        this.field_5_reserved2 = i;
    }

    public int getReserved3() {
        return this.field_6_reserved3;
    }

    public void setReserved3(int i) {
        this.field_6_reserved3 = i;
    }

    public void setLocked(boolean z) {
        this.field_3_option = locked.setShortBoolean(this.field_3_option, z);
    }

    public boolean isLocked() {
        return locked.isSet(this.field_3_option);
    }

    public void setPrintable(boolean z) {
        this.field_3_option = printable.setShortBoolean(this.field_3_option, z);
    }

    public boolean isPrintable() {
        return printable.isSet(this.field_3_option);
    }

    public void setAutofill(boolean z) {
        this.field_3_option = autofill.setShortBoolean(this.field_3_option, z);
    }

    public boolean isAutofill() {
        return autofill.isSet(this.field_3_option);
    }

    public void setAutoline(boolean z) {
        this.field_3_option = autoline.setShortBoolean(this.field_3_option, z);
    }

    public boolean isAutoline() {
        return autoline.isSet(this.field_3_option);
    }
}
