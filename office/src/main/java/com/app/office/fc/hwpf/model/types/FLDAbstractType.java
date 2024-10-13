package com.app.office.fc.hwpf.model.types;

import com.app.office.fc.hwpf.model.HDFType;
import com.app.office.fc.util.BitField;
import com.app.office.fc.util.Internal;

@Internal
public abstract class FLDAbstractType implements HDFType {
    private static BitField ch = new BitField(31);
    private static BitField fDiffer = new BitField(1);
    private static BitField fHasSep = new BitField(64);
    private static BitField fLocked = new BitField(16);
    private static BitField fNested = new BitField(64);
    private static BitField fPrivateResult = new BitField(32);
    private static BitField fResultDirty = new BitField(4);
    private static BitField fResultEdited = new BitField(8);
    private static BitField fZombieEmbed = new BitField(2);
    private static BitField reserved = new BitField(224);
    protected byte field_1_chHolder;
    protected byte field_2_flt;

    public static int getSize() {
        return 6;
    }

    /* access modifiers changed from: protected */
    public void fillFields(byte[] bArr, int i) {
        this.field_1_chHolder = bArr[i + 0];
        this.field_2_flt = bArr[i + 1];
    }

    public void serialize(byte[] bArr, int i) {
        bArr[i + 0] = this.field_1_chHolder;
        bArr[i + 1] = this.field_2_flt;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[FLD]\n");
        stringBuffer.append("    .chHolder             = ");
        stringBuffer.append(" (");
        stringBuffer.append(getChHolder());
        stringBuffer.append(" )\n");
        stringBuffer.append("         .ch                       = ");
        stringBuffer.append(getCh());
        stringBuffer.append(10);
        stringBuffer.append("         .reserved                 = ");
        stringBuffer.append(getReserved());
        stringBuffer.append(10);
        stringBuffer.append("    .flt                  = ");
        stringBuffer.append(" (");
        stringBuffer.append(getFlt());
        stringBuffer.append(" )\n");
        stringBuffer.append("         .fDiffer                  = ");
        stringBuffer.append(isFDiffer());
        stringBuffer.append(10);
        stringBuffer.append("         .fZombieEmbed             = ");
        stringBuffer.append(isFZombieEmbed());
        stringBuffer.append(10);
        stringBuffer.append("         .fResultDirty             = ");
        stringBuffer.append(isFResultDirty());
        stringBuffer.append(10);
        stringBuffer.append("         .fResultEdited            = ");
        stringBuffer.append(isFResultEdited());
        stringBuffer.append(10);
        stringBuffer.append("         .fLocked                  = ");
        stringBuffer.append(isFLocked());
        stringBuffer.append(10);
        stringBuffer.append("         .fPrivateResult           = ");
        stringBuffer.append(isFPrivateResult());
        stringBuffer.append(10);
        stringBuffer.append("         .fNested                  = ");
        stringBuffer.append(isFNested());
        stringBuffer.append(10);
        stringBuffer.append("         .fHasSep                  = ");
        stringBuffer.append(isFHasSep());
        stringBuffer.append(10);
        stringBuffer.append("[/FLD]\n");
        return stringBuffer.toString();
    }

    public byte getChHolder() {
        return this.field_1_chHolder;
    }

    public void setChHolder(byte b) {
        this.field_1_chHolder = b;
    }

    public byte getFlt() {
        return this.field_2_flt;
    }

    public void setFlt(byte b) {
        this.field_2_flt = b;
    }

    public void setCh(byte b) {
        this.field_1_chHolder = (byte) ch.setValue(this.field_1_chHolder, b);
    }

    public byte getCh() {
        return (byte) ch.getValue(this.field_1_chHolder);
    }

    public void setReserved(byte b) {
        this.field_1_chHolder = (byte) reserved.setValue(this.field_1_chHolder, b);
    }

    public byte getReserved() {
        return (byte) reserved.getValue(this.field_1_chHolder);
    }

    public void setFDiffer(boolean z) {
        this.field_2_flt = (byte) fDiffer.setBoolean(this.field_2_flt, z);
    }

    public boolean isFDiffer() {
        return fDiffer.isSet(this.field_2_flt);
    }

    public void setFZombieEmbed(boolean z) {
        this.field_2_flt = (byte) fZombieEmbed.setBoolean(this.field_2_flt, z);
    }

    public boolean isFZombieEmbed() {
        return fZombieEmbed.isSet(this.field_2_flt);
    }

    public void setFResultDirty(boolean z) {
        this.field_2_flt = (byte) fResultDirty.setBoolean(this.field_2_flt, z);
    }

    public boolean isFResultDirty() {
        return fResultDirty.isSet(this.field_2_flt);
    }

    public void setFResultEdited(boolean z) {
        this.field_2_flt = (byte) fResultEdited.setBoolean(this.field_2_flt, z);
    }

    public boolean isFResultEdited() {
        return fResultEdited.isSet(this.field_2_flt);
    }

    public void setFLocked(boolean z) {
        this.field_2_flt = (byte) fLocked.setBoolean(this.field_2_flt, z);
    }

    public boolean isFLocked() {
        return fLocked.isSet(this.field_2_flt);
    }

    public void setFPrivateResult(boolean z) {
        this.field_2_flt = (byte) fPrivateResult.setBoolean(this.field_2_flt, z);
    }

    public boolean isFPrivateResult() {
        return fPrivateResult.isSet(this.field_2_flt);
    }

    public void setFNested(boolean z) {
        this.field_2_flt = (byte) fNested.setBoolean(this.field_2_flt, z);
    }

    public boolean isFNested() {
        return fNested.isSet(this.field_2_flt);
    }

    public void setFHasSep(boolean z) {
        this.field_2_flt = (byte) fHasSep.setBoolean(this.field_2_flt, z);
    }

    public boolean isFHasSep() {
        return fHasSep.isSet(this.field_2_flt);
    }
}
