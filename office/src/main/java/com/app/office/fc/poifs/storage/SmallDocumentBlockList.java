package com.app.office.fc.poifs.storage;

import java.io.IOException;
import java.util.List;

public class SmallDocumentBlockList extends BlockListImpl {
    public /* bridge */ /* synthetic */ int blockCount() {
        return super.blockCount();
    }

    public /* bridge */ /* synthetic */ ListManagedBlock[] fetchBlocks(int i, int i2) throws IOException {
        return super.fetchBlocks(i, i2);
    }

    public /* bridge */ /* synthetic */ ListManagedBlock remove(int i) throws IOException {
        return super.remove(i);
    }

    public /* bridge */ /* synthetic */ void setBAT(BlockAllocationTableReader blockAllocationTableReader) throws IOException {
        super.setBAT(blockAllocationTableReader);
    }

    public /* bridge */ /* synthetic */ void zap(int i) {
        super.zap(i);
    }

    public SmallDocumentBlockList(List list) {
        setBlocks((SmallDocumentBlock[]) list.toArray(new SmallDocumentBlock[list.size()]));
    }
}
