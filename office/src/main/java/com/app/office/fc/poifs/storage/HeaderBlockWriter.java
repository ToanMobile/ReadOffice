package com.app.office.fc.poifs.storage;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class HeaderBlockWriter implements HeaderBlockConstants, BlockWritable {
    private final HeaderBlock _header_block;

    public HeaderBlockWriter(POIFSBigBlockSize pOIFSBigBlockSize) {
        this._header_block = new HeaderBlock(pOIFSBigBlockSize);
    }

    public HeaderBlockWriter(HeaderBlock headerBlock) {
        this._header_block = headerBlock;
    }

    public BATBlock[] setBATBlocks(int i, int i2) {
        BATBlock[] bATBlockArr;
        POIFSBigBlockSize bigBlockSize = this._header_block.getBigBlockSize();
        this._header_block.setBATCount(i);
        int min = Math.min(i, 109);
        int[] iArr = new int[min];
        for (int i3 = 0; i3 < min; i3++) {
            iArr[i3] = i2 + i3;
        }
        this._header_block.setBATArray(iArr);
        if (i > 109) {
            int i4 = i - 109;
            int[] iArr2 = new int[i4];
            for (int i5 = 0; i5 < i4; i5++) {
                iArr2[i5] = i2 + i5 + 109;
            }
            int i6 = i2 + i;
            bATBlockArr = BATBlock.createXBATBlocks(bigBlockSize, iArr2, i6);
            this._header_block.setXBATStart(i6);
        } else {
            bATBlockArr = BATBlock.createXBATBlocks(bigBlockSize, new int[0], 0);
            this._header_block.setXBATStart(-2);
        }
        this._header_block.setXBATCount(bATBlockArr.length);
        return bATBlockArr;
    }

    public void setPropertyStart(int i) {
        this._header_block.setPropertyStart(i);
    }

    public void setSBATStart(int i) {
        this._header_block.setSBATStart(i);
    }

    public void setSBATBlockCount(int i) {
        this._header_block.setSBATBlockCount(i);
    }

    static int calculateXBATStorageRequirements(POIFSBigBlockSize pOIFSBigBlockSize, int i) {
        if (i > 109) {
            return BATBlock.calculateXBATStorageRequirements(pOIFSBigBlockSize, i - 109);
        }
        return 0;
    }

    public void writeBlocks(OutputStream outputStream) throws IOException {
        this._header_block.writeData(outputStream);
    }

    public void writeBlock(ByteBuffer byteBuffer) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(this._header_block.getBigBlockSize().getBigBlockSize());
        this._header_block.writeData(byteArrayOutputStream);
        byteBuffer.put(byteArrayOutputStream.toByteArray());
    }
}
