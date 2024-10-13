package com.app.office.fc.hslf;

import com.app.office.fc.hslf.exceptions.CorruptPowerPointFileException;
import com.app.office.fc.hslf.record.CurrentUserAtom;
import com.app.office.fc.hslf.record.DocumentEncryptionAtom;
import com.app.office.fc.hslf.record.PersistPtrHolder;
import com.app.office.fc.hslf.record.Record;
import com.app.office.fc.hslf.record.UserEditAtom;

public final class EncryptedSlideShow {
    public static boolean checkIfEncrypted(HSLFSlideShow hSLFSlideShow) {
        return false;
    }

    public static DocumentEncryptionAtom fetchDocumentEncryptionAtom(HSLFSlideShow hSLFSlideShow) {
        CurrentUserAtom currentUserAtom = hSLFSlideShow.getCurrentUserAtom();
        if (currentUserAtom.getCurrentEditOffset() != 0) {
            if (currentUserAtom.getCurrentEditOffset() <= ((long) hSLFSlideShow.getUnderlyingBytes().length)) {
                try {
                    Record buildRecordAtOffset = Record.buildRecordAtOffset(hSLFSlideShow.getUnderlyingBytes(), (int) currentUserAtom.getCurrentEditOffset());
                    if (buildRecordAtOffset == null || !(buildRecordAtOffset instanceof UserEditAtom)) {
                        return null;
                    }
                    Record buildRecordAtOffset2 = Record.buildRecordAtOffset(hSLFSlideShow.getUnderlyingBytes(), ((UserEditAtom) buildRecordAtOffset).getPersistPointersOffset());
                    if (!(buildRecordAtOffset2 instanceof PersistPtrHolder)) {
                        return null;
                    }
                    PersistPtrHolder persistPtrHolder = (PersistPtrHolder) buildRecordAtOffset2;
                    int[] knownSlideIDs = persistPtrHolder.getKnownSlideIDs();
                    int i = -1;
                    for (int i2 = 0; i2 < knownSlideIDs.length; i2++) {
                        if (knownSlideIDs[i2] > i) {
                            i = knownSlideIDs[i2];
                        }
                    }
                    if (i == -1) {
                        return null;
                    }
                    Record buildRecordAtOffset3 = Record.buildRecordAtOffset(hSLFSlideShow.getUnderlyingBytes(), persistPtrHolder.getSlideLocationsLookup().get(Integer.valueOf(i)).intValue());
                    if (buildRecordAtOffset3 instanceof DocumentEncryptionAtom) {
                        return (DocumentEncryptionAtom) buildRecordAtOffset3;
                    }
                } catch (ArrayIndexOutOfBoundsException unused) {
                    return null;
                }
            } else {
                throw new CorruptPowerPointFileException("The CurrentUserAtom claims that the offset of last edit details are past the end of the file");
            }
        }
        return null;
    }
}
