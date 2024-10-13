package com.app.office.fc.poifs.storage;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import com.app.office.fc.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public final class DocumentBlock extends BigBlock {
    private static final byte _default_value = -1;
    private int _bytes_read;
    private byte[] _data;

    public static byte getFillByte() {
        return -1;
    }

    public /* bridge */ /* synthetic */ void writeBlocks(OutputStream outputStream) throws IOException {
        super.writeBlocks(outputStream);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DocumentBlock(com.app.office.fc.poifs.storage.RawDataBlock r3) throws java.io.IOException {
        /*
            r2 = this;
            int r0 = r3.getBigBlockSize()
            r1 = 512(0x200, float:7.175E-43)
            if (r0 != r1) goto L_0x000b
            com.app.office.fc.poifs.common.POIFSBigBlockSize r0 = com.app.office.fc.poifs.common.POIFSConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS
            goto L_0x000d
        L_0x000b:
            com.app.office.fc.poifs.common.POIFSBigBlockSize r0 = com.app.office.fc.poifs.common.POIFSConstants.LARGER_BIG_BLOCK_SIZE_DETAILS
        L_0x000d:
            r2.<init>(r0)
            byte[] r3 = r3.getData()
            r2._data = r3
            int r3 = r3.length
            r2._bytes_read = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.app.office.fc.poifs.storage.DocumentBlock.<init>(com.app.office.fc.poifs.storage.RawDataBlock):void");
    }

    public DocumentBlock(InputStream inputStream, POIFSBigBlockSize pOIFSBigBlockSize) throws IOException {
        this(pOIFSBigBlockSize);
        int readFully = IOUtils.readFully(inputStream, this._data);
        this._bytes_read = readFully == -1 ? 0 : readFully;
    }

    private DocumentBlock(POIFSBigBlockSize pOIFSBigBlockSize) {
        super(pOIFSBigBlockSize);
        byte[] bArr = new byte[pOIFSBigBlockSize.getBigBlockSize()];
        this._data = bArr;
        Arrays.fill(bArr, (byte) -1);
    }

    public int size() {
        return this._bytes_read;
    }

    public boolean partiallyRead() {
        return this._bytes_read != this.bigBlockSize.getBigBlockSize();
    }

    public static DocumentBlock[] convert(POIFSBigBlockSize pOIFSBigBlockSize, byte[] bArr, int i) {
        int bigBlockSize = ((i + pOIFSBigBlockSize.getBigBlockSize()) - 1) / pOIFSBigBlockSize.getBigBlockSize();
        DocumentBlock[] documentBlockArr = new DocumentBlock[bigBlockSize];
        int i2 = 0;
        for (int i3 = 0; i3 < bigBlockSize; i3++) {
            documentBlockArr[i3] = new DocumentBlock(pOIFSBigBlockSize);
            if (i2 < bArr.length) {
                int min = Math.min(pOIFSBigBlockSize.getBigBlockSize(), bArr.length - i2);
                System.arraycopy(bArr, i2, documentBlockArr[i3]._data, 0, min);
                if (min != pOIFSBigBlockSize.getBigBlockSize()) {
                    Arrays.fill(documentBlockArr[i3]._data, min, pOIFSBigBlockSize.getBigBlockSize(), (byte) -1);
                }
            } else {
                Arrays.fill(documentBlockArr[i3]._data, (byte) -1);
            }
            i2 += pOIFSBigBlockSize.getBigBlockSize();
        }
        return documentBlockArr;
    }

    public static DataInputBlock getDataInputBlock(DocumentBlock[] documentBlockArr, int i) {
        if (documentBlockArr == null || documentBlockArr.length == 0) {
            return null;
        }
        POIFSBigBlockSize pOIFSBigBlockSize = documentBlockArr[0].bigBlockSize;
        return new DataInputBlock(documentBlockArr[i >> pOIFSBigBlockSize.getHeaderValue()]._data, i & (pOIFSBigBlockSize.getBigBlockSize() - 1));
    }

    /* access modifiers changed from: package-private */
    public void writeData(OutputStream outputStream) throws IOException {
        doWriteData(outputStream, this._data);
    }
}
