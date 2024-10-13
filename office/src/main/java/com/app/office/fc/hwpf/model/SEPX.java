package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.sprm.SectionSprmCompressor;
import com.app.office.fc.hwpf.sprm.SectionSprmUncompressor;
import com.app.office.fc.hwpf.sprm.SprmBuffer;
import com.app.office.fc.hwpf.usermodel.SectionProperties;
import com.app.office.fc.util.Internal;

@Internal
public final class SEPX extends PropertyNode<SEPX> {
    SectionDescriptor _sed;
    SectionProperties sectionProperties;

    public SEPX(SectionDescriptor sectionDescriptor, int i, int i2, byte[] bArr) {
        super(i, i2, new SprmBuffer(bArr, 0));
        this._sed = sectionDescriptor;
    }

    public byte[] getGrpprl() {
        SectionProperties sectionProperties2 = this.sectionProperties;
        if (sectionProperties2 != null) {
            this._buf = new SprmBuffer(SectionSprmCompressor.compressSectionProperty(sectionProperties2), 0);
        }
        return ((SprmBuffer) this._buf).toByteArray();
    }

    public SectionDescriptor getSectionDescriptor() {
        return this._sed;
    }

    public SectionProperties getSectionProperties() {
        if (this.sectionProperties == null) {
            this.sectionProperties = SectionSprmUncompressor.uncompressSEP(((SprmBuffer) this._buf).toByteArray(), 0);
        }
        return this.sectionProperties;
    }

    public boolean equals(Object obj) {
        SEPX sepx = (SEPX) obj;
        if (super.equals(obj)) {
            return sepx._sed.equals(this._sed);
        }
        return false;
    }

    public String toString() {
        return "SEPX from " + getStart() + " to " + getEnd();
    }
}
