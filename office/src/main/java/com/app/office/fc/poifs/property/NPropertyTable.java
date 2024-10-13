package com.app.office.fc.poifs.property;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import com.app.office.fc.poifs.filesystem.NPOIFSFileSystem;
import com.app.office.fc.poifs.filesystem.NPOIFSStream;
import com.app.office.fc.poifs.storage.HeaderBlock;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class NPropertyTable extends PropertyTableBase {
    private POIFSBigBlockSize _bigBigBlockSize;

    public NPropertyTable(HeaderBlock headerBlock) {
        super(headerBlock);
        this._bigBigBlockSize = headerBlock.getBigBlockSize();
    }

    public NPropertyTable(HeaderBlock headerBlock, NPOIFSFileSystem nPOIFSFileSystem) throws IOException {
        super(headerBlock, buildProperties(new NPOIFSStream(nPOIFSFileSystem, headerBlock.getPropertyStart()).iterator(), headerBlock.getBigBlockSize()));
        this._bigBigBlockSize = headerBlock.getBigBlockSize();
    }

    private static List<Property> buildProperties(Iterator<ByteBuffer> it, POIFSBigBlockSize pOIFSBigBlockSize) throws IOException {
        byte[] bArr;
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            ByteBuffer next = it.next();
            if (next.hasArray() && next.arrayOffset() == 0 && next.array().length == pOIFSBigBlockSize.getBigBlockSize()) {
                bArr = next.array();
            } else {
                int bigBlockSize = pOIFSBigBlockSize.getBigBlockSize();
                byte[] bArr2 = new byte[bigBlockSize];
                next.get(bArr2, 0, bigBlockSize);
                bArr = bArr2;
            }
            PropertyFactory.convertToProperties(bArr, arrayList);
        }
        return arrayList;
    }

    public int countBlocks() {
        return (int) Math.ceil((double) ((this._properties.size() * 128) / this._bigBigBlockSize.getBigBlockSize()));
    }

    public void write(NPOIFSStream nPOIFSStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (Property property : this._properties) {
            if (property != null) {
                property.writeData(byteArrayOutputStream);
            }
        }
        nPOIFSStream.updateContents(byteArrayOutputStream.toByteArray());
        if (getStartBlock() != nPOIFSStream.getStartBlock()) {
            setStartBlock(nPOIFSStream.getStartBlock());
        }
    }
}
