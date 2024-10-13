package com.app.office.fc;

import com.app.office.fc.hpsf.DocumentSummaryInformation;
import com.app.office.fc.hpsf.HPSFException;
import com.app.office.fc.hpsf.MutablePropertySet;
import com.app.office.fc.hpsf.PropertySet;
import com.app.office.fc.hpsf.PropertySetFactory;
import com.app.office.fc.hpsf.SummaryInformation;
import com.app.office.fc.hpsf.WritingNotSupportedException;
import com.app.office.fc.poifs.filesystem.DirectoryEntry;
import com.app.office.fc.poifs.filesystem.DirectoryNode;
import com.app.office.fc.poifs.filesystem.Entry;
import com.app.office.fc.poifs.filesystem.NPOIFSFileSystem;
import com.app.office.fc.poifs.filesystem.POIFSFileSystem;
import com.app.office.fc.util.Internal;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import com.app.office.fc.util.POIUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public abstract class POIDocument {
    private static final POILogger logger = POILogFactory.getLogger(POIDocument.class);
    protected DirectoryNode directory;
    private DocumentSummaryInformation dsInf;
    private boolean initialized;
    private SummaryInformation sInf;

    public abstract void write(OutputStream outputStream) throws IOException;

    protected POIDocument(DirectoryNode directoryNode) {
        this.initialized = false;
        this.directory = directoryNode;
    }

    @Deprecated
    protected POIDocument(DirectoryNode directoryNode, POIFSFileSystem pOIFSFileSystem) {
        this.initialized = false;
        this.directory = directoryNode;
    }

    protected POIDocument(POIFSFileSystem pOIFSFileSystem) {
        this(pOIFSFileSystem.getRoot());
    }

    protected POIDocument(NPOIFSFileSystem nPOIFSFileSystem) {
        this(nPOIFSFileSystem.getRoot());
    }

    public DocumentSummaryInformation getDocumentSummaryInformation() {
        if (!this.initialized) {
            readProperties();
        }
        return this.dsInf;
    }

    public SummaryInformation getSummaryInformation() {
        if (!this.initialized) {
            readProperties();
        }
        return this.sInf;
    }

    public void createInformationProperties() {
        if (!this.initialized) {
            readProperties();
        }
        if (this.sInf == null) {
            this.sInf = PropertySetFactory.newSummaryInformation();
        }
        if (this.dsInf == null) {
            this.dsInf = PropertySetFactory.newDocumentSummaryInformation();
        }
    }

    /* access modifiers changed from: protected */
    public void readProperties() {
        PropertySet propertySet = getPropertySet(DocumentSummaryInformation.DEFAULT_STREAM_NAME);
        if (propertySet != null && (propertySet instanceof DocumentSummaryInformation)) {
            this.dsInf = (DocumentSummaryInformation) propertySet;
        } else if (propertySet != null) {
            logger.log(POILogger.WARN, (Object) "DocumentSummaryInformation property set came back with wrong class - ", (Object) propertySet.getClass());
        }
        PropertySet propertySet2 = getPropertySet(SummaryInformation.DEFAULT_STREAM_NAME);
        if (propertySet2 instanceof SummaryInformation) {
            this.sInf = (SummaryInformation) propertySet2;
        } else if (propertySet2 != null) {
            logger.log(POILogger.WARN, (Object) "SummaryInformation property set came back with wrong class - ", (Object) propertySet2.getClass());
        }
        this.initialized = true;
    }

    /* access modifiers changed from: protected */
    public PropertySet getPropertySet(String str) {
        DirectoryNode directoryNode = this.directory;
        if (directoryNode == null) {
            return null;
        }
        try {
            try {
                return PropertySetFactory.create(directoryNode.createDocumentInputStream(directoryNode.getEntry(str)));
            } catch (IOException e) {
                POILogger pOILogger = logger;
                int i = POILogger.WARN;
                pOILogger.log(i, (Object) "Error creating property set with name " + str + "\n" + e);
                return null;
            } catch (HPSFException e2) {
                POILogger pOILogger2 = logger;
                int i2 = POILogger.WARN;
                pOILogger2.log(i2, (Object) "Error creating property set with name " + str + "\n" + e2);
                return null;
            }
        } catch (IOException e3) {
            POILogger pOILogger3 = logger;
            int i3 = POILogger.WARN;
            pOILogger3.log(i3, (Object) "Error getting property set with name " + str + "\n" + e3);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void writeProperties(POIFSFileSystem pOIFSFileSystem) throws IOException {
        writeProperties(pOIFSFileSystem, (List<String>) null);
    }

    /* access modifiers changed from: protected */
    public void writeProperties(POIFSFileSystem pOIFSFileSystem, List<String> list) throws IOException {
        SummaryInformation summaryInformation = getSummaryInformation();
        if (summaryInformation != null) {
            writePropertySet(SummaryInformation.DEFAULT_STREAM_NAME, summaryInformation, pOIFSFileSystem);
            if (list != null) {
                list.add(SummaryInformation.DEFAULT_STREAM_NAME);
            }
        }
        DocumentSummaryInformation documentSummaryInformation = getDocumentSummaryInformation();
        if (documentSummaryInformation != null) {
            writePropertySet(DocumentSummaryInformation.DEFAULT_STREAM_NAME, documentSummaryInformation, pOIFSFileSystem);
            if (list != null) {
                list.add(DocumentSummaryInformation.DEFAULT_STREAM_NAME);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void writePropertySet(String str, PropertySet propertySet, POIFSFileSystem pOIFSFileSystem) throws IOException {
        try {
            MutablePropertySet mutablePropertySet = new MutablePropertySet(propertySet);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mutablePropertySet.write(byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            pOIFSFileSystem.createDocument(new ByteArrayInputStream(byteArray), str);
            POILogger pOILogger = logger;
            int i = POILogger.INFO;
            pOILogger.log(i, (Object) "Wrote property set " + str + " of size " + byteArray.length);
        } catch (WritingNotSupportedException unused) {
            PrintStream printStream = System.err;
            printStream.println("Couldn't write property set with name " + str + " as not supported by HPSF yet");
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void copyNodes(POIFSFileSystem pOIFSFileSystem, POIFSFileSystem pOIFSFileSystem2, List<String> list) throws IOException {
        POIUtils.copyNodes(pOIFSFileSystem, pOIFSFileSystem2, list);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void copyNodes(DirectoryNode directoryNode, DirectoryNode directoryNode2, List<String> list) throws IOException {
        POIUtils.copyNodes((DirectoryEntry) directoryNode, (DirectoryEntry) directoryNode2, list);
    }

    /* access modifiers changed from: protected */
    @Internal
    @Deprecated
    public void copyNodeRecursively(Entry entry, DirectoryEntry directoryEntry) throws IOException {
        POIUtils.copyNodeRecursively(entry, directoryEntry);
    }
}
