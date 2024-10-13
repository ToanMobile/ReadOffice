package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.poifs.filesystem.BlockStore;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;

public class NPOIFSStream implements Iterable<ByteBuffer> {
    /* access modifiers changed from: private */
    public BlockStore blockStore;
    private int startBlock;

    public NPOIFSStream(BlockStore blockStore2, int i) {
        this.blockStore = blockStore2;
        this.startBlock = i;
    }

    public NPOIFSStream(BlockStore blockStore2) {
        this.blockStore = blockStore2;
        this.startBlock = -2;
    }

    public int getStartBlock() {
        return this.startBlock;
    }

    public Iterator<ByteBuffer> iterator() {
        return getBlockIterator();
    }

    public Iterator<ByteBuffer> getBlockIterator() {
        int i = this.startBlock;
        if (i != -2) {
            return new StreamBlockByteBufferIterator(i);
        }
        throw new IllegalStateException("Can't read from a new stream before it has been written to");
    }

    public void updateContents(byte[] bArr) throws IOException {
        int blockStoreBlockSize = this.blockStore.getBlockStoreBlockSize();
        int ceil = (int) Math.ceil(((double) bArr.length) / ((double) blockStoreBlockSize));
        BlockStore.ChainLoopDetector chainLoopDetector = this.blockStore.getChainLoopDetector();
        int i = this.startBlock;
        int i2 = -2;
        for (int i3 = 0; i3 < ceil; i3++) {
            if (i == -2) {
                int freeBlock = this.blockStore.getFreeBlock();
                chainLoopDetector.claim(freeBlock);
                if (i2 != -2) {
                    this.blockStore.setNextBlock(i2, freeBlock);
                }
                this.blockStore.setNextBlock(freeBlock, -2);
                if (this.startBlock == -2) {
                    this.startBlock = freeBlock;
                }
                i2 = freeBlock;
                i = -2;
            } else {
                chainLoopDetector.claim(i);
                i2 = i;
                i = this.blockStore.getNextBlock(i);
            }
            int i4 = i3 * blockStoreBlockSize;
            this.blockStore.createBlockIfNeeded(i2).put(bArr, i4, Math.min(bArr.length - i4, blockStoreBlockSize));
        }
        new NPOIFSStream(this.blockStore, i).free(chainLoopDetector);
        this.blockStore.setNextBlock(i2, -2);
    }

    public void free() throws IOException {
        free(this.blockStore.getChainLoopDetector());
    }

    private void free(BlockStore.ChainLoopDetector chainLoopDetector) {
        int i = this.startBlock;
        while (i != -2) {
            chainLoopDetector.claim(i);
            int nextBlock = this.blockStore.getNextBlock(i);
            this.blockStore.setNextBlock(i, -1);
            i = nextBlock;
        }
        this.startBlock = -2;
    }

    protected class StreamBlockByteBufferIterator implements Iterator<ByteBuffer> {
        private BlockStore.ChainLoopDetector loopDetector;
        private int nextBlock;

        protected StreamBlockByteBufferIterator(int i) {
            this.nextBlock = i;
            try {
                this.loopDetector = NPOIFSStream.this.blockStore.getChainLoopDetector();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean hasNext() {
            return this.nextBlock != -2;
        }

        public ByteBuffer next() {
            int i = this.nextBlock;
            if (i != -2) {
                try {
                    this.loopDetector.claim(i);
                    ByteBuffer blockAt = NPOIFSStream.this.blockStore.getBlockAt(this.nextBlock);
                    this.nextBlock = NPOIFSStream.this.blockStore.getNextBlock(this.nextBlock);
                    return blockAt;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IndexOutOfBoundsException("Can't read past the end of the stream");
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
