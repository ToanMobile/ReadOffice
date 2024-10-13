package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import com.app.office.fc.poifs.common.POIFSConstants;
import com.app.office.fc.poifs.property.DocumentProperty;
import com.app.office.fc.poifs.property.Property;
import com.app.office.fc.poifs.storage.BlockWritable;
import com.app.office.fc.poifs.storage.DataInputBlock;
import com.app.office.fc.poifs.storage.DocumentBlock;
import com.app.office.fc.poifs.storage.ListManagedBlock;
import com.app.office.fc.poifs.storage.RawDataBlock;
import com.app.office.fc.poifs.storage.SmallDocumentBlock;
import com.app.office.fc.util.HexDump;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public final class POIFSDocument implements BATManaged, BlockWritable {
    private static final DocumentBlock[] EMPTY_BIG_BLOCK_ARRAY = new DocumentBlock[0];
    private static final SmallDocumentBlock[] EMPTY_SMALL_BLOCK_ARRAY = new SmallDocumentBlock[0];
    private final POIFSBigBlockSize _bigBigBlockSize;
    private BigBlockStore _big_store;
    private DocumentProperty _property;
    private int _size;
    private SmallBlockStore _small_store;

    public boolean preferArray() {
        return true;
    }

    public POIFSDocument(String str, RawDataBlock[] rawDataBlockArr, int i) throws IOException {
        POIFSBigBlockSize pOIFSBigBlockSize;
        this._size = i;
        if (rawDataBlockArr.length == 0) {
            this._bigBigBlockSize = POIFSConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS;
        } else {
            if (rawDataBlockArr[0].getBigBlockSize() == 512) {
                pOIFSBigBlockSize = POIFSConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS;
            } else {
                pOIFSBigBlockSize = POIFSConstants.LARGER_BIG_BLOCK_SIZE_DETAILS;
            }
            this._bigBigBlockSize = pOIFSBigBlockSize;
        }
        this._big_store = new BigBlockStore(this._bigBigBlockSize, convertRawBlocksToBigBlocks(rawDataBlockArr));
        this._property = new DocumentProperty(str, this._size);
        this._small_store = new SmallBlockStore(this._bigBigBlockSize, EMPTY_SMALL_BLOCK_ARRAY);
        this._property.setDocument(this);
    }

    private static DocumentBlock[] convertRawBlocksToBigBlocks(ListManagedBlock[] listManagedBlockArr) throws IOException {
        int length = listManagedBlockArr.length;
        DocumentBlock[] documentBlockArr = new DocumentBlock[length];
        for (int i = 0; i < length; i++) {
            documentBlockArr[i] = new DocumentBlock(listManagedBlockArr[i]);
        }
        return documentBlockArr;
    }

    private static SmallDocumentBlock[] convertRawBlocksToSmallBlocks(ListManagedBlock[] listManagedBlockArr) {
        if (listManagedBlockArr instanceof SmallDocumentBlock[]) {
            return (SmallDocumentBlock[]) listManagedBlockArr;
        }
        SmallDocumentBlock[] smallDocumentBlockArr = new SmallDocumentBlock[listManagedBlockArr.length];
        System.arraycopy(listManagedBlockArr, 0, smallDocumentBlockArr, 0, listManagedBlockArr.length);
        return smallDocumentBlockArr;
    }

    public POIFSDocument(String str, SmallDocumentBlock[] smallDocumentBlockArr, int i) {
        this._size = i;
        if (smallDocumentBlockArr.length == 0) {
            this._bigBigBlockSize = POIFSConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS;
        } else {
            this._bigBigBlockSize = smallDocumentBlockArr[0].getBigBlockSize();
        }
        this._big_store = new BigBlockStore(this._bigBigBlockSize, EMPTY_BIG_BLOCK_ARRAY);
        this._property = new DocumentProperty(str, this._size);
        this._small_store = new SmallBlockStore(this._bigBigBlockSize, smallDocumentBlockArr);
        this._property.setDocument(this);
    }

    public POIFSDocument(String str, POIFSBigBlockSize pOIFSBigBlockSize, ListManagedBlock[] listManagedBlockArr, int i) throws IOException {
        this._size = i;
        this._bigBigBlockSize = pOIFSBigBlockSize;
        DocumentProperty documentProperty = new DocumentProperty(str, i);
        this._property = documentProperty;
        documentProperty.setDocument(this);
        if (Property.isSmall(this._size)) {
            this._big_store = new BigBlockStore(pOIFSBigBlockSize, EMPTY_BIG_BLOCK_ARRAY);
            this._small_store = new SmallBlockStore(pOIFSBigBlockSize, convertRawBlocksToSmallBlocks(listManagedBlockArr));
            return;
        }
        this._big_store = new BigBlockStore(pOIFSBigBlockSize, convertRawBlocksToBigBlocks(listManagedBlockArr));
        this._small_store = new SmallBlockStore(pOIFSBigBlockSize, EMPTY_SMALL_BLOCK_ARRAY);
    }

    public POIFSDocument(String str, ListManagedBlock[] listManagedBlockArr, int i) throws IOException {
        this(str, POIFSConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS, listManagedBlockArr, i);
    }

    public POIFSDocument(String str, POIFSBigBlockSize pOIFSBigBlockSize, InputStream inputStream) throws IOException {
        DocumentBlock documentBlock;
        ArrayList arrayList = new ArrayList();
        this._size = 0;
        this._bigBigBlockSize = pOIFSBigBlockSize;
        do {
            documentBlock = new DocumentBlock(inputStream, pOIFSBigBlockSize);
            int size = documentBlock.size();
            if (size > 0) {
                arrayList.add(documentBlock);
                this._size += size;
            }
        } while (!documentBlock.partiallyRead());
        DocumentBlock[] documentBlockArr = (DocumentBlock[]) arrayList.toArray(new DocumentBlock[arrayList.size()]);
        this._big_store = new BigBlockStore(pOIFSBigBlockSize, documentBlockArr);
        DocumentProperty documentProperty = new DocumentProperty(str, this._size);
        this._property = documentProperty;
        documentProperty.setDocument(this);
        if (this._property.shouldUseSmallBlocks()) {
            this._small_store = new SmallBlockStore(pOIFSBigBlockSize, SmallDocumentBlock.convert(pOIFSBigBlockSize, (BlockWritable[]) documentBlockArr, this._size));
            this._big_store = new BigBlockStore(pOIFSBigBlockSize, new DocumentBlock[0]);
            return;
        }
        this._small_store = new SmallBlockStore(pOIFSBigBlockSize, EMPTY_SMALL_BLOCK_ARRAY);
    }

    public POIFSDocument(String str, InputStream inputStream) throws IOException {
        this(str, POIFSConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS, inputStream);
    }

    public POIFSDocument(String str, int i, POIFSBigBlockSize pOIFSBigBlockSize, POIFSDocumentPath pOIFSDocumentPath, POIFSWriterListener pOIFSWriterListener) {
        this._size = i;
        this._bigBigBlockSize = pOIFSBigBlockSize;
        DocumentProperty documentProperty = new DocumentProperty(str, i);
        this._property = documentProperty;
        documentProperty.setDocument(this);
        if (this._property.shouldUseSmallBlocks()) {
            this._small_store = new SmallBlockStore(pOIFSBigBlockSize, pOIFSDocumentPath, str, i, pOIFSWriterListener);
            this._big_store = new BigBlockStore(pOIFSBigBlockSize, EMPTY_BIG_BLOCK_ARRAY);
            return;
        }
        this._small_store = new SmallBlockStore(pOIFSBigBlockSize, EMPTY_SMALL_BLOCK_ARRAY);
        this._big_store = new BigBlockStore(pOIFSBigBlockSize, pOIFSDocumentPath, str, i, pOIFSWriterListener);
    }

    public POIFSDocument(String str, int i, POIFSDocumentPath pOIFSDocumentPath, POIFSWriterListener pOIFSWriterListener) {
        this(str, i, POIFSConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS, pOIFSDocumentPath, pOIFSWriterListener);
    }

    public BlockWritable[] getSmallBlocks() {
        return this._small_store.getBlocks();
    }

    public int getSize() {
        return this._size;
    }

    /* access modifiers changed from: package-private */
    public void read(byte[] bArr, int i) {
        int length = bArr.length;
        DataInputBlock dataInputBlock = getDataInputBlock(i);
        int available = dataInputBlock.available();
        if (available > length) {
            dataInputBlock.readFully(bArr, 0, length);
            return;
        }
        int i2 = 0;
        while (length > 0) {
            boolean z = length >= available;
            int i3 = z ? available : length;
            dataInputBlock.readFully(bArr, i2, i3);
            length -= i3;
            i2 += i3;
            i += i3;
            if (z) {
                if (i != this._size) {
                    dataInputBlock = getDataInputBlock(i);
                    available = dataInputBlock.available();
                } else if (length > 0) {
                    throw new IllegalStateException("reached end of document stream unexpectedly");
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public DataInputBlock getDataInputBlock(int i) {
        int i2 = this._size;
        if (i >= i2) {
            if (i <= i2) {
                return null;
            }
            throw new RuntimeException("Request for Offset " + i + " doc size is " + this._size);
        } else if (this._property.shouldUseSmallBlocks()) {
            return SmallDocumentBlock.getDataInputBlock(this._small_store.getBlocks(), i);
        } else {
            return DocumentBlock.getDataInputBlock(this._big_store.getBlocks(), i);
        }
    }

    /* access modifiers changed from: package-private */
    public DocumentProperty getDocumentProperty() {
        return this._property;
    }

    public void writeBlocks(OutputStream outputStream) throws IOException {
        this._big_store.writeBlocks(outputStream);
    }

    public int countBlocks() {
        return this._big_store.countBlocks();
    }

    public void setStartBlock(int i) {
        this._property.setStartBlock(i);
    }

    public Object[] getViewableArray() {
        String str;
        Object[] objArr = new Object[1];
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BlockWritable[] blockWritableArr = null;
            if (this._big_store.isValid()) {
                blockWritableArr = this._big_store.getBlocks();
            } else if (this._small_store.isValid()) {
                blockWritableArr = this._small_store.getBlocks();
            }
            if (blockWritableArr != null) {
                for (BlockWritable writeBlocks : blockWritableArr) {
                    writeBlocks.writeBlocks(byteArrayOutputStream);
                }
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                if (byteArray.length > this._property.getSize()) {
                    int size = this._property.getSize();
                    byte[] bArr = new byte[size];
                    System.arraycopy(byteArray, 0, bArr, 0, size);
                    byteArray = bArr;
                }
                ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                HexDump.dump(byteArray, 0, (OutputStream) byteArrayOutputStream2, 0);
                str = byteArrayOutputStream2.toString();
            } else {
                str = "<NO DATA>";
            }
        } catch (IOException e) {
            str = e.getMessage();
        }
        objArr[0] = str;
        return objArr;
    }

    public Iterator getViewableIterator() {
        return Collections.EMPTY_LIST.iterator();
    }

    public String getShortDescription() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Document: \"");
        stringBuffer.append(this._property.getName());
        stringBuffer.append("\"");
        stringBuffer.append(" size = ");
        stringBuffer.append(getSize());
        return stringBuffer.toString();
    }

    private static final class SmallBlockStore {
        private final POIFSBigBlockSize _bigBlockSize;
        private final String _name;
        private final POIFSDocumentPath _path;
        private final int _size;
        private SmallDocumentBlock[] _smallBlocks;
        private final POIFSWriterListener _writer;

        SmallBlockStore(POIFSBigBlockSize pOIFSBigBlockSize, SmallDocumentBlock[] smallDocumentBlockArr) {
            this._bigBlockSize = pOIFSBigBlockSize;
            this._smallBlocks = smallDocumentBlockArr;
            this._path = null;
            this._name = null;
            this._size = -1;
            this._writer = null;
        }

        SmallBlockStore(POIFSBigBlockSize pOIFSBigBlockSize, POIFSDocumentPath pOIFSDocumentPath, String str, int i, POIFSWriterListener pOIFSWriterListener) {
            this._bigBlockSize = pOIFSBigBlockSize;
            this._smallBlocks = new SmallDocumentBlock[0];
            this._path = pOIFSDocumentPath;
            this._name = str;
            this._size = i;
            this._writer = pOIFSWriterListener;
        }

        /* access modifiers changed from: package-private */
        public boolean isValid() {
            return this._smallBlocks.length > 0 || this._writer != null;
        }

        /* access modifiers changed from: package-private */
        public SmallDocumentBlock[] getBlocks() {
            if (isValid() && this._writer != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(this._size);
                this._writer.processPOIFSWriterEvent(new POIFSWriterEvent(new DocumentOutputStream(byteArrayOutputStream, this._size), this._path, this._name, this._size));
                this._smallBlocks = SmallDocumentBlock.convert(this._bigBlockSize, byteArrayOutputStream.toByteArray(), this._size);
            }
            return this._smallBlocks;
        }
    }

    private static final class BigBlockStore {
        private final POIFSBigBlockSize _bigBlockSize;
        private final String _name;
        private final POIFSDocumentPath _path;
        private final int _size;
        private final POIFSWriterListener _writer;
        private DocumentBlock[] bigBlocks;

        BigBlockStore(POIFSBigBlockSize pOIFSBigBlockSize, DocumentBlock[] documentBlockArr) {
            this._bigBlockSize = pOIFSBigBlockSize;
            this.bigBlocks = documentBlockArr;
            this._path = null;
            this._name = null;
            this._size = -1;
            this._writer = null;
        }

        BigBlockStore(POIFSBigBlockSize pOIFSBigBlockSize, POIFSDocumentPath pOIFSDocumentPath, String str, int i, POIFSWriterListener pOIFSWriterListener) {
            this._bigBlockSize = pOIFSBigBlockSize;
            this.bigBlocks = new DocumentBlock[0];
            this._path = pOIFSDocumentPath;
            this._name = str;
            this._size = i;
            this._writer = pOIFSWriterListener;
        }

        /* access modifiers changed from: package-private */
        public boolean isValid() {
            return this.bigBlocks.length > 0 || this._writer != null;
        }

        /* access modifiers changed from: package-private */
        public DocumentBlock[] getBlocks() {
            if (isValid() && this._writer != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(this._size);
                this._writer.processPOIFSWriterEvent(new POIFSWriterEvent(new DocumentOutputStream(byteArrayOutputStream, this._size), this._path, this._name, this._size));
                this.bigBlocks = DocumentBlock.convert(this._bigBlockSize, byteArrayOutputStream.toByteArray(), this._size);
            }
            return this.bigBlocks;
        }

        /* access modifiers changed from: package-private */
        public void writeBlocks(OutputStream outputStream) throws IOException {
            if (!isValid()) {
                return;
            }
            if (this._writer != null) {
                DocumentOutputStream documentOutputStream = new DocumentOutputStream(outputStream, this._size);
                this._writer.processPOIFSWriterEvent(new POIFSWriterEvent(documentOutputStream, this._path, this._name, this._size));
                documentOutputStream.writeFiller(countBlocks() * this._bigBlockSize.getBigBlockSize(), DocumentBlock.getFillByte());
                return;
            }
            int i = 0;
            while (true) {
                DocumentBlock[] documentBlockArr = this.bigBlocks;
                if (i < documentBlockArr.length) {
                    documentBlockArr[i].writeBlocks(outputStream);
                    i++;
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public int countBlocks() {
            if (!isValid()) {
                return 0;
            }
            if (this._writer == null) {
                return this.bigBlocks.length;
            }
            return ((this._size + this._bigBlockSize.getBigBlockSize()) - 1) / this._bigBlockSize.getBigBlockSize();
        }
    }
}
