package com.app.office.fc.poifs.storage;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import com.app.office.fc.poifs.property.Property;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public final class PropertyBlock extends BigBlock {
    private Property[] _properties;

    public /* bridge */ /* synthetic */ void writeBlocks(OutputStream outputStream) throws IOException {
        super.writeBlocks(outputStream);
    }

    private PropertyBlock(POIFSBigBlockSize pOIFSBigBlockSize, Property[] propertyArr, int i) {
        super(pOIFSBigBlockSize);
        this._properties = new Property[pOIFSBigBlockSize.getPropertiesPerBlock()];
        int i2 = 0;
        while (true) {
            Property[] propertyArr2 = this._properties;
            if (i2 < propertyArr2.length) {
                propertyArr2[i2] = propertyArr[i2 + i];
                i2++;
            } else {
                return;
            }
        }
    }

    public static BlockWritable[] createPropertyBlockArray(POIFSBigBlockSize pOIFSBigBlockSize, List<Property> list) {
        int propertiesPerBlock = pOIFSBigBlockSize.getPropertiesPerBlock();
        int size = ((list.size() + propertiesPerBlock) - 1) / propertiesPerBlock;
        int i = size * propertiesPerBlock;
        Property[] propertyArr = new Property[i];
        System.arraycopy(list.toArray(new Property[0]), 0, propertyArr, 0, list.size());
        for (int size2 = list.size(); size2 < i; size2++) {
            propertyArr[size2] = new Property() {
                public boolean isDirectory() {
                    return false;
                }

                /* access modifiers changed from: protected */
                public void preWrite() {
                }
            };
        }
        BlockWritable[] blockWritableArr = new BlockWritable[size];
        for (int i2 = 0; i2 < size; i2++) {
            blockWritableArr[i2] = new PropertyBlock(pOIFSBigBlockSize, propertyArr, i2 * propertiesPerBlock);
        }
        return blockWritableArr;
    }

    /* access modifiers changed from: package-private */
    public void writeData(OutputStream outputStream) throws IOException {
        int propertiesPerBlock = this.bigBlockSize.getPropertiesPerBlock();
        for (int i = 0; i < propertiesPerBlock; i++) {
            this._properties[i].writeData(outputStream);
        }
    }
}
