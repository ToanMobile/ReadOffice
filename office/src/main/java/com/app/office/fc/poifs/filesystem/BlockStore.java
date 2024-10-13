package com.app.office.fc.poifs.filesystem;

import com.app.office.fc.poifs.storage.BATBlock;
import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class BlockStore {
    /* access modifiers changed from: protected */
    public abstract ByteBuffer createBlockIfNeeded(int i) throws IOException;

    /* access modifiers changed from: protected */
    public abstract BATBlock.BATBlockAndIndex getBATBlockAndIndex(int i);

    /* access modifiers changed from: protected */
    public abstract ByteBuffer getBlockAt(int i) throws IOException;

    /* access modifiers changed from: protected */
    public abstract int getBlockStoreBlockSize();

    /* access modifiers changed from: protected */
    public abstract ChainLoopDetector getChainLoopDetector() throws IOException;

    /* access modifiers changed from: protected */
    public abstract int getFreeBlock() throws IOException;

    /* access modifiers changed from: protected */
    public abstract int getNextBlock(int i);

    /* access modifiers changed from: protected */
    public abstract void setNextBlock(int i, int i2);

    protected class ChainLoopDetector {
        private boolean[] used_blocks;

        protected ChainLoopDetector(long j) {
            this.used_blocks = new boolean[((int) Math.ceil((double) (j / ((long) BlockStore.this.getBlockStoreBlockSize()))))];
        }

        /* access modifiers changed from: protected */
        public void claim(int i) {
            boolean[] zArr = this.used_blocks;
            if (i < zArr.length) {
                if (!zArr[i]) {
                    zArr[i] = true;
                    return;
                }
                throw new IllegalStateException("Potential loop detected - Block " + i + " was already claimed but was just requested again");
            }
        }
    }
}
