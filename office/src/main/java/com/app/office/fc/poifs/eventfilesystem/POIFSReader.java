package com.app.office.fc.poifs.eventfilesystem;

import com.app.office.fc.openxml4j.opc.PackagingURIHelper;
import com.app.office.fc.poifs.filesystem.DocumentInputStream;
import com.app.office.fc.poifs.filesystem.POIFSDocument;
import com.app.office.fc.poifs.filesystem.POIFSDocumentPath;
import com.app.office.fc.poifs.property.DirectoryProperty;
import com.app.office.fc.poifs.property.Property;
import com.app.office.fc.poifs.property.PropertyTable;
import com.app.office.fc.poifs.storage.BlockAllocationTableReader;
import com.app.office.fc.poifs.storage.BlockList;
import com.app.office.fc.poifs.storage.HeaderBlock;
import com.app.office.fc.poifs.storage.RawDataBlockList;
import com.app.office.fc.poifs.storage.SmallBlockTableReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Objects;

public class POIFSReader {
    private POIFSReaderRegistry registry = new POIFSReaderRegistry();
    private boolean registryClosed = false;

    public void read(InputStream inputStream) throws IOException {
        this.registryClosed = true;
        HeaderBlock headerBlock = new HeaderBlock(inputStream);
        RawDataBlockList rawDataBlockList = new RawDataBlockList(inputStream, headerBlock.getBigBlockSize());
        new BlockAllocationTableReader(headerBlock.getBigBlockSize(), headerBlock.getBATCount(), headerBlock.getBATArray(), headerBlock.getXBATCount(), headerBlock.getXBATIndex(), rawDataBlockList);
        PropertyTable propertyTable = new PropertyTable(headerBlock, rawDataBlockList);
        processProperties(SmallBlockTableReader.getSmallDocumentBlocks(headerBlock.getBigBlockSize(), rawDataBlockList, propertyTable.getRoot(), headerBlock.getSBATStart()), rawDataBlockList, propertyTable.getRoot().getChildren(), new POIFSDocumentPath());
    }

    public void registerListener(POIFSReaderListener pOIFSReaderListener) {
        Objects.requireNonNull(pOIFSReaderListener);
        if (!this.registryClosed) {
            this.registry.registerListener(pOIFSReaderListener);
            return;
        }
        throw new IllegalStateException();
    }

    public void registerListener(POIFSReaderListener pOIFSReaderListener, String str) {
        registerListener(pOIFSReaderListener, (POIFSDocumentPath) null, str);
    }

    public void registerListener(POIFSReaderListener pOIFSReaderListener, POIFSDocumentPath pOIFSDocumentPath, String str) {
        if (pOIFSReaderListener == null || str == null || str.length() == 0) {
            throw null;
        } else if (!this.registryClosed) {
            POIFSReaderRegistry pOIFSReaderRegistry = this.registry;
            if (pOIFSDocumentPath == null) {
                pOIFSDocumentPath = new POIFSDocumentPath();
            }
            pOIFSReaderRegistry.registerListener(pOIFSReaderListener, pOIFSDocumentPath, str);
        } else {
            throw new IllegalStateException();
        }
    }

    public static void main(String[] strArr) throws IOException {
        if (strArr.length == 0) {
            System.err.println("at least one argument required: input filename(s)");
            System.exit(1);
        }
        for (int i = 0; i < strArr.length; i++) {
            POIFSReader pOIFSReader = new POIFSReader();
            pOIFSReader.registerListener(new SampleListener());
            PrintStream printStream = System.out;
            printStream.println("reading " + strArr[i]);
            FileInputStream fileInputStream = new FileInputStream(strArr[i]);
            pOIFSReader.read(fileInputStream);
            fileInputStream.close();
        }
    }

    private void processProperties(BlockList blockList, BlockList blockList2, Iterator it, POIFSDocumentPath pOIFSDocumentPath) throws IOException {
        POIFSDocument pOIFSDocument;
        while (it.hasNext()) {
            Property property = (Property) it.next();
            String name = property.getName();
            if (property.isDirectory()) {
                processProperties(blockList, blockList2, ((DirectoryProperty) property).getChildren(), new POIFSDocumentPath(pOIFSDocumentPath, new String[]{name}));
            } else {
                int startBlock = property.getStartBlock();
                Iterator listeners = this.registry.getListeners(pOIFSDocumentPath, name);
                if (listeners.hasNext()) {
                    int size = property.getSize();
                    if (property.shouldUseSmallBlocks()) {
                        pOIFSDocument = new POIFSDocument(name, blockList.fetchBlocks(startBlock, -1), size);
                    } else {
                        pOIFSDocument = new POIFSDocument(name, blockList2.fetchBlocks(startBlock, -1), size);
                    }
                    while (listeners.hasNext()) {
                        ((POIFSReaderListener) listeners.next()).processPOIFSReaderEvent(new POIFSReaderEvent(new DocumentInputStream(pOIFSDocument), pOIFSDocumentPath, name));
                    }
                } else if (property.shouldUseSmallBlocks()) {
                    blockList.fetchBlocks(startBlock, -1);
                } else {
                    blockList2.fetchBlocks(startBlock, -1);
                }
            }
        }
    }

    private static class SampleListener implements POIFSReaderListener {
        SampleListener() {
        }

        public void processPOIFSReaderEvent(POIFSReaderEvent pOIFSReaderEvent) {
            DocumentInputStream stream = pOIFSReaderEvent.getStream();
            POIFSDocumentPath path = pOIFSReaderEvent.getPath();
            String name = pOIFSReaderEvent.getName();
            try {
                int available = stream.available();
                stream.read(new byte[available]);
                int length = path.length();
                for (int i = 0; i < length; i++) {
                    PrintStream printStream = System.out;
                    printStream.print(PackagingURIHelper.FORWARD_SLASH_STRING + path.getComponent(i));
                }
                PrintStream printStream2 = System.out;
                printStream2.println(PackagingURIHelper.FORWARD_SLASH_STRING + name + ": " + available + " bytes read");
            } catch (IOException unused) {
            }
        }
    }
}
