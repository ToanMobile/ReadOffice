package com.app.office.fc.hwpf.model;

import com.app.office.fc.hwpf.model.io.HWPFOutputStream;
import com.app.office.fc.util.Internal;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Internal
public final class SavedByTable {
    private SavedByEntry[] entries;

    public SavedByTable(byte[] bArr, int i, int i2) {
        String[] read = SttbfUtils.read(bArr, i);
        int length = read.length / 2;
        this.entries = new SavedByEntry[length];
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = i3 * 2;
            this.entries[i3] = new SavedByEntry(read[i4], read[i4 + 1]);
        }
    }

    public List<SavedByEntry> getEntries() {
        return Collections.unmodifiableList(Arrays.asList(this.entries));
    }

    public void writeTo(HWPFOutputStream hWPFOutputStream) throws IOException {
        SavedByEntry[] savedByEntryArr = this.entries;
        String[] strArr = new String[(savedByEntryArr.length * 2)];
        int i = 0;
        for (SavedByEntry savedByEntry : savedByEntryArr) {
            int i2 = i + 1;
            strArr[i] = savedByEntry.getUserName();
            i = i2 + 1;
            strArr[i2] = savedByEntry.getSaveLocation();
        }
        SttbfUtils.write(hWPFOutputStream, strArr);
    }
}
