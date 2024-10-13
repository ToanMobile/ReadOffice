package com.app.office.fc.poifs.storage;

import com.app.office.fc.poifs.common.POIFSBigBlockSize;
import com.app.office.fc.poifs.filesystem.BATManaged;
import com.app.office.fc.poifs.filesystem.POIFSDocument;
import com.app.office.fc.poifs.property.RootProperty;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SmallBlockTableWriter implements BlockWritable, BATManaged {
    private int _big_block_count;
    private RootProperty _root;
    private BlockAllocationTableWriter _sbat;
    private List _small_blocks = new ArrayList();

    public SmallBlockTableWriter(POIFSBigBlockSize pOIFSBigBlockSize, List list, RootProperty rootProperty) {
        this._sbat = new BlockAllocationTableWriter(pOIFSBigBlockSize);
        this._root = rootProperty;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            POIFSDocument pOIFSDocument = (POIFSDocument) it.next();
            BlockWritable[] smallBlocks = pOIFSDocument.getSmallBlocks();
            if (smallBlocks.length != 0) {
                pOIFSDocument.setStartBlock(this._sbat.allocateSpace(smallBlocks.length));
                for (BlockWritable add : smallBlocks) {
                    this._small_blocks.add(add);
                }
            } else {
                pOIFSDocument.setStartBlock(-2);
            }
        }
        this._sbat.simpleCreateBlocks();
        this._root.setSize(this._small_blocks.size());
        this._big_block_count = SmallDocumentBlock.fill(pOIFSBigBlockSize, this._small_blocks);
    }

    public int getSBATBlockCount() {
        return (this._big_block_count + 15) / 16;
    }

    public BlockAllocationTableWriter getSBAT() {
        return this._sbat;
    }

    public int countBlocks() {
        return this._big_block_count;
    }

    public void setStartBlock(int i) {
        this._root.setStartBlock(i);
    }

    public void writeBlocks(OutputStream outputStream) throws IOException {
        for (BlockWritable writeBlocks : this._small_blocks) {
            writeBlocks.writeBlocks(outputStream);
        }
    }
}
