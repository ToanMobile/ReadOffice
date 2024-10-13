package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import com.app.office.fc.poifs.common.POIFSConstants;
import com.app.office.fc.poifs.property.DirectoryProperty;
import com.app.office.fc.poifs.property.Property;
import com.app.office.fc.poifs.property.PropertyTable;
import com.app.office.fc.poifs.storage.BATBlock;
import com.app.office.fc.poifs.storage.BlockAllocationTableReader;
import com.app.office.fc.poifs.storage.BlockAllocationTableWriter;
import com.app.office.fc.poifs.storage.BlockList;
import com.app.office.fc.poifs.storage.BlockWritable;
import com.app.office.fc.poifs.storage.HeaderBlock;
import com.app.office.fc.poifs.storage.HeaderBlockWriter;
import com.app.office.fc.poifs.storage.RawDataBlockList;
import com.app.office.fc.poifs.storage.SmallBlockTableReader;
import com.app.office.fc.poifs.storage.SmallBlockTableWriter;
import com.app.office.fc.util.CloseIgnoringInputStream;
import com.app.office.fc.util.IOUtils;
import com.app.office.fc.util.LongField;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class POIFSFileSystem {
    private static final POILogger _logger = POILogFactory.getLogger(POIFSFileSystem.class);
    private List _documents;
    private PropertyTable _property_table;
    private DirectoryNode _root;
    private POIFSBigBlockSize bigBlockSize;

    public String getShortDescription() {
        return "POIFS FileSystem";
    }

    public static InputStream createNonClosingInputStream(InputStream inputStream) {
        return new CloseIgnoringInputStream(inputStream);
    }

    public POIFSFileSystem() {
        this.bigBlockSize = POIFSConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS;
        this._property_table = new PropertyTable(new HeaderBlock(this.bigBlockSize));
        this._documents = new ArrayList();
        this._root = null;
    }

    /* JADX INFO: finally extract failed */
    public POIFSFileSystem(InputStream inputStream) throws IOException {
        this();
        try {
            HeaderBlock headerBlock = new HeaderBlock(inputStream);
            POIFSBigBlockSize bigBlockSize2 = headerBlock.getBigBlockSize();
            this.bigBlockSize = bigBlockSize2;
            RawDataBlockList rawDataBlockList = new RawDataBlockList(inputStream, bigBlockSize2);
            closeInputStream(inputStream, true);
            new BlockAllocationTableReader(headerBlock.getBigBlockSize(), headerBlock.getBATCount(), headerBlock.getBATArray(), headerBlock.getXBATCount(), headerBlock.getXBATIndex(), rawDataBlockList);
            PropertyTable propertyTable = new PropertyTable(headerBlock, rawDataBlockList);
            processProperties(SmallBlockTableReader.getSmallDocumentBlocks(this.bigBlockSize, rawDataBlockList, propertyTable.getRoot(), headerBlock.getSBATStart()), rawDataBlockList, propertyTable.getRoot().getChildren(), (DirectoryNode) null, headerBlock.getPropertyStart());
            getRoot().setStorageClsid(propertyTable.getRoot().getStorageClsid());
        } catch (Throwable th) {
            closeInputStream(inputStream, false);
            throw th;
        }
    }

    private void closeInputStream(InputStream inputStream, boolean z) {
        if (inputStream.markSupported() && !(inputStream instanceof ByteArrayInputStream)) {
            _logger.log(POILogger.WARN, (Object) "POIFS is closing the supplied input stream of type (" + inputStream.getClass().getName() + ") which supports mark/reset.  This will be a problem for the caller if the stream will still be used.  If that is the case the caller should wrap the input stream to avoid this close logic.  This warning is only temporary and will not be present in future versions of POI.");
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            if (!z) {
                e.printStackTrace();
                return;
            }
            throw new RuntimeException(e);
        }
    }

    public static boolean hasPOIFSHeader(InputStream inputStream) throws IOException {
        inputStream.mark(8);
        byte[] bArr = new byte[8];
        IOUtils.readFully(inputStream, bArr);
        LongField longField = new LongField(0, bArr);
        if (inputStream instanceof PushbackInputStream) {
            ((PushbackInputStream) inputStream).unread(bArr);
        } else {
            inputStream.reset();
        }
        if (longField.get() == -2226271756974174256L) {
            return true;
        }
        return false;
    }

    public DocumentEntry createDocument(InputStream inputStream, String str) throws IOException {
        return getRoot().createDocument(str, inputStream);
    }

    public DocumentEntry createDocument(String str, int i, POIFSWriterListener pOIFSWriterListener) throws IOException {
        return getRoot().createDocument(str, i, pOIFSWriterListener);
    }

    public DirectoryEntry createDirectory(String str) throws IOException {
        return getRoot().createDirectory(str);
    }

    public void writeFilesystem(OutputStream outputStream) throws IOException {
        this._property_table.preWrite();
        SmallBlockTableWriter smallBlockTableWriter = new SmallBlockTableWriter(this.bigBlockSize, this._documents, this._property_table.getRoot());
        BlockAllocationTableWriter blockAllocationTableWriter = new BlockAllocationTableWriter(this.bigBlockSize);
        ArrayList<BATManaged> arrayList = new ArrayList<>();
        arrayList.addAll(this._documents);
        arrayList.add(this._property_table);
        arrayList.add(smallBlockTableWriter);
        arrayList.add(smallBlockTableWriter.getSBAT());
        for (BATManaged bATManaged : arrayList) {
            int countBlocks = bATManaged.countBlocks();
            if (countBlocks != 0) {
                bATManaged.setStartBlock(blockAllocationTableWriter.allocateSpace(countBlocks));
            }
        }
        int createBlocks = blockAllocationTableWriter.createBlocks();
        HeaderBlockWriter headerBlockWriter = new HeaderBlockWriter(this.bigBlockSize);
        BATBlock[] bATBlocks = headerBlockWriter.setBATBlocks(blockAllocationTableWriter.countBlocks(), createBlocks);
        headerBlockWriter.setPropertyStart(this._property_table.getStartBlock());
        headerBlockWriter.setSBATStart(smallBlockTableWriter.getSBAT().getStartBlock());
        headerBlockWriter.setSBATBlockCount(smallBlockTableWriter.getSBATBlockCount());
        ArrayList<BlockWritable> arrayList2 = new ArrayList<>();
        arrayList2.add(headerBlockWriter);
        arrayList2.addAll(this._documents);
        arrayList2.add(this._property_table);
        arrayList2.add(smallBlockTableWriter);
        arrayList2.add(smallBlockTableWriter.getSBAT());
        arrayList2.add(blockAllocationTableWriter);
        for (BATBlock add : bATBlocks) {
            arrayList2.add(add);
        }
        for (BlockWritable writeBlocks : arrayList2) {
            writeBlocks.writeBlocks(outputStream);
        }
    }

    public static void main(String[] strArr) throws IOException {
        if (strArr.length != 2) {
            System.err.println("two arguments required: input filename and output filename");
            System.exit(1);
        }
        FileInputStream fileInputStream = new FileInputStream(strArr[0]);
        FileOutputStream fileOutputStream = new FileOutputStream(strArr[1]);
        new POIFSFileSystem(fileInputStream).writeFilesystem(fileOutputStream);
        fileInputStream.close();
        fileOutputStream.close();
    }

    public DirectoryNode getRoot() {
        if (this._root == null) {
            this._root = new DirectoryNode((DirectoryProperty) this._property_table.getRoot(), this, (DirectoryNode) null);
        }
        return this._root;
    }

    public DocumentInputStream createDocumentInputStream(String str) throws IOException {
        return getRoot().createDocumentInputStream(str);
    }

    /* access modifiers changed from: package-private */
    public void addDocument(POIFSDocument pOIFSDocument) {
        this._documents.add(pOIFSDocument);
        this._property_table.addProperty(pOIFSDocument.getDocumentProperty());
    }

    /* access modifiers changed from: package-private */
    public void addDirectory(DirectoryProperty directoryProperty) {
        this._property_table.addProperty(directoryProperty);
    }

    /* access modifiers changed from: package-private */
    public void remove(EntryNode entryNode) {
        this._property_table.removeProperty(entryNode.getProperty());
        if (entryNode.isDocumentEntry()) {
            this._documents.remove(((DocumentNode) entryNode).getDocument());
        }
    }

    private void processProperties(BlockList blockList, BlockList blockList2, Iterator it, DirectoryNode directoryNode, int i) throws IOException {
        POIFSDocument pOIFSDocument;
        while (it.hasNext()) {
            Property property = (Property) it.next();
            String name = property.getName();
            DirectoryNode root = directoryNode == null ? getRoot() : directoryNode;
            if (property.isDirectory()) {
                DirectoryNode directoryNode2 = (DirectoryNode) root.createDirectory(name);
                directoryNode2.setStorageClsid(property.getStorageClsid());
                processProperties(blockList, blockList2, ((DirectoryProperty) property).getChildren(), directoryNode2, i);
            } else {
                int startBlock = property.getStartBlock();
                int size = property.getSize();
                if (property.shouldUseSmallBlocks()) {
                    pOIFSDocument = new POIFSDocument(name, blockList.fetchBlocks(startBlock, i), size);
                } else {
                    pOIFSDocument = new POIFSDocument(name, blockList2.fetchBlocks(startBlock, i), size);
                }
                root.createDocument(pOIFSDocument);
            }
        }
    }

    public int getBigBlockSize() {
        return this.bigBlockSize.getBigBlockSize();
    }

    public POIFSBigBlockSize getBigBlockSizeDetails() {
        return this.bigBlockSize;
    }
}
