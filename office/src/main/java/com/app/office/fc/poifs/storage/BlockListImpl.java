package com.app.office.fc.poifs.storage;

import java.io.IOException;

abstract class BlockListImpl implements BlockList {
    private BlockAllocationTableReader _bat = null;
    private ListManagedBlock[] _blocks = new ListManagedBlock[0];

    protected BlockListImpl() {
    }

    /* access modifiers changed from: protected */
    public void setBlocks(ListManagedBlock[] listManagedBlockArr) {
        this._blocks = listManagedBlockArr;
    }

    public void zap(int i) {
        if (i >= 0) {
            ListManagedBlock[] listManagedBlockArr = this._blocks;
            if (i < listManagedBlockArr.length) {
                listManagedBlockArr[i] = null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public ListManagedBlock get(int i) {
        return this._blocks[i];
    }

    public ListManagedBlock remove(int i) throws IOException {
        try {
            ListManagedBlock[] listManagedBlockArr = this._blocks;
            ListManagedBlock listManagedBlock = listManagedBlockArr[i];
            if (listManagedBlock != null) {
                listManagedBlockArr[i] = null;
                return listManagedBlock;
            }
            throw new IOException("block[ " + i + " ] already removed - does your POIFS have circular or duplicate block references?");
        } catch (ArrayIndexOutOfBoundsException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot remove block[ ");
            sb.append(i);
            sb.append(" ]; out of range[ 0 - ");
            sb.append(this._blocks.length - 1);
            sb.append(" ]");
            throw new IOException(sb.toString());
        }
    }

    public ListManagedBlock[] fetchBlocks(int i, int i2) throws IOException {
        BlockAllocationTableReader blockAllocationTableReader = this._bat;
        if (blockAllocationTableReader != null) {
            return blockAllocationTableReader.fetchBlocks(i, i2, this);
        }
        throw new IOException("Improperly initialized list: no block allocation table provided");
    }

    public void setBAT(BlockAllocationTableReader blockAllocationTableReader) throws IOException {
        if (this._bat == null) {
            this._bat = blockAllocationTableReader;
            return;
        }
        throw new IOException("Attempt to replace existing BlockAllocationTable");
    }

    public int blockCount() {
        return this._blocks.length;
    }

    /* access modifiers changed from: protected */
    public int remainingBlocks() {
        int i = 0;
        int i2 = 0;
        while (true) {
            ListManagedBlock[] listManagedBlockArr = this._blocks;
            if (i >= listManagedBlockArr.length) {
                return i2;
            }
            if (listManagedBlockArr[i] != null) {
                i2++;
            }
            i++;
        }
    }
}
