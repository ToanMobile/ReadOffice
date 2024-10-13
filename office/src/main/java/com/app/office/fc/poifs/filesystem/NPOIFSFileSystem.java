package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import com.app.office.fc.poifs.common.POIFSConstants;
import com.app.office.fc.poifs.filesystem.BlockStore;
import com.app.office.fc.poifs.nio.ByteArrayBackedDataSource;
import com.app.office.fc.poifs.nio.DataSource;
import com.app.office.fc.poifs.nio.FileBackedDataSource;
import com.app.office.fc.poifs.property.DirectoryProperty;
import com.app.office.fc.poifs.property.NPropertyTable;
import com.app.office.fc.poifs.storage.BATBlock;
import com.app.office.fc.poifs.storage.BlockAllocationTableWriter;
import com.app.office.fc.poifs.storage.HeaderBlock;
import com.app.office.fc.poifs.storage.HeaderBlockWriter;
import com.app.office.fc.util.CloseIgnoringInputStream;
import com.app.office.fc.util.IOUtils;
import com.app.office.fc.util.LongField;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NPOIFSFileSystem extends BlockStore implements Closeable {
    private static final POILogger _logger = POILogFactory.getLogger(NPOIFSFileSystem.class);
    private List<BATBlock> _bat_blocks;
    private DataSource _data;
    private HeaderBlock _header;
    private NPOIFSMiniStore _mini_store;
    private NPropertyTable _property_table;
    private DirectoryNode _root;
    private List<BATBlock> _xbat_blocks;
    private POIFSBigBlockSize bigBlockSize;

    public String getShortDescription() {
        return "POIFS FileSystem";
    }

    public static InputStream createNonClosingInputStream(InputStream inputStream) {
        return new CloseIgnoringInputStream(inputStream);
    }

    private NPOIFSFileSystem(boolean z) {
        this.bigBlockSize = POIFSConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS;
        HeaderBlock headerBlock = new HeaderBlock(this.bigBlockSize);
        this._header = headerBlock;
        NPropertyTable nPropertyTable = new NPropertyTable(headerBlock);
        this._property_table = nPropertyTable;
        this._mini_store = new NPOIFSMiniStore(this, nPropertyTable.getRoot(), new ArrayList(), this._header);
        this._xbat_blocks = new ArrayList();
        this._bat_blocks = new ArrayList();
        this._root = null;
        if (z) {
            this._data = new ByteArrayBackedDataSource(new byte[(this.bigBlockSize.getBigBlockSize() * 3)]);
        }
    }

    public NPOIFSFileSystem() {
        this(true);
        this._header.setBATCount(1);
        this._header.setBATArray(new int[]{0});
        this._bat_blocks.add(BATBlock.createEmptyBATBlock(this.bigBlockSize, false));
        setNextBlock(0, -3);
        this._property_table.setStartBlock(1);
        setNextBlock(1, -2);
    }

    public NPOIFSFileSystem(File file) throws IOException {
        this(file, true);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public NPOIFSFileSystem(File file, boolean z) throws IOException {
        this(new RandomAccessFile(file, z ? "r" : "rw").getChannel(), true);
    }

    public NPOIFSFileSystem(FileChannel fileChannel) throws IOException {
        this(fileChannel, false);
    }

    private NPOIFSFileSystem(FileChannel fileChannel, boolean z) throws IOException {
        this(false);
        try {
            ByteBuffer allocate = ByteBuffer.allocate(512);
            IOUtils.readFully((ReadableByteChannel) fileChannel, allocate);
            this._header = new HeaderBlock(allocate);
            this._data = new FileBackedDataSource(fileChannel);
            readCoreContents();
        } catch (IOException e) {
            if (z) {
                fileChannel.close();
            }
            throw e;
        } catch (RuntimeException e2) {
            if (z) {
                fileChannel.close();
            }
            throw e2;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x005b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NPOIFSFileSystem(java.io.InputStream r6) throws java.io.IOException {
        /*
            r5 = this;
            r0 = 0
            r5.<init>((boolean) r0)
            java.nio.channels.ReadableByteChannel r1 = java.nio.channels.Channels.newChannel(r6)     // Catch:{ all -> 0x0057 }
            r2 = 512(0x200, float:7.175E-43)
            java.nio.ByteBuffer r2 = java.nio.ByteBuffer.allocate(r2)     // Catch:{ all -> 0x0055 }
            com.app.office.fc.util.IOUtils.readFully((java.nio.channels.ReadableByteChannel) r1, (java.nio.ByteBuffer) r2)     // Catch:{ all -> 0x0055 }
            com.app.office.fc.poifs.storage.HeaderBlock r3 = new com.app.office.fc.poifs.storage.HeaderBlock     // Catch:{ all -> 0x0055 }
            r3.<init>((java.nio.ByteBuffer) r2)     // Catch:{ all -> 0x0055 }
            r5._header = r3     // Catch:{ all -> 0x0055 }
            int r3 = r3.getBATCount()     // Catch:{ all -> 0x0055 }
            com.app.office.fc.poifs.storage.BlockAllocationTableReader.sanityCheckBlockCount(r3)     // Catch:{ all -> 0x0055 }
            com.app.office.fc.poifs.storage.HeaderBlock r3 = r5._header     // Catch:{ all -> 0x0055 }
            int r3 = com.app.office.fc.poifs.storage.BATBlock.calculateMaximumSize(r3)     // Catch:{ all -> 0x0055 }
            java.nio.ByteBuffer r3 = java.nio.ByteBuffer.allocate(r3)     // Catch:{ all -> 0x0055 }
            r2.position(r0)     // Catch:{ all -> 0x0055 }
            r3.put(r2)     // Catch:{ all -> 0x0055 }
            int r2 = r2.capacity()     // Catch:{ all -> 0x0055 }
            r3.position(r2)     // Catch:{ all -> 0x0055 }
            com.app.office.fc.util.IOUtils.readFully((java.nio.channels.ReadableByteChannel) r1, (java.nio.ByteBuffer) r3)     // Catch:{ all -> 0x0055 }
            r0 = 1
            com.app.office.fc.poifs.nio.ByteArrayBackedDataSource r2 = new com.app.office.fc.poifs.nio.ByteArrayBackedDataSource     // Catch:{ all -> 0x0055 }
            byte[] r4 = r3.array()     // Catch:{ all -> 0x0055 }
            int r3 = r3.position()     // Catch:{ all -> 0x0055 }
            r2.<init>(r4, r3)     // Catch:{ all -> 0x0055 }
            r5._data = r2     // Catch:{ all -> 0x0055 }
            if (r1 == 0) goto L_0x004e
            r1.close()
        L_0x004e:
            r5.closeInputStream(r6, r0)
            r5.readCoreContents()
            return
        L_0x0055:
            r2 = move-exception
            goto L_0x0059
        L_0x0057:
            r2 = move-exception
            r1 = 0
        L_0x0059:
            if (r1 == 0) goto L_0x005e
            r1.close()
        L_0x005e:
            r5.closeInputStream(r6, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.poifs.filesystem.NPOIFSFileSystem.<init>(java.io.InputStream):void");
    }

    private void closeInputStream(InputStream inputStream, boolean z) {
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

    private void readCoreContents() throws IOException {
        int valueAt;
        this.bigBlockSize = this._header.getBigBlockSize();
        BlockStore.ChainLoopDetector chainLoopDetector = getChainLoopDetector();
        for (int readBAT : this._header.getBATArray()) {
            readBAT(readBAT, chainLoopDetector);
        }
        int xBATIndex = this._header.getXBATIndex();
        for (int i = 0; i < this._header.getXBATCount(); i++) {
            chainLoopDetector.claim(xBATIndex);
            BATBlock createBATBlock = BATBlock.createBATBlock(this.bigBlockSize, getBlockAt(xBATIndex));
            createBATBlock.setOurBlockIndex(xBATIndex);
            xBATIndex = createBATBlock.getValueAt(this.bigBlockSize.getXBATEntriesPerBlock());
            this._xbat_blocks.add(createBATBlock);
            int i2 = 0;
            while (i2 < this.bigBlockSize.getXBATEntriesPerBlock() && (valueAt = createBATBlock.getValueAt(i2)) != -1) {
                readBAT(valueAt, chainLoopDetector);
                i2++;
            }
        }
        this._property_table = new NPropertyTable(this._header, this);
        ArrayList arrayList = new ArrayList();
        this._mini_store = new NPOIFSMiniStore(this, this._property_table.getRoot(), arrayList, this._header);
        int sBATStart = this._header.getSBATStart();
        for (int i3 = 0; i3 < this._header.getSBATCount(); i3++) {
            chainLoopDetector.claim(sBATStart);
            BATBlock createBATBlock2 = BATBlock.createBATBlock(this.bigBlockSize, getBlockAt(sBATStart));
            createBATBlock2.setOurBlockIndex(sBATStart);
            arrayList.add(createBATBlock2);
            sBATStart = getNextBlock(sBATStart);
        }
    }

    private void readBAT(int i, BlockStore.ChainLoopDetector chainLoopDetector) throws IOException {
        chainLoopDetector.claim(i);
        BATBlock createBATBlock = BATBlock.createBATBlock(this.bigBlockSize, getBlockAt(i));
        createBATBlock.setOurBlockIndex(i);
        this._bat_blocks.add(createBATBlock);
    }

    private BATBlock createBAT(int i, boolean z) throws IOException {
        BATBlock createEmptyBATBlock = BATBlock.createEmptyBATBlock(this.bigBlockSize, !z);
        createEmptyBATBlock.setOurBlockIndex(i);
        this._data.write(ByteBuffer.allocate(this.bigBlockSize.getBigBlockSize()), (long) ((i + 1) * this.bigBlockSize.getBigBlockSize()));
        return createEmptyBATBlock;
    }

    /* access modifiers changed from: protected */
    public ByteBuffer getBlockAt(int i) throws IOException {
        return this._data.read(this.bigBlockSize.getBigBlockSize(), (long) ((i + 1) * this.bigBlockSize.getBigBlockSize()));
    }

    /* access modifiers changed from: protected */
    public ByteBuffer createBlockIfNeeded(int i) throws IOException {
        try {
            return getBlockAt(i);
        } catch (IndexOutOfBoundsException unused) {
            ByteBuffer allocate = ByteBuffer.allocate(getBigBlockSize());
            this._data.write(allocate, (long) ((i + 1) * this.bigBlockSize.getBigBlockSize()));
            return getBlockAt(i);
        }
    }

    /* access modifiers changed from: protected */
    public BATBlock.BATBlockAndIndex getBATBlockAndIndex(int i) {
        return BATBlock.getBATBlockAndIndex(i, this._header, this._bat_blocks);
    }

    /* access modifiers changed from: protected */
    public int getNextBlock(int i) {
        BATBlock.BATBlockAndIndex bATBlockAndIndex = getBATBlockAndIndex(i);
        return bATBlockAndIndex.getBlock().getValueAt(bATBlockAndIndex.getIndex());
    }

    /* access modifiers changed from: protected */
    public void setNextBlock(int i, int i2) {
        BATBlock.BATBlockAndIndex bATBlockAndIndex = getBATBlockAndIndex(i);
        bATBlockAndIndex.getBlock().setValueAt(bATBlockAndIndex.getIndex(), i2);
    }

    /* access modifiers changed from: protected */
    public int getFreeBlock() throws IOException {
        int i = 0;
        for (int i2 = 0; i2 < this._bat_blocks.size(); i2++) {
            int bATEntriesPerBlock = this.bigBlockSize.getBATEntriesPerBlock();
            BATBlock bATBlock = this._bat_blocks.get(i2);
            if (bATBlock.hasFreeSectors()) {
                for (int i3 = 0; i3 < bATEntriesPerBlock; i3++) {
                    if (bATBlock.getValueAt(i3) == -1) {
                        return i + i3;
                    }
                }
                continue;
            }
            i += bATEntriesPerBlock;
        }
        BATBlock createBAT = createBAT(i, true);
        createBAT.setValueAt(0, -3);
        this._bat_blocks.add(createBAT);
        if (this._header.getBATCount() >= 109) {
            BATBlock bATBlock2 = null;
            Iterator<BATBlock> it = this._xbat_blocks.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                BATBlock next = it.next();
                if (next.hasFreeSectors()) {
                    bATBlock2 = next;
                    break;
                }
            }
            if (bATBlock2 == null) {
                int i4 = i + 1;
                BATBlock createBAT2 = createBAT(i4, false);
                createBAT2.setValueAt(0, i);
                createBAT.setValueAt(1, -4);
                if (this._xbat_blocks.size() == 0) {
                    this._header.setXBATStart(i4);
                } else {
                    List<BATBlock> list = this._xbat_blocks;
                    list.get(list.size() - 1).setValueAt(this.bigBlockSize.getXBATEntriesPerBlock(), i4);
                }
                this._xbat_blocks.add(createBAT2);
                this._header.setXBATCount(this._xbat_blocks.size());
                i = i4;
                bATBlock2 = createBAT2;
            }
            for (int i5 = 0; i5 < this.bigBlockSize.getXBATEntriesPerBlock(); i5++) {
                if (bATBlock2.getValueAt(i5) == -1) {
                    bATBlock2.setValueAt(i5, i);
                }
            }
        } else {
            int bATCount = this._header.getBATCount() + 1;
            int[] iArr = new int[bATCount];
            int i6 = bATCount - 1;
            System.arraycopy(this._header.getBATArray(), 0, iArr, 0, i6);
            iArr[i6] = i;
            this._header.setBATArray(iArr);
        }
        this._header.setBATCount(this._bat_blocks.size());
        return i + 1;
    }

    /* access modifiers changed from: protected */
    public BlockStore.ChainLoopDetector getChainLoopDetector() throws IOException {
        return new BlockStore.ChainLoopDetector(this._data.size());
    }

    /* access modifiers changed from: package-private */
    public NPropertyTable _get_property_table() {
        return this._property_table;
    }

    public NPOIFSMiniStore getMiniStore() {
        return this._mini_store;
    }

    /* access modifiers changed from: package-private */
    public void addDocument(NPOIFSDocument nPOIFSDocument) {
        this._property_table.addProperty(nPOIFSDocument.getDocumentProperty());
    }

    /* access modifiers changed from: package-private */
    public void addDirectory(DirectoryProperty directoryProperty) {
        this._property_table.addProperty(directoryProperty);
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

    public void writeFilesystem() throws IOException {
        if (this._data instanceof FileBackedDataSource) {
            syncWithDataSource();
            return;
        }
        throw new IllegalArgumentException("POIFS opened from an inputstream, so writeFilesystem() may not be called. Use writeFilesystem(OutputStream) instead");
    }

    public void writeFilesystem(OutputStream outputStream) throws IOException {
        syncWithDataSource();
        this._data.copyTo(outputStream);
    }

    private void syncWithDataSource() throws IOException {
        new HeaderBlockWriter(this._header).writeBlock(getBlockAt(-1));
        for (BATBlock next : this._bat_blocks) {
            BlockAllocationTableWriter.writeBlock(next, getBlockAt(next.getOurBlockIndex()));
        }
        this._mini_store.syncWithDataSource();
        this._property_table.write(new NPOIFSStream(this, this._header.getPropertyStart()));
    }

    public void close() throws IOException {
        this._data.close();
    }

    public static void main(String[] strArr) throws IOException {
        if (strArr.length != 2) {
            System.err.println("two arguments required: input filename and output filename");
            System.exit(1);
        }
        FileInputStream fileInputStream = new FileInputStream(strArr[0]);
        FileOutputStream fileOutputStream = new FileOutputStream(strArr[1]);
        new NPOIFSFileSystem((InputStream) fileInputStream).writeFilesystem(fileOutputStream);
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
    public void remove(EntryNode entryNode) {
        this._property_table.removeProperty(entryNode.getProperty());
    }

    public int getBigBlockSize() {
        return this.bigBlockSize.getBigBlockSize();
    }

    public POIFSBigBlockSize getBigBlockSizeDetails() {
        return this.bigBlockSize;
    }

    /* access modifiers changed from: protected */
    public int getBlockStoreBlockSize() {
        return getBigBlockSize();
    }
}
