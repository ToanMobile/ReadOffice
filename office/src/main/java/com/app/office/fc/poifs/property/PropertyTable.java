package com.app.office.fc.poifs.property;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import com.app.office.fc.poifs.storage.BlockWritable;
import com.app.office.fc.poifs.storage.HeaderBlock;
import com.app.office.fc.poifs.storage.PropertyBlock;
import com.app.office.fc.poifs.storage.RawDataBlockList;
import java.io.IOException;
import java.io.OutputStream;

public final class PropertyTable extends PropertyTableBase implements BlockWritable {
    private POIFSBigBlockSize _bigBigBlockSize;
    private BlockWritable[] _blocks = null;

    public PropertyTable(HeaderBlock headerBlock) {
        super(headerBlock);
        this._bigBigBlockSize = headerBlock.getBigBlockSize();
    }

    public PropertyTable(HeaderBlock headerBlock, RawDataBlockList rawDataBlockList) throws IOException {
        super(headerBlock, PropertyFactory.convertToProperties(rawDataBlockList.fetchBlocks(headerBlock.getPropertyStart(), -1)));
        this._bigBigBlockSize = headerBlock.getBigBlockSize();
    }

    public void preWrite() {
        Property[] propertyArr = (Property[]) this._properties.toArray(new Property[this._properties.size()]);
        for (int i = 0; i < propertyArr.length; i++) {
            propertyArr[i].setIndex(i);
        }
        this._blocks = PropertyBlock.createPropertyBlockArray(this._bigBigBlockSize, this._properties);
        for (Property preWrite : propertyArr) {
            preWrite.preWrite();
        }
    }

    public int countBlocks() {
        BlockWritable[] blockWritableArr = this._blocks;
        if (blockWritableArr == null) {
            return 0;
        }
        return blockWritableArr.length;
    }

    public void writeBlocks(OutputStream outputStream) throws IOException {
        if (this._blocks != null) {
            int i = 0;
            while (true) {
                BlockWritable[] blockWritableArr = this._blocks;
                if (i < blockWritableArr.length) {
                    blockWritableArr[i].writeBlocks(outputStream);
                    i++;
                } else {
                    return;
                }
            }
        }
    }
}
