package com.app.office.fc.hwpf.usermodel;

import com.app.office.fc.poifs.filesystem.DirectoryEntry;
import com.app.office.fc.poifs.filesystem.Entry;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.POIUtils;
import java.io.FileNotFoundException;
import java.io.IOException;

@Internal
public class ObjectPoolImpl implements ObjectsPool {
    private DirectoryEntry _objectPool;

    public ObjectPoolImpl(DirectoryEntry directoryEntry) {
        this._objectPool = directoryEntry;
    }

    public Entry getObjectById(String str) {
        DirectoryEntry directoryEntry = this._objectPool;
        if (directoryEntry == null) {
            return null;
        }
        try {
            return directoryEntry.getEntry(str);
        } catch (FileNotFoundException unused) {
            return null;
        }
    }

    @Internal
    public void writeTo(DirectoryEntry directoryEntry) throws IOException {
        DirectoryEntry directoryEntry2 = this._objectPool;
        if (directoryEntry2 != null) {
            POIUtils.copyNodeRecursively(directoryEntry2, directoryEntry);
        }
    }
}
