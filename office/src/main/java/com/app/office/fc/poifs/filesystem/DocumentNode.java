package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.poifs.property.DocumentProperty;
import java.util.ArrayList;
import java.util.Iterator;

public class DocumentNode extends EntryNode implements DocumentEntry {
    private POIFSDocument _document;

    public Object[] getViewableArray() {
        return new Object[0];
    }

    /* access modifiers changed from: protected */
    public boolean isDeleteOK() {
        return true;
    }

    public boolean isDocumentEntry() {
        return true;
    }

    public boolean preferArray() {
        return false;
    }

    DocumentNode(DocumentProperty documentProperty, DirectoryNode directoryNode) {
        super(documentProperty, directoryNode);
        this._document = documentProperty.getDocument();
    }

    /* access modifiers changed from: package-private */
    public POIFSDocument getDocument() {
        return this._document;
    }

    public int getSize() {
        return getProperty().getSize();
    }

    public Iterator getViewableIterator() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getProperty());
        arrayList.add(this._document);
        return arrayList.iterator();
    }

    public String getShortDescription() {
        return getName();
    }
}
