package com.app.office.fc.poifs.filesystem;

public class POIFSWriterEvent {
    private String documentName;
    private int limit;
    private POIFSDocumentPath path;
    private DocumentOutputStream stream;

    POIFSWriterEvent(DocumentOutputStream documentOutputStream, POIFSDocumentPath pOIFSDocumentPath, String str, int i) {
        this.stream = documentOutputStream;
        this.path = pOIFSDocumentPath;
        this.documentName = str;
        this.limit = i;
    }

    public DocumentOutputStream getStream() {
        return this.stream;
    }

    public POIFSDocumentPath getPath() {
        return this.path;
    }

    public String getName() {
        return this.documentName;
    }

    public int getLimit() {
        return this.limit;
    }
}
