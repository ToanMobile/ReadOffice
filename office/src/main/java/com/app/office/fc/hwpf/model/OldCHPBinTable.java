package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.PropertyNode;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.LittleEndian;
import java.util.Collections;

@Internal
public final class OldCHPBinTable extends CHPBinTable {
    public OldCHPBinTable(byte[] bArr, int i, int i2, int i3, TextPieceTable textPieceTable) {
        PlexOfCps plexOfCps = new PlexOfCps(bArr, i, i2, 2);
        int length = plexOfCps.length();
        for (int i4 = 0; i4 < length; i4++) {
            CHPFormattedDiskPage cHPFormattedDiskPage = new CHPFormattedDiskPage(bArr, LittleEndian.getShort(plexOfCps.getProperty(i4).getBytes()) * 512, textPieceTable);
            int size = cHPFormattedDiskPage.size();
            for (int i5 = 0; i5 < size; i5++) {
                CHPX chpx = cHPFormattedDiskPage.getCHPX(i5);
                if (chpx != null) {
                    this._textRuns.add(chpx);
                }
            }
        }
        Collections.sort(this._textRuns, PropertyNode.StartComparator.instance);
    }
}
