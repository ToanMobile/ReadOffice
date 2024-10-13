package com.app.office.fc.poifs.storage;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import com.app.office.fc.util.IntList;
import com.app.office.fc.util.LittleEndian;
import com.app.office.fc.util.POILogFactory;
import com.app.office.fc.util.POILogger;
import java.io.IOException;
import java.util.ArrayList;

public final class BlockAllocationTableReader {
    private static final int MAX_BLOCK_COUNT = 65535;
    private static final POILogger _logger = POILogFactory.getLogger(BlockAllocationTableReader.class);
    private final IntList _entries;
    private POIFSBigBlockSize bigBlockSize;

    public BlockAllocationTableReader(POIFSBigBlockSize pOIFSBigBlockSize, int i, int[] iArr, int i2, int i3, BlockList blockList) throws IOException {
        this(pOIFSBigBlockSize);
        sanityCheckBlockCount(i);
        int min = Math.min(i, iArr.length);
        RawDataBlock[] rawDataBlockArr = new RawDataBlock[i];
        int i4 = 0;
        while (i4 < min) {
            int i5 = iArr[i4];
            if (i5 <= blockList.blockCount()) {
                rawDataBlockArr[i4] = (RawDataBlock) blockList.remove(i5);
                i4++;
            } else {
                throw new IOException("Your file contains " + blockList.blockCount() + " sectors, but the initial DIFAT array at index " + i4 + " referenced block # " + i5 + ". This isn't allowed and  your file is corrupt");
            }
        }
        if (i4 < i) {
            if (i3 >= 0) {
                int xBATEntriesPerBlock = pOIFSBigBlockSize.getXBATEntriesPerBlock();
                int nextXBATChainOffset = pOIFSBigBlockSize.getNextXBATChainOffset();
                for (int i6 = 0; i6 < i2; i6++) {
                    int min2 = Math.min(i - i4, xBATEntriesPerBlock);
                    byte[] data = blockList.remove(i3).getData();
                    int i7 = 0;
                    int i8 = 0;
                    while (i7 < min2) {
                        rawDataBlockArr[i4] = (RawDataBlock) blockList.remove(LittleEndian.getInt(data, i8));
                        i8 += 4;
                        i7++;
                        i4++;
                    }
                    i3 = LittleEndian.getInt(data, nextXBATChainOffset);
                    if (i3 == -2) {
                        break;
                    }
                }
            } else {
                throw new IOException("BAT count exceeds limit, yet XBAT index indicates no valid entries");
            }
        }
        if (i4 == i) {
            setEntries(rawDataBlockArr, blockList);
            return;
        }
        throw new IOException("Could not find all blocks");
    }

    BlockAllocationTableReader(POIFSBigBlockSize pOIFSBigBlockSize, ListManagedBlock[] listManagedBlockArr, BlockList blockList) throws IOException {
        this(pOIFSBigBlockSize);
        setEntries(listManagedBlockArr, blockList);
    }

    BlockAllocationTableReader(POIFSBigBlockSize pOIFSBigBlockSize) {
        this.bigBlockSize = pOIFSBigBlockSize;
        this._entries = new IntList();
    }

    public static void sanityCheckBlockCount(int i) throws IOException {
        if (i <= 0) {
            throw new IOException("Illegal block count; minimum count is 1, got " + i + " instead");
        } else if (i > 65535) {
            throw new IOException("Block count " + i + " is too high. POI maximum is " + 65535 + ".");
        }
    }

    /* access modifiers changed from: package-private */
    public ListManagedBlock[] fetchBlocks(int i, int i2, BlockList blockList) throws IOException {
        ArrayList arrayList = new ArrayList();
        boolean z = true;
        while (i != -2) {
            try {
                arrayList.add(blockList.remove(i));
                i = this._entries.get(i);
                z = false;
            } catch (IOException e) {
                if (i == i2) {
                    _logger.log(POILogger.WARN, (Object) "Warning, header block comes after data blocks in POIFS block listing");
                } else if (i != 0 || !z) {
                    throw e;
                } else {
                    _logger.log(POILogger.WARN, (Object) "Warning, incorrectly terminated empty data blocks in POIFS block listing (should end at -2, ended at 0)");
                }
                i = -2;
            }
        }
        return (ListManagedBlock[]) arrayList.toArray(new ListManagedBlock[arrayList.size()]);
    }

    /* access modifiers changed from: package-private */
    public boolean isUsed(int i) {
        try {
            return this._entries.get(i) != -1;
        } catch (IndexOutOfBoundsException unused) {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public int getNextBlockIndex(int i) throws IOException {
        if (isUsed(i)) {
            return this._entries.get(i);
        }
        throw new IOException("index " + i + " is unused");
    }

    private void setEntries(ListManagedBlock[] listManagedBlockArr, BlockList blockList) throws IOException {
        int bATEntriesPerBlock = this.bigBlockSize.getBATEntriesPerBlock();
        for (int i = 0; i < listManagedBlockArr.length; i++) {
            byte[] data = listManagedBlockArr[i].getData();
            int i2 = 0;
            for (int i3 = 0; i3 < bATEntriesPerBlock; i3++) {
                int i4 = LittleEndian.getInt(data, i2);
                if (i4 == -1) {
                    blockList.zap(this._entries.size());
                }
                this._entries.add(i4);
                i2 += 4;
            }
            listManagedBlockArr[i] = null;
        }
        blockList.setBAT(this);
    }
}
