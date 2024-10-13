package com.app.office.fc.openxml4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipFileZipEntrySource implements ZipEntrySource {
    private ZipFile zipArchive;

    public ZipFileZipEntrySource(ZipFile zipFile) {
        this.zipArchive = zipFile;
    }

    public void close() throws IOException {
        this.zipArchive.close();
        this.zipArchive = null;
    }

    public Enumeration<? extends ZipEntry> getEntries() {
        return this.zipArchive.entries();
    }

    public InputStream getInputStream(ZipEntry zipEntry) throws IOException {
        return this.zipArchive.getInputStream(zipEntry);
    }
}
