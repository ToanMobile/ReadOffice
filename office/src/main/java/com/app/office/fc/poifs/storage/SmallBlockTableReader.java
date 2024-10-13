package com.app.office.fc.poifs.storage;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import com.app.office.fc.poifs.property.RootProperty;
import java.io.IOException;

public final class SmallBlockTableReader {
    public static BlockList getSmallDocumentBlocks(POIFSBigBlockSize pOIFSBigBlockSize, RawDataBlockList rawDataBlockList, RootProperty rootProperty, int i) throws IOException {
        SmallDocumentBlockList smallDocumentBlockList = new SmallDocumentBlockList(SmallDocumentBlock.extract(pOIFSBigBlockSize, rawDataBlockList.fetchBlocks(rootProperty.getStartBlock(), -1)));
        new BlockAllocationTableReader(pOIFSBigBlockSize, rawDataBlockList.fetchBlocks(i, -1), smallDocumentBlockList);
        return smallDocumentBlockList;
    }
}
