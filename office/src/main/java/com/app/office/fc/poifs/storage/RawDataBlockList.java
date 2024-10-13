package com.app.office.fc.poifs.storage;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RawDataBlockList extends BlockListImpl {
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

    public RawDataBlockList(InputStream inputStream, POIFSBigBlockSize pOIFSBigBlockSize) throws IOException {
        RawDataBlock rawDataBlock;
        ArrayList arrayList = new ArrayList();
        do {
            rawDataBlock = new RawDataBlock(inputStream, pOIFSBigBlockSize.getBigBlockSize());
            if (rawDataBlock.hasData()) {
                arrayList.add(rawDataBlock);
            }
        } while (!rawDataBlock.eof());
        setBlocks((ListManagedBlock[]) arrayList.toArray(new RawDataBlock[arrayList.size()]));
    }
}
