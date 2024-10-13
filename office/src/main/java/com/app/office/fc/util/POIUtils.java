package com.app.office.fc.util;

import com.app.office.fc.poifs.filesystem.DirectoryEntry;
import com.app.office.fc.poifs.filesystem.DocumentEntry;
import com.app.office.fc.poifs.filesystem.DocumentInputStream;
import com.app.office.fc.poifs.filesystem.Entry;
import com.app.office.fc.poifs.filesystem.POIFSFileSystem;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Internal
public class POIUtils {
    @Internal
    public static void copyNodeRecursively(Entry entry, DirectoryEntry directoryEntry) throws IOException {
        if (entry.isDirectoryEntry()) {
            DirectoryEntry createDirectory = directoryEntry.createDirectory(entry.getName());
            Iterator<Entry> entries = ((DirectoryEntry) entry).getEntries();
            while (entries.hasNext()) {
                copyNodeRecursively(entries.next(), createDirectory);
            }
            return;
        }
        DocumentEntry documentEntry = (DocumentEntry) entry;
        DocumentInputStream documentInputStream = new DocumentInputStream(documentEntry);
        directoryEntry.createDocument(documentEntry.getName(), documentInputStream);
        documentInputStream.close();
    }

    public static void copyNodes(DirectoryEntry directoryEntry, DirectoryEntry directoryEntry2, List<String> list) throws IOException {
        Iterator<Entry> entries = directoryEntry.getEntries();
        while (entries.hasNext()) {
            Entry next = entries.next();
            if (!list.contains(next.getName())) {
                copyNodeRecursively(next, directoryEntry2);
            }
        }
    }

    public static void copyNodes(POIFSFileSystem pOIFSFileSystem, POIFSFileSystem pOIFSFileSystem2, List<String> list) throws IOException {
        copyNodes((DirectoryEntry) pOIFSFileSystem.getRoot(), (DirectoryEntry) pOIFSFileSystem2.getRoot(), list);
    }
}
