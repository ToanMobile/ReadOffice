package com.app.office.fc.poifs.property;

import com.app.office.fc.poifs.filesystem.POIFSDocument;

public class DocumentProperty extends Property {
    private POIFSDocument _document = null;

    public boolean isDirectory() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void preWrite() {
    }

    public DocumentProperty(String str, int i) {
        setName(str);
        setSize(i);
        setNodeColor((byte) 1);
        setPropertyType((byte) 2);
    }

    protected DocumentProperty(int i, byte[] bArr, int i2) {
        super(i, bArr, i2);
    }

    public void setDocument(POIFSDocument pOIFSDocument) {
        this._document = pOIFSDocument;
    }

    public POIFSDocument getDocument() {
        return this._document;
    }

    public boolean shouldUseSmallBlocks() {
        return super.shouldUseSmallBlocks();
    }
}
