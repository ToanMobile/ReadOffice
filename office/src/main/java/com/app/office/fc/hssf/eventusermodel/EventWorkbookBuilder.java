package com.app.office.fc.hssf.eventusermodel;

import com.app.office.fc.hssf.model.InternalWorkbook;
import com.app.office.fc.hssf.record.BoundSheetRecord;
import com.app.office.fc.hssf.record.EOFRecord;
import com.app.office.fc.hssf.record.ExternSheetRecord;
import com.app.office.fc.hssf.record.Record;
import com.app.office.fc.hssf.record.SSTRecord;
import com.app.office.fc.hssf.record.SupBookRecord;
import com.app.office.fc.hssf.usermodel.HSSFWorkbook;
import java.util.ArrayList;
import java.util.List;

public class EventWorkbookBuilder {
    public static InternalWorkbook createStubWorkbook(ExternSheetRecord[] externSheetRecordArr, BoundSheetRecord[] boundSheetRecordArr, SSTRecord sSTRecord) {
        ArrayList arrayList = new ArrayList();
        if (boundSheetRecordArr != null) {
            for (BoundSheetRecord add : boundSheetRecordArr) {
                arrayList.add(add);
            }
        }
        if (sSTRecord != null) {
            arrayList.add(sSTRecord);
        }
        if (externSheetRecordArr != null) {
            arrayList.add(SupBookRecord.createInternalReferences((short) externSheetRecordArr.length));
            for (ExternSheetRecord add2 : externSheetRecordArr) {
                arrayList.add(add2);
            }
        }
        arrayList.add(EOFRecord.instance);
        return InternalWorkbook.createWorkbook(arrayList);
    }

    public static InternalWorkbook createStubWorkbook(ExternSheetRecord[] externSheetRecordArr, BoundSheetRecord[] boundSheetRecordArr) {
        return createStubWorkbook(externSheetRecordArr, boundSheetRecordArr, (SSTRecord) null);
    }

    public static class SheetRecordCollectingListener implements HSSFListener {
        private List boundSheetRecords = new ArrayList();
        private HSSFListener childListener;
        private List externSheetRecords = new ArrayList();
        private SSTRecord sstRecord = null;

        public SheetRecordCollectingListener(HSSFListener hSSFListener) {
            this.childListener = hSSFListener;
        }

        public BoundSheetRecord[] getBoundSheetRecords() {
            List list = this.boundSheetRecords;
            return (BoundSheetRecord[]) list.toArray(new BoundSheetRecord[list.size()]);
        }

        public ExternSheetRecord[] getExternSheetRecords() {
            List list = this.externSheetRecords;
            return (ExternSheetRecord[]) list.toArray(new ExternSheetRecord[list.size()]);
        }

        public SSTRecord getSSTRecord() {
            return this.sstRecord;
        }

        public HSSFWorkbook getStubHSSFWorkbook() {
            return HSSFWorkbook.create(getStubWorkbook());
        }

        public InternalWorkbook getStubWorkbook() {
            return EventWorkbookBuilder.createStubWorkbook(getExternSheetRecords(), getBoundSheetRecords(), getSSTRecord());
        }

        public void processRecord(Record record) {
            processRecordInternally(record);
            this.childListener.processRecord(record);
        }

        public void processRecordInternally(Record record) {
            if (record instanceof BoundSheetRecord) {
                this.boundSheetRecords.add(record);
            } else if (record instanceof ExternSheetRecord) {
                this.externSheetRecords.add(record);
            } else if (record instanceof SSTRecord) {
                this.sstRecord = (SSTRecord) record;
            }
        }
    }
}
