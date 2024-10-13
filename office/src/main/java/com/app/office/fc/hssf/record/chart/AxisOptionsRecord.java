package com.app.office.fc.hssf.record.chart;

import com.app.office.fc.hssf.record.RecordInputStream;
import com.app.office.fc.hssf.record.StandardRecord;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.BitFieldFactory;
import com.app.office.fc.util.HexDump;
import com.app.office.fc.util.LittleEndianOutput;

public final class AxisOptionsRecord extends StandardRecord {
    private static final BitField defaultBase = BitFieldFactory.getInstance(32);
    private static final BitField defaultCross = BitFieldFactory.getInstance(64);
    private static final BitField defaultDateSettings = BitFieldFactory.getInstance(128);
    private static final BitField defaultMajor = BitFieldFactory.getInstance(4);
    private static final BitField defaultMaximum = BitFieldFactory.getInstance(2);
    private static final BitField defaultMinimum = BitFieldFactory.getInstance(1);
    private static final BitField defaultMinorUnit = BitFieldFactory.getInstance(8);
    private static final BitField isDate = BitFieldFactory.getInstance(16);
    public static final short sid = 4194;
    private short field_1_minimumCategory;
    private short field_2_maximumCategory;
    private short field_3_majorUnitValue;
    private short field_4_majorUnit;
    private short field_5_minorUnitValue;
    private short field_6_minorUnit;
    private short field_7_baseUnit;
    private short field_8_crossingPoint;
    private short field_9_options;

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 18;
    }

    public short getSid() {
        return sid;
    }

    public AxisOptionsRecord() {
    }

    public AxisOptionsRecord(RecordInputStream recordInputStream) {
        this.field_1_minimumCategory = recordInputStream.readShort();
        this.field_2_maximumCategory = recordInputStream.readShort();
        this.field_3_majorUnitValue = recordInputStream.readShort();
        this.field_4_majorUnit = recordInputStream.readShort();
        this.field_5_minorUnitValue = recordInputStream.readShort();
        this.field_6_minorUnit = recordInputStream.readShort();
        this.field_7_baseUnit = recordInputStream.readShort();
        this.field_8_crossingPoint = recordInputStream.readShort();
        this.field_9_options = recordInputStream.readShort();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[AXCEXT]\n");
        stringBuffer.append("    .minimumCategory      = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getMinimumCategory()));
        stringBuffer.append(" (");
        stringBuffer.append(getMinimumCategory());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .maximumCategory      = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getMaximumCategory()));
        stringBuffer.append(" (");
        stringBuffer.append(getMaximumCategory());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .majorUnitValue       = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getMajorUnitValue()));
        stringBuffer.append(" (");
        stringBuffer.append(getMajorUnitValue());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .majorUnit            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getMajorUnit()));
        stringBuffer.append(" (");
        stringBuffer.append(getMajorUnit());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .minorUnitValue       = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getMinorUnitValue()));
        stringBuffer.append(" (");
        stringBuffer.append(getMinorUnitValue());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .minorUnit            = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getMinorUnit()));
        stringBuffer.append(" (");
        stringBuffer.append(getMinorUnit());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .baseUnit             = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getBaseUnit()));
        stringBuffer.append(" (");
        stringBuffer.append(getBaseUnit());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .crossingPoint        = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getCrossingPoint()));
        stringBuffer.append(" (");
        stringBuffer.append(getCrossingPoint());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("    .options              = ");
        stringBuffer.append("0x");
        stringBuffer.append(HexDump.toHex(getOptions()));
        stringBuffer.append(" (");
        stringBuffer.append(getOptions());
        stringBuffer.append(" )");
        stringBuffer.append(System.getProperty("line.separator"));
        stringBuffer.append("         .defaultMinimum           = ");
        stringBuffer.append(isDefaultMinimum());
        stringBuffer.append(10);
        stringBuffer.append("         .defaultMaximum           = ");
        stringBuffer.append(isDefaultMaximum());
        stringBuffer.append(10);
        stringBuffer.append("         .defaultMajor             = ");
        stringBuffer.append(isDefaultMajor());
        stringBuffer.append(10);
        stringBuffer.append("         .defaultMinorUnit         = ");
        stringBuffer.append(isDefaultMinorUnit());
        stringBuffer.append(10);
        stringBuffer.append("         .isDate                   = ");
        stringBuffer.append(isIsDate());
        stringBuffer.append(10);
        stringBuffer.append("         .defaultBase              = ");
        stringBuffer.append(isDefaultBase());
        stringBuffer.append(10);
        stringBuffer.append("         .defaultCross             = ");
        stringBuffer.append(isDefaultCross());
        stringBuffer.append(10);
        stringBuffer.append("         .defaultDateSettings      = ");
        stringBuffer.append(isDefaultDateSettings());
        stringBuffer.append(10);
        stringBuffer.append("[/AXCEXT]\n");
        return stringBuffer.toString();
    }

    public void serialize(LittleEndianOutput littleEndianOutput) {
        littleEndianOutput.writeShort(this.field_1_minimumCategory);
        littleEndianOutput.writeShort(this.field_2_maximumCategory);
        littleEndianOutput.writeShort(this.field_3_majorUnitValue);
        littleEndianOutput.writeShort(this.field_4_majorUnit);
        littleEndianOutput.writeShort(this.field_5_minorUnitValue);
        littleEndianOutput.writeShort(this.field_6_minorUnit);
        littleEndianOutput.writeShort(this.field_7_baseUnit);
        littleEndianOutput.writeShort(this.field_8_crossingPoint);
        littleEndianOutput.writeShort(this.field_9_options);
    }

    public Object clone() {
        AxisOptionsRecord axisOptionsRecord = new AxisOptionsRecord();
        axisOptionsRecord.field_1_minimumCategory = this.field_1_minimumCategory;
        axisOptionsRecord.field_2_maximumCategory = this.field_2_maximumCategory;
        axisOptionsRecord.field_3_majorUnitValue = this.field_3_majorUnitValue;
        axisOptionsRecord.field_4_majorUnit = this.field_4_majorUnit;
        axisOptionsRecord.field_5_minorUnitValue = this.field_5_minorUnitValue;
        axisOptionsRecord.field_6_minorUnit = this.field_6_minorUnit;
        axisOptionsRecord.field_7_baseUnit = this.field_7_baseUnit;
        axisOptionsRecord.field_8_crossingPoint = this.field_8_crossingPoint;
        axisOptionsRecord.field_9_options = this.field_9_options;
        return axisOptionsRecord;
    }

    public short getMinimumCategory() {
        return this.field_1_minimumCategory;
    }

    public void setMinimumCategory(short s) {
        this.field_1_minimumCategory = s;
    }

    public short getMaximumCategory() {
        return this.field_2_maximumCategory;
    }

    public void setMaximumCategory(short s) {
        this.field_2_maximumCategory = s;
    }

    public short getMajorUnitValue() {
        return this.field_3_majorUnitValue;
    }

    public void setMajorUnitValue(short s) {
        this.field_3_majorUnitValue = s;
    }

    public short getMajorUnit() {
        return this.field_4_majorUnit;
    }

    public void setMajorUnit(short s) {
        this.field_4_majorUnit = s;
    }

    public short getMinorUnitValue() {
        return this.field_5_minorUnitValue;
    }

    public void setMinorUnitValue(short s) {
        this.field_5_minorUnitValue = s;
    }

    public short getMinorUnit() {
        return this.field_6_minorUnit;
    }

    public void setMinorUnit(short s) {
        this.field_6_minorUnit = s;
    }

    public short getBaseUnit() {
        return this.field_7_baseUnit;
    }

    public void setBaseUnit(short s) {
        this.field_7_baseUnit = s;
    }

    public short getCrossingPoint() {
        return this.field_8_crossingPoint;
    }

    public void setCrossingPoint(short s) {
        this.field_8_crossingPoint = s;
    }

    public short getOptions() {
        return this.field_9_options;
    }

    public void setOptions(short s) {
        this.field_9_options = s;
    }

    public void setDefaultMinimum(boolean z) {
        this.field_9_options = defaultMinimum.setShortBoolean(this.field_9_options, z);
    }

    public boolean isDefaultMinimum() {
        return defaultMinimum.isSet(this.field_9_options);
    }

    public void setDefaultMaximum(boolean z) {
        this.field_9_options = defaultMaximum.setShortBoolean(this.field_9_options, z);
    }

    public boolean isDefaultMaximum() {
        return defaultMaximum.isSet(this.field_9_options);
    }

    public void setDefaultMajor(boolean z) {
        this.field_9_options = defaultMajor.setShortBoolean(this.field_9_options, z);
    }

    public boolean isDefaultMajor() {
        return defaultMajor.isSet(this.field_9_options);
    }

    public void setDefaultMinorUnit(boolean z) {
        this.field_9_options = defaultMinorUnit.setShortBoolean(this.field_9_options, z);
    }

    public boolean isDefaultMinorUnit() {
        return defaultMinorUnit.isSet(this.field_9_options);
    }

    public void setIsDate(boolean z) {
        this.field_9_options = isDate.setShortBoolean(this.field_9_options, z);
    }

    public boolean isIsDate() {
        return isDate.isSet(this.field_9_options);
    }

    public void setDefaultBase(boolean z) {
        this.field_9_options = defaultBase.setShortBoolean(this.field_9_options, z);
    }

    public boolean isDefaultBase() {
        return defaultBase.isSet(this.field_9_options);
    }

    public void setDefaultCross(boolean z) {
        this.field_9_options = defaultCross.setShortBoolean(this.field_9_options, z);
    }

    public boolean isDefaultCross() {
        return defaultCross.isSet(this.field_9_options);
    }

    public void setDefaultDateSettings(boolean z) {
        this.field_9_options = defaultDateSettings.setShortBoolean(this.field_9_options, z);
    }

    public boolean isDefaultDateSettings() {
        return defaultDateSettings.isSet(this.field_9_options);
    }
}
