package com.app.office.fc.hssf.eventusermodel;

import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.RecordFactoryInputStream;
import com.app.office.fc.poifs.filesystem.DirectoryNode;
import com.app.office.fc.poifs.filesystem.POIFSFileSystem;
import java.io.IOException;
import java.io.InputStream;

public class HSSFEventFactory {
    public void processWorkbookEvents(HSSFRequest hSSFRequest, POIFSFileSystem pOIFSFileSystem) throws IOException {
        processWorkbookEvents(hSSFRequest, pOIFSFileSystem.getRoot());
    }

    public void processWorkbookEvents(HSSFRequest hSSFRequest, DirectoryNode directoryNode) throws IOException {
        processEvents(hSSFRequest, directoryNode.createDocumentInputStream("Workbook"));
    }

    public short abortableProcessWorkbookEvents(HSSFRequest hSSFRequest, POIFSFileSystem pOIFSFileSystem) throws IOException, HSSFUserException {
        return abortableProcessWorkbookEvents(hSSFRequest, pOIFSFileSystem.getRoot());
    }

    public short abortableProcessWorkbookEvents(HSSFRequest hSSFRequest, DirectoryNode directoryNode) throws IOException, HSSFUserException {
        return abortableProcessEvents(hSSFRequest, directoryNode.createDocumentInputStream("Workbook"));
    }

    public void processEvents(HSSFRequest hSSFRequest, InputStream inputStream) {
        try {
            genericProcessEvents(hSSFRequest, inputStream);
        } catch (HSSFUserException unused) {
        }
    }

    public short abortableProcessEvents(HSSFRequest hSSFRequest, InputStream inputStream) throws HSSFUserException {
        return genericProcessEvents(hSSFRequest, inputStream);
    }

    private short genericProcessEvents(HSSFRequest hSSFRequest, InputStream inputStream) throws HSSFUserException {
        Record nextRecord;
        short s = 0;
        RecordFactoryInputStream recordFactoryInputStream = new RecordFactoryInputStream(inputStream, false);
        do {
            nextRecord = recordFactoryInputStream.nextRecord();
            if (nextRecord == null || (s = hSSFRequest.processRecord(nextRecord)) != 0) {
                return s;
            }
            nextRecord = recordFactoryInputStream.nextRecord();
            break;
        } while ((s = hSSFRequest.processRecord(nextRecord)) != 0);
        return s;
    }
}
