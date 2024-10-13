package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.PropertyNode;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.Collections;

@Internal
public final class OldPAPBinTable extends PAPBinTable {
    public OldPAPBinTable(byte[] bArr, int i, int i2, int i3, TextPieceTable textPieceTable) {
        PlexOfCps plexOfCps = new PlexOfCps(bArr, i, i2, 2);
        int length = plexOfCps.length();
        for (int i4 = 0; i4 < length; i4++) {
            PAPFormattedDiskPage pAPFormattedDiskPage = new PAPFormattedDiskPage(bArr, bArr, LittleEndian.getShort(plexOfCps.getProperty(i4).getBytes()) * 512, textPieceTable);
            int size = pAPFormattedDiskPage.size();
            for (int i5 = 0; i5 < size; i5++) {
                PAPX papx = pAPFormattedDiskPage.getPAPX(i5);
                if (papx != null) {
                    this._paragraphs.add(papx);
                }
            }
        }
        Collections.sort(this._paragraphs, PropertyNode.StartComparator.instance);
    }
}
