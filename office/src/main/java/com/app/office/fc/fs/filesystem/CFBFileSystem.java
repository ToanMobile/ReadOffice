package com.app.office.fc.fs.filesystem;

import com.app.office.fc.fs.storage.BlockAllocationTableReader;
import com.app.office.fc.fs.storage.BlockList;
import com.app.office.fc.fs.storage.HeaderBlock;
import com.app.office.fc.fs.storage.RawDataBlock;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CFBFileSystem {
    private BlockSize bigBlockSize;
    private HeaderBlock headerBlock;
    private boolean isGetThumbnail;
    private Property root;

    public CFBFileSystem(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    /* JADX INFO: finally extract failed */
    public CFBFileSystem(InputStream inputStream, boolean z) throws IOException {
        this.bigBlockSize = CFBConstants.SMALLER_BIG_BLOCK_SIZE_DETAILS;
        this.isGetThumbnail = z;
        try {
            HeaderBlock headerBlock2 = new HeaderBlock(inputStream);
            this.headerBlock = headerBlock2;
            BlockSize bigBlockSize2 = headerBlock2.getBigBlockSize();
            this.bigBlockSize = bigBlockSize2;
            BlockList blockList = new BlockList(inputStream, bigBlockSize2);
            inputStream.close();
            new BlockAllocationTableReader(this.headerBlock.getBigBlockSize(), this.headerBlock.getBATCount(), this.headerBlock.getBATArray(), this.headerBlock.getXBATCount(), this.headerBlock.getXBATIndex(), blockList);
            ArrayList arrayList = new ArrayList();
            readProperties(blockList.fetchBlocks(this.headerBlock.getPropertyStart(), -1), blockList, arrayList);
            createPropertyTree(this.root, arrayList);
            readPrepertiesRawData(readSmallRawDataBlock(blockList), blockList, this.root);
        } catch (Throwable th) {
            inputStream.close();
            throw th;
        }
    }

    private void createPropertyTree(Property property, List<Property> list) {
        int childPropertyIndex = property.getChildPropertyIndex();
        if (childPropertyIndex >= 0) {
            Stack stack = new Stack();
            stack.push(list.get(childPropertyIndex));
            while (!stack.isEmpty()) {
                Property property2 = (Property) stack.pop();
                property.addChildProperty(property2);
                if (property2.isDirectory()) {
                    createPropertyTree(property2, list);
                }
                int previousPropertyIndex = property2.getPreviousPropertyIndex();
                if (previousPropertyIndex >= 0) {
                    stack.push(list.get(previousPropertyIndex));
                }
                int nextPropertyIndex = property2.getNextPropertyIndex();
                if (nextPropertyIndex >= 0) {
                    stack.push(list.get(nextPropertyIndex));
                }
            }
        }
    }

    private void readProperties(RawDataBlock[] rawDataBlockArr, BlockList blockList, List<Property> list) throws IOException {
        for (RawDataBlock data : rawDataBlockArr) {
            byte[] data2 = data.getData();
            int length = data2.length / 128;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                byte b = data2[i + 66];
                if (b == 1) {
                    list.add(new Property(list.size(), data2, i));
                } else if (b == 2) {
                    list.add(new Property(list.size(), data2, i));
                } else if (b == 5) {
                    Property property = new Property(list.size(), data2, i);
                    this.root = property;
                    list.add(property);
                }
                i += 128;
            }
        }
    }

    private BlockList readSmallRawDataBlock(BlockList blockList) throws IOException {
        RawDataBlock[] fetchBlocks = blockList.fetchBlocks(this.root.getStartBlock(), -1);
        int bigBlockSize2 = this.headerBlock.getBigBlockSize().getBigBlockSize() / 64;
        ArrayList arrayList = new ArrayList();
        for (RawDataBlock data : fetchBlocks) {
            byte[] data2 = data.getData();
            for (int i = 0; i < bigBlockSize2; i++) {
                byte[] bArr = new byte[64];
                System.arraycopy(data2, i * 64, bArr, 0, 64);
                arrayList.add(new RawDataBlock(bArr));
            }
        }
        BlockList blockList2 = new BlockList((RawDataBlock[]) arrayList.toArray(new RawDataBlock[arrayList.size()]));
        new BlockAllocationTableReader(this.bigBlockSize, blockList.fetchBlocks(this.headerBlock.getSBATStart(), -1), blockList2);
        return blockList2;
    }

    private void readPrepertiesRawData(BlockList blockList, BlockList blockList2, Property property) throws IOException {
        for (Property next : property.properties.values()) {
            if (next.isDocument()) {
                getPropertyRawData(next, blockList, blockList2);
            } else if (next.isDirectory()) {
                readPrepertiesRawData(blockList, blockList2, next);
            }
        }
    }

    private void getPropertyRawData(Property property, BlockList blockList, BlockList blockList2) throws IOException {
        RawDataBlock[] rawDataBlockArr;
        String name = property.getName();
        int startBlock = property.getStartBlock();
        if (property.shouldUseSmallBlocks()) {
            rawDataBlockArr = blockList.fetchBlocks(startBlock, this.headerBlock.getPropertyStart());
        } else {
            rawDataBlockArr = blockList2.fetchBlocks(startBlock, this.headerBlock.getPropertyStart());
        }
        if (rawDataBlockArr != null && rawDataBlockArr.length != 0) {
            if (name.equals("Pictures") || name.endsWith("WorkBook") || name.equals("PowerPoint Document") || name.endsWith("Ole") || name.endsWith("ObjInfo") || name.endsWith("ComObj") || name.endsWith("EPRINT")) {
                property.setBlocks(rawDataBlockArr);
                return;
            }
            int length = rawDataBlockArr[0].getData().length;
            byte[] bArr = new byte[(rawDataBlockArr.length * length)];
            int i = 0;
            for (RawDataBlock data : rawDataBlockArr) {
                System.arraycopy(data.getData(), 0, bArr, i, length);
                i += length;
            }
            property.setDocumentRawData(bArr);
        }
    }

    public byte[] getPropertyRawData(String str) {
        Property property = getProperty(str);
        if (property != null) {
            return property.getDocumentRawData();
        }
        return null;
    }

    public Property getProperty(String str) {
        return this.root.getChlidProperty(str);
    }

    public void dispose() {
        HeaderBlock headerBlock2 = this.headerBlock;
        if (headerBlock2 != null) {
            headerBlock2.dispose();
            this.headerBlock = null;
        }
        Property property = this.root;
        if (property != null) {
            property.dispose();
        }
    }
}
