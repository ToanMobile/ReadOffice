package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.PropertyNode;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.Collections;

@Internal
public final class OldSectionTable extends SectionTable {
    @Deprecated
    public OldSectionTable(byte[] bArr, int i, int i2, int i3, TextPieceTable textPieceTable) {
        this(bArr, i, i2);
    }

    public OldSectionTable(byte[] bArr, int i, int i2) {
        SEPX sepx;
        PlexOfCps plexOfCps = new PlexOfCps(bArr, i, i2, 12);
        int length = plexOfCps.length();
        for (int i3 = 0; i3 < length; i3++) {
            GenericPropertyNode property = plexOfCps.getProperty(i3);
            SectionDescriptor sectionDescriptor = new SectionDescriptor(property.getBytes(), 0);
            int fc = sectionDescriptor.getFc();
            int start = property.getStart();
            int end = property.getEnd();
            if (fc == -1) {
                sepx = new SEPX(sectionDescriptor, start, end, new byte[0]);
            } else {
                int i4 = LittleEndian.getShort(bArr, fc) + 2;
                byte[] bArr2 = new byte[i4];
                System.arraycopy(bArr, fc + 2, bArr2, 0, i4);
                sepx = new SEPX(sectionDescriptor, start, end, bArr2);
            }
            this._sections.add(sepx);
        }
        Collections.sort(this._sections, PropertyNode.StartComparator.instance);
    }
}
