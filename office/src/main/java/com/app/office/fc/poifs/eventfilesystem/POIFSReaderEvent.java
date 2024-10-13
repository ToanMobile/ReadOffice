package com.app.office.fc.poifs.eventfilesystem;

import com.app.office.fc.poifs.filesystem.DocumentInputStream;
import com.app.office.fc.poifs.filesystem.POIFSDocumentPath;

public class POIFSReaderEvent {
    private String documentName;
    private POIFSDocumentPath path;
    private DocumentInputStream stream;

    POIFSReaderEvent(DocumentInputStream documentInputStream, POIFSDocumentPath pOIFSDocumentPath, String str) {
        this.stream = documentInputStream;
        this.path = pOIFSDocumentPath;
        this.documentName = str;
    }

    public DocumentInputStream getStream() {
        return this.stream;
    }

    public POIFSDocumentPath getPath() {
        return this.path;
    }

    public String getName() {
        return this.documentName;
    }
}
