package com.app.office.fc.hwpf.model.types;

import com.app.office.fc.hwpf.model.HDFType;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;

@Internal
public abstract class TLPAbstractType implements HDFType {
    private static BitField fBestFit = new BitField(16);
    private static BitField fBorders = new BitField(1);
    private static BitField fColor = new BitField(8);
    private static BitField fFont = new BitField(4);
    private static BitField fHdrRows = new BitField(32);
    private static BitField fLastRow = new BitField(64);
    private static BitField fShading = new BitField(2);
    protected short field_1_itl;
    protected byte field_2_tlp_flags;

    public int getSize() {
        return 7;
    }

    /* access modifiers changed from: protected */
    public void fillFields(byte[] bArr, int i) {
        this.field_1_itl = LittleEndian.getShort(bArr, i + 0);
        this.field_2_tlp_flags = bArr[i + 2];
    }

    public void serialize(byte[] bArr, int i) {
        LittleEndian.putShort(bArr, i + 0, this.field_1_itl);
        bArr[i + 2] = this.field_2_tlp_flags;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[TLP]\n");
        stringBuffer.append("    .itl                  = ");
        stringBuffer.append(" (");
        stringBuffer.append(getItl());
        stringBuffer.append(" )\n");
        stringBuffer.append("    .tlp_flags            = ");
        stringBuffer.append(" (");
        stringBuffer.append(getTlp_flags());
        stringBuffer.append(" )\n");
        stringBuffer.append("         .fBorders                 = ");
        stringBuffer.append(isFBorders());
        stringBuffer.append(10);
        stringBuffer.append("         .fShading                 = ");
        stringBuffer.append(isFShading());
        stringBuffer.append(10);
        stringBuffer.append("         .fFont                    = ");
        stringBuffer.append(isFFont());
        stringBuffer.append(10);
        stringBuffer.append("         .fColor                   = ");
        stringBuffer.append(isFColor());
        stringBuffer.append(10);
        stringBuffer.append("         .fBestFit                 = ");
        stringBuffer.append(isFBestFit());
        stringBuffer.append(10);
        stringBuffer.append("         .fHdrRows                 = ");
        stringBuffer.append(isFHdrRows());
        stringBuffer.append(10);
        stringBuffer.append("         .fLastRow                 = ");
        stringBuffer.append(isFLastRow());
        stringBuffer.append(10);
        stringBuffer.append("[/TLP]\n");
        return stringBuffer.toString();
    }

    public short getItl() {
        return this.field_1_itl;
    }

    public void setItl(short s) {
        this.field_1_itl = s;
    }

    public byte getTlp_flags() {
        return this.field_2_tlp_flags;
    }

    public void setTlp_flags(byte b) {
        this.field_2_tlp_flags = b;
    }

    public void setFBorders(boolean z) {
        this.field_2_tlp_flags = (byte) fBorders.setBoolean(this.field_2_tlp_flags, z);
    }

    public boolean isFBorders() {
        return fBorders.isSet(this.field_2_tlp_flags);
    }

    public void setFShading(boolean z) {
        this.field_2_tlp_flags = (byte) fShading.setBoolean(this.field_2_tlp_flags, z);
    }

    public boolean isFShading() {
        return fShading.isSet(this.field_2_tlp_flags);
    }

    public void setFFont(boolean z) {
        this.field_2_tlp_flags = (byte) fFont.setBoolean(this.field_2_tlp_flags, z);
    }

    public boolean isFFont() {
        return fFont.isSet(this.field_2_tlp_flags);
    }

    public void setFColor(boolean z) {
        this.field_2_tlp_flags = (byte) fColor.setBoolean(this.field_2_tlp_flags, z);
    }

    public boolean isFColor() {
        return fColor.isSet(this.field_2_tlp_flags);
    }

    public void setFBestFit(boolean z) {
        this.field_2_tlp_flags = (byte) fBestFit.setBoolean(this.field_2_tlp_flags, z);
    }

    public boolean isFBestFit() {
        return fBestFit.isSet(this.field_2_tlp_flags);
    }

    public void setFHdrRows(boolean z) {
        this.field_2_tlp_flags = (byte) fHdrRows.setBoolean(this.field_2_tlp_flags, z);
    }

    public boolean isFHdrRows() {
        return fHdrRows.isSet(this.field_2_tlp_flags);
    }

    public void setFLastRow(boolean z) {
        this.field_2_tlp_flags = (byte) fLastRow.setBoolean(this.field_2_tlp_flags, z);
    }

    public boolean isFLastRow() {
        return fLastRow.isSet(this.field_2_tlp_flags);
    }
}
