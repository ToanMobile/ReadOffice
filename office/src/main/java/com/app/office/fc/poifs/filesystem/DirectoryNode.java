package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.hpsf.ClassID;
import com.app.office.fc.poifs.property.DirectoryProperty;
import com.app.office.fc.poifs.property.DocumentProperty;
import com.app.office.fc.poifs.property.Property;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DirectoryNode extends EntryNode implements DirectoryEntry, Iterable<Entry> {
    private Map<String, Entry> _byname;
    private ArrayList<Entry> _entries;
    private NPOIFSFileSystem _nfilesystem;
    private POIFSFileSystem _ofilesystem;
    private POIFSDocumentPath _path;

    public Object[] getViewableArray() {
        return new Object[0];
    }

    public boolean isDirectoryEntry() {
        return true;
    }

    public boolean preferArray() {
        return false;
    }

    DirectoryNode(DirectoryProperty directoryProperty, POIFSFileSystem pOIFSFileSystem, DirectoryNode directoryNode) {
        this(directoryProperty, directoryNode, pOIFSFileSystem, (NPOIFSFileSystem) null);
    }

    DirectoryNode(DirectoryProperty directoryProperty, NPOIFSFileSystem nPOIFSFileSystem, DirectoryNode directoryNode) {
        this(directoryProperty, directoryNode, (POIFSFileSystem) null, nPOIFSFileSystem);
    }

    private DirectoryNode(DirectoryProperty directoryProperty, DirectoryNode directoryNode, POIFSFileSystem pOIFSFileSystem, NPOIFSFileSystem nPOIFSFileSystem) {
        super(directoryProperty, directoryNode);
        Entry entry;
        this._ofilesystem = pOIFSFileSystem;
        this._nfilesystem = nPOIFSFileSystem;
        if (directoryNode == null) {
            this._path = new POIFSDocumentPath();
        } else {
            this._path = new POIFSDocumentPath(directoryNode._path, new String[]{directoryProperty.getName()});
        }
        this._byname = new HashMap();
        this._entries = new ArrayList<>();
        Iterator<Property> children = directoryProperty.getChildren();
        while (children.hasNext()) {
            Property next = children.next();
            if (next.isDirectory()) {
                DirectoryProperty directoryProperty2 = (DirectoryProperty) next;
                POIFSFileSystem pOIFSFileSystem2 = this._ofilesystem;
                if (pOIFSFileSystem2 != null) {
                    entry = new DirectoryNode(directoryProperty2, pOIFSFileSystem2, this);
                } else {
                    entry = new DirectoryNode(directoryProperty2, this._nfilesystem, this);
                }
            } else {
                entry = new DocumentNode((DocumentProperty) next, this);
            }
            this._entries.add(entry);
            this._byname.put(entry.getName(), entry);
        }
    }

    public POIFSDocumentPath getPath() {
        return this._path;
    }

    public POIFSFileSystem getFileSystem() {
        return this._ofilesystem;
    }

    public NPOIFSFileSystem getNFileSystem() {
        return this._nfilesystem;
    }

    public DocumentInputStream createDocumentInputStream(String str) throws IOException {
        return createDocumentInputStream(getEntry(str));
    }

    public DocumentInputStream createDocumentInputStream(Entry entry) throws IOException {
        if (entry.isDocumentEntry()) {
            return new DocumentInputStream((DocumentEntry) entry);
        }
        throw new IOException("Entry '" + entry.getName() + "' is not a DocumentEntry");
    }

    /* access modifiers changed from: package-private */
    public DocumentEntry createDocument(POIFSDocument pOIFSDocument) throws IOException {
        DocumentProperty documentProperty = pOIFSDocument.getDocumentProperty();
        DocumentNode documentNode = new DocumentNode(documentProperty, this);
        ((DirectoryProperty) getProperty()).addChild(documentProperty);
        this._ofilesystem.addDocument(pOIFSDocument);
        this._entries.add(documentNode);
        this._byname.put(documentProperty.getName(), documentNode);
        return documentNode;
    }

    /* access modifiers changed from: package-private */
    public DocumentEntry createDocument(NPOIFSDocument nPOIFSDocument) throws IOException {
        DocumentProperty documentProperty = nPOIFSDocument.getDocumentProperty();
        DocumentNode documentNode = new DocumentNode(documentProperty, this);
        ((DirectoryProperty) getProperty()).addChild(documentProperty);
        this._nfilesystem.addDocument(nPOIFSDocument);
        this._entries.add(documentNode);
        this._byname.put(documentProperty.getName(), documentNode);
        return documentNode;
    }

    /* access modifiers changed from: package-private */
    public boolean changeName(String str, String str2) {
        EntryNode entryNode = (EntryNode) this._byname.get(str);
        if (entryNode == null) {
            return false;
        }
        boolean changeName = ((DirectoryProperty) getProperty()).changeName(entryNode.getProperty(), str2);
        if (!changeName) {
            return changeName;
        }
        this._byname.remove(str);
        this._byname.put(entryNode.getProperty().getName(), entryNode);
        return changeName;
    }

    /* access modifiers changed from: package-private */
    public boolean deleteEntry(EntryNode entryNode) {
        boolean deleteChild = ((DirectoryProperty) getProperty()).deleteChild(entryNode.getProperty());
        if (deleteChild) {
            this._entries.remove(entryNode);
            this._byname.remove(entryNode.getName());
            POIFSFileSystem pOIFSFileSystem = this._ofilesystem;
            if (pOIFSFileSystem != null) {
                pOIFSFileSystem.remove(entryNode);
            } else {
                this._nfilesystem.remove(entryNode);
            }
        }
        return deleteChild;
    }

    public Iterator<Entry> getEntries() {
        return this._entries.iterator();
    }

    public boolean isEmpty() {
        return this._entries.isEmpty();
    }

    public int getEntryCount() {
        return this._entries.size();
    }

    public boolean hasEntry(String str) {
        return str != null && this._byname.containsKey(str);
    }

    public Entry getEntry(String str) throws FileNotFoundException {
        Entry entry = str != null ? this._byname.get(str) : null;
        if (entry != null) {
            return entry;
        }
        throw new FileNotFoundException("no such entry: \"" + str + "\"");
    }

    public DocumentEntry createDocument(String str, InputStream inputStream) throws IOException {
        NPOIFSFileSystem nPOIFSFileSystem = this._nfilesystem;
        if (nPOIFSFileSystem != null) {
            return createDocument(new NPOIFSDocument(str, nPOIFSFileSystem, inputStream));
        }
        return createDocument(new POIFSDocument(str, inputStream));
    }

    public DocumentEntry createDocument(String str, int i, POIFSWriterListener pOIFSWriterListener) throws IOException {
        return createDocument(new POIFSDocument(str, i, this._path, pOIFSWriterListener));
    }

    public DirectoryEntry createDirectory(String str) throws IOException {
        DirectoryNode directoryNode;
        DirectoryProperty directoryProperty = new DirectoryProperty(str);
        POIFSFileSystem pOIFSFileSystem = this._ofilesystem;
        if (pOIFSFileSystem != null) {
            directoryNode = new DirectoryNode(directoryProperty, pOIFSFileSystem, this);
            this._ofilesystem.addDirectory(directoryProperty);
        } else {
            directoryNode = new DirectoryNode(directoryProperty, this._nfilesystem, this);
            this._nfilesystem.addDirectory(directoryProperty);
        }
        ((DirectoryProperty) getProperty()).addChild(directoryProperty);
        this._entries.add(directoryNode);
        this._byname.put(str, directoryNode);
        return directoryNode;
    }

    public ClassID getStorageClsid() {
        return getProperty().getStorageClsid();
    }

    public void setStorageClsid(ClassID classID) {
        getProperty().setStorageClsid(classID);
    }

    /* access modifiers changed from: protected */
    public boolean isDeleteOK() {
        return isEmpty();
    }

    public Iterator getViewableIterator() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getProperty());
        Iterator<Entry> it = this._entries.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList.iterator();
    }

    public String getShortDescription() {
        return getName();
    }

    public Iterator<Entry> iterator() {
        return getEntries();
    }
}
