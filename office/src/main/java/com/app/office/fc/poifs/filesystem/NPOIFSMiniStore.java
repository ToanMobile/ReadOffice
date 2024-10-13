package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.poifs.filesystem.BlockStore;
import com.app.office.fc.poifs.property.RootProperty;
import com.app.office.fc.poifs.storage.BATBlock;
import com.app.office.fc.poifs.storage.BlockAllocationTableWriter;
import com.app.office.fc.poifs.storage.HeaderBlock;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

public class NPOIFSMiniStore extends BlockStore {
    private NPOIFSFileSystem _filesystem;
    private HeaderBlock _header;
    private NPOIFSStream _mini_stream;
    private RootProperty _root;
    private List<BATBlock> _sbat_blocks;

    /* access modifiers changed from: protected */
    public int getBlockStoreBlockSize() {
        return 64;
    }

    protected NPOIFSMiniStore(NPOIFSFileSystem nPOIFSFileSystem, RootProperty rootProperty, List<BATBlock> list, HeaderBlock headerBlock) {
        this._filesystem = nPOIFSFileSystem;
        this._sbat_blocks = list;
        this._header = headerBlock;
        this._root = rootProperty;
        this._mini_stream = new NPOIFSStream(nPOIFSFileSystem, rootProperty.getStartBlock());
    }

    /* access modifiers changed from: protected */
    public ByteBuffer getBlockAt(int i) throws IOException {
        int i2 = i * 64;
        int bigBlockSize = i2 / this._filesystem.getBigBlockSize();
        int bigBlockSize2 = i2 % this._filesystem.getBigBlockSize();
        Iterator<ByteBuffer> blockIterator = this._mini_stream.getBlockIterator();
        for (int i3 = 0; i3 < bigBlockSize; i3++) {
            blockIterator.next();
        }
        ByteBuffer next = blockIterator.next();
        if (next != null) {
            next.position(next.position() + bigBlockSize2);
            ByteBuffer slice = next.slice();
            slice.limit(64);
            return slice;
        }
        throw new IndexOutOfBoundsException("Big block " + bigBlockSize + " outside stream");
    }

    /* access modifiers changed from: protected */
    public ByteBuffer createBlockIfNeeded(int i) throws IOException {
        try {
            return getBlockAt(i);
        } catch (IndexOutOfBoundsException unused) {
            int freeBlock = this._filesystem.getFreeBlock();
            this._filesystem.createBlockIfNeeded(freeBlock);
            BlockStore.ChainLoopDetector chainLoopDetector = this._filesystem.getChainLoopDetector();
            int startBlock = this._mini_stream.getStartBlock();
            while (true) {
                chainLoopDetector.claim(startBlock);
                int nextBlock = this._filesystem.getNextBlock(startBlock);
                if (nextBlock == -2) {
                    this._filesystem.setNextBlock(startBlock, freeBlock);
                    this._filesystem.setNextBlock(freeBlock, -2);
                    return createBlockIfNeeded(i);
                }
                startBlock = nextBlock;
            }
        }
    }

    /* access modifiers changed from: protected */
    public BATBlock.BATBlockAndIndex getBATBlockAndIndex(int i) {
        return BATBlock.getSBATBlockAndIndex(i, this._header, this._sbat_blocks);
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
        int bATEntriesPerBlock = this._filesystem.getBigBlockSizeDetails().getBATEntriesPerBlock();
        int i = 0;
        for (int i2 = 0; i2 < this._sbat_blocks.size(); i2++) {
            BATBlock bATBlock = this._sbat_blocks.get(i2);
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
        BATBlock createEmptyBATBlock = BATBlock.createEmptyBATBlock(this._filesystem.getBigBlockSizeDetails(), false);
        int freeBlock = this._filesystem.getFreeBlock();
        createEmptyBATBlock.setOurBlockIndex(freeBlock);
        if (this._header.getSBATCount() == 0) {
            this._header.setSBATStart(freeBlock);
            this._header.setSBATBlockCount(1);
        } else {
            BlockStore.ChainLoopDetector chainLoopDetector = this._filesystem.getChainLoopDetector();
            int sBATStart = this._header.getSBATStart();
            while (true) {
                chainLoopDetector.claim(sBATStart);
                int nextBlock = this._filesystem.getNextBlock(sBATStart);
                if (nextBlock == -2) {
                    break;
                }
                sBATStart = nextBlock;
            }
            this._filesystem.setNextBlock(sBATStart, freeBlock);
            HeaderBlock headerBlock = this._header;
            headerBlock.setSBATBlockCount(headerBlock.getSBATCount() + 1);
        }
        this._filesystem.setNextBlock(freeBlock, -2);
        this._sbat_blocks.add(createEmptyBATBlock);
        return i;
    }

    /* access modifiers changed from: protected */
    public BlockStore.ChainLoopDetector getChainLoopDetector() throws IOException {
        return new BlockStore.ChainLoopDetector((long) this._root.getSize());
    }

    /* access modifiers changed from: protected */
    public void syncWithDataSource() throws IOException {
        for (BATBlock next : this._sbat_blocks) {
            BlockAllocationTableWriter.writeBlock(next, this._filesystem.getBlockAt(next.getOurBlockIndex()));
        }
    }
}
