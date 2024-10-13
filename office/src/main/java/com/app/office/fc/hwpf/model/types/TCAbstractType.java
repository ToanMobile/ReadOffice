package com.app.office.fc.hwpf.model.types;

import com.app.office.fc.hwpf.model.HDFType;
import com.app.office.fc.hwpf.usermodel.BorderCode;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;

@Internal
public abstract class TCAbstractType implements HDFType {
    private static BitField fBackward = new BitField(8);
    private static BitField fFirstMerged = new BitField(1);
    private static BitField fFitText = new BitField(4096);
    private static BitField fMerged = new BitField(2);
    private static BitField fNoWrap = new BitField(8192);
    private static BitField fRotateFont = new BitField(16);
    private static BitField fUnused = new BitField(49152);
    private static BitField fVertMerge = new BitField(32);
    private static BitField fVertRestart = new BitField(64);
    private static BitField fVertical = new BitField(4);
    private static BitField ftsWidth = new BitField(3584);
    private static BitField vertAlign = new BitField(384);
    protected byte field_10_ftsCellPaddingRight;
    protected short field_11_wCellSpacingLeft;
    protected short field_12_wCellSpacingTop;
    protected short field_13_wCellSpacingBottom;
    protected short field_14_wCellSpacingRight;
    protected byte field_15_ftsCellSpacingLeft;
    protected byte field_16_ftsCellSpacingTop;
    protected byte field_17_ftsCellSpacingBottom;
    protected byte field_18_ftsCellSpacingRight;
    protected BorderCode field_19_brcTop;
    protected short field_1_rgf;
    protected BorderCode field_20_brcLeft;
    protected BorderCode field_21_brcBottom;
    protected BorderCode field_22_brcRight;
    protected short field_2_wWidth;
    protected short field_3_wCellPaddingLeft;
    protected short field_4_wCellPaddingTop;
    protected short field_5_wCellPaddingBottom;
    protected short field_6_wCellPaddingRight;
    protected byte field_7_ftsCellPaddingLeft;
    protected byte field_8_ftsCellPaddingTop;
    protected byte field_9_ftsCellPaddingBottom;

    public int getSize() {
        return 48;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[TC]\n");
        stringBuffer.append("    .rgf                  = ");
        stringBuffer.append(" (");
        stringBuffer.append(getRgf());
        stringBuffer.append(" )\n");
        stringBuffer.append("         .fFirstMerged             = ");
        stringBuffer.append(isFFirstMerged());
        stringBuffer.append(10);
        stringBuffer.append("         .fMerged                  = ");
        stringBuffer.append(isFMerged());
        stringBuffer.append(10);
        stringBuffer.append("         .fVertical                = ");
        stringBuffer.append(isFVertical());
        stringBuffer.append(10);
        stringBuffer.append("         .fBackward                = ");
        stringBuffer.append(isFBackward());
        stringBuffer.append(10);
        stringBuffer.append("         .fRotateFont              = ");
        stringBuffer.append(isFRotateFont());
        stringBuffer.append(10);
        stringBuffer.append("         .fVertMerge               = ");
        stringBuffer.append(isFVertMerge());
        stringBuffer.append(10);
        stringBuffer.append("         .fVertRestart             = ");
        stringBuffer.append(isFVertRestart());
        stringBuffer.append(10);
        stringBuffer.append("         .vertAlign                = ");
        stringBuffer.append(getVertAlign());
        stringBuffer.append(10);
        stringBuffer.append("         .ftsWidth                 = ");
        stringBuffer.append(getFtsWidth());
        stringBuffer.append(10);
        stringBuffer.append("         .fFitText                 = ");
        stringBuffer.append(isFFitText());
        stringBuffer.append(10);
        stringBuffer.append("         .fNoWrap                  = ");
        stringBuffer.append(isFNoWrap());
        stringBuffer.append(10);
        stringBuffer.append("         .fUnused                  = ");
        stringBuffer.append(getFUnused());
        stringBuffer.append(10);
        stringBuffer.append("    .wWidth               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWWidth());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingLeft     = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingTop      = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingBottom   = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellPaddingRight    = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellPaddingRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingLeft   = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingTop    = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingBottom = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellPaddingRight  = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellPaddingRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingLeft     = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingTop      = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingBottom   = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .wCellSpacingRight    = ");
        stringBuffer.append(" (");
        stringBuffer.append(getWCellSpacingRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingLeft   = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingTop    = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingBottom = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .ftsCellSpacingRight  = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFtsCellSpacingRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .brcTop               = ");
        stringBuffer.append(" (");
        stringBuffer.append(getBrcTop());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .brcLeft              = ");
        stringBuffer.append(" (");
        stringBuffer.append(getBrcLeft());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .brcBottom            = ");
        stringBuffer.append(" (");
        stringBuffer.append(getBrcBottom());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .brcRight             = ");
        stringBuffer.append(" (");
        stringBuffer.append(getBrcRight());
        stringBuffer.append(" )\n");
        stringBuffer.append("[/TC]\n");
        return stringBuffer.toString();
    }

    public short getRgf() {
        return this.field_1_rgf;
    }

    public void setRgf(short s) {
        this.field_1_rgf = s;
    }

    public short getWWidth() {
        return this.field_2_wWidth;
    }

    public void setWWidth(short s) {
        this.field_2_wWidth = s;
    }

    public short getWCellPaddingLeft() {
        return this.field_3_wCellPaddingLeft;
    }

    public void setWCellPaddingLeft(short s) {
        this.field_3_wCellPaddingLeft = s;
    }

    public short getWCellPaddingTop() {
        return this.field_4_wCellPaddingTop;
    }

    public void setWCellPaddingTop(short s) {
        this.field_4_wCellPaddingTop = s;
    }

    public short getWCellPaddingBottom() {
        return this.field_5_wCellPaddingBottom;
    }

    public void setWCellPaddingBottom(short s) {
        this.field_5_wCellPaddingBottom = s;
    }

    public short getWCellPaddingRight() {
        return this.field_6_wCellPaddingRight;
    }

    public void setWCellPaddingRight(short s) {
        this.field_6_wCellPaddingRight = s;
    }

    public byte getFtsCellPaddingLeft() {
        return this.field_7_ftsCellPaddingLeft;
    }

    public void setFtsCellPaddingLeft(byte b) {
        this.field_7_ftsCellPaddingLeft = b;
    }

    public byte getFtsCellPaddingTop() {
        return this.field_8_ftsCellPaddingTop;
    }

    public void setFtsCellPaddingTop(byte b) {
        this.field_8_ftsCellPaddingTop = b;
    }

    public byte getFtsCellPaddingBottom() {
        return this.field_9_ftsCellPaddingBottom;
    }

    public void setFtsCellPaddingBottom(byte b) {
        this.field_9_ftsCellPaddingBottom = b;
    }

    public byte getFtsCellPaddingRight() {
        return this.field_10_ftsCellPaddingRight;
    }

    public void setFtsCellPaddingRight(byte b) {
        this.field_10_ftsCellPaddingRight = b;
    }

    public short getWCellSpacingLeft() {
        return this.field_11_wCellSpacingLeft;
    }

    public void setWCellSpacingLeft(short s) {
        this.field_11_wCellSpacingLeft = s;
    }

    public short getWCellSpacingTop() {
        return this.field_12_wCellSpacingTop;
    }

    public void setWCellSpacingTop(short s) {
        this.field_12_wCellSpacingTop = s;
    }

    public short getWCellSpacingBottom() {
        return this.field_13_wCellSpacingBottom;
    }

    public void setWCellSpacingBottom(short s) {
        this.field_13_wCellSpacingBottom = s;
    }

    public short getWCellSpacingRight() {
        return this.field_14_wCellSpacingRight;
    }

    public void setWCellSpacingRight(short s) {
        this.field_14_wCellSpacingRight = s;
    }

    public byte getFtsCellSpacingLeft() {
        return this.field_15_ftsCellSpacingLeft;
    }

    public void setFtsCellSpacingLeft(byte b) {
        this.field_15_ftsCellSpacingLeft = b;
    }

    public byte getFtsCellSpacingTop() {
        return this.field_16_ftsCellSpacingTop;
    }

    public void setFtsCellSpacingTop(byte b) {
        this.field_16_ftsCellSpacingTop = b;
    }

    public byte getFtsCellSpacingBottom() {
        return this.field_17_ftsCellSpacingBottom;
    }

    public void setFtsCellSpacingBottom(byte b) {
        this.field_17_ftsCellSpacingBottom = b;
    }

    public byte getFtsCellSpacingRight() {
        return this.field_18_ftsCellSpacingRight;
    }

    public void setFtsCellSpacingRight(byte b) {
        this.field_18_ftsCellSpacingRight = b;
    }

    public BorderCode getBrcTop() {
        return this.field_19_brcTop;
    }

    public void setBrcTop(BorderCode borderCode) {
        this.field_19_brcTop = borderCode;
    }

    public BorderCode getBrcLeft() {
        return this.field_20_brcLeft;
    }

    public void setBrcLeft(BorderCode borderCode) {
        this.field_20_brcLeft = borderCode;
    }

    public BorderCode getBrcBottom() {
        return this.field_21_brcBottom;
    }

    public void setBrcBottom(BorderCode borderCode) {
        this.field_21_brcBottom = borderCode;
    }

    public BorderCode getBrcRight() {
        return this.field_22_brcRight;
    }

    public void setBrcRight(BorderCode borderCode) {
        this.field_22_brcRight = borderCode;
    }

    public void setFFirstMerged(boolean z) {
        this.field_1_rgf = (short) fFirstMerged.setBoolean(this.field_1_rgf, z);
    }

    public boolean isFFirstMerged() {
        return fFirstMerged.isSet(this.field_1_rgf);
    }

    public void setFMerged(boolean z) {
        this.field_1_rgf = (short) fMerged.setBoolean(this.field_1_rgf, z);
    }

    public boolean isFMerged() {
        return fMerged.isSet(this.field_1_rgf);
    }

    public void setFVertical(boolean z) {
        this.field_1_rgf = (short) fVertical.setBoolean(this.field_1_rgf, z);
    }

    public boolean isFVertical() {
        return fVertical.isSet(this.field_1_rgf);
    }

    public void setFBackward(boolean z) {
        this.field_1_rgf = (short) fBackward.setBoolean(this.field_1_rgf, z);
    }

    public boolean isFBackward() {
        return fBackward.isSet(this.field_1_rgf);
    }

    public void setFRotateFont(boolean z) {
        this.field_1_rgf = (short) fRotateFont.setBoolean(this.field_1_rgf, z);
    }

    public boolean isFRotateFont() {
        return fRotateFont.isSet(this.field_1_rgf);
    }

    public void setFVertMerge(boolean z) {
        this.field_1_rgf = (short) fVertMerge.setBoolean(this.field_1_rgf, z);
    }

    public boolean isFVertMerge() {
        return fVertMerge.isSet(this.field_1_rgf);
    }

    public void setFVertRestart(boolean z) {
        this.field_1_rgf = (short) fVertRestart.setBoolean(this.field_1_rgf, z);
    }

    public boolean isFVertRestart() {
        return fVertRestart.isSet(this.field_1_rgf);
    }

    public void setVertAlign(byte b) {
        this.field_1_rgf = (short) vertAlign.setValue(this.field_1_rgf, b);
    }

    public byte getVertAlign() {
        return (byte) vertAlign.getValue(this.field_1_rgf);
    }

    public void setFtsWidth(byte b) {
        this.field_1_rgf = (short) ftsWidth.setValue(this.field_1_rgf, b);
    }

    public byte getFtsWidth() {
        return (byte) ftsWidth.getValue(this.field_1_rgf);
    }

    public void setFFitText(boolean z) {
        this.field_1_rgf = (short) fFitText.setBoolean(this.field_1_rgf, z);
    }

    public boolean isFFitText() {
        return fFitText.isSet(this.field_1_rgf);
    }

    public void setFNoWrap(boolean z) {
        this.field_1_rgf = (short) fNoWrap.setBoolean(this.field_1_rgf, z);
    }

    public boolean isFNoWrap() {
        return fNoWrap.isSet(this.field_1_rgf);
    }

    public void setFUnused(byte b) {
        this.field_1_rgf = (short) fUnused.setValue(this.field_1_rgf, b);
    }

    public byte getFUnused() {
        return (byte) fUnused.getValue(this.field_1_rgf);
    }
}
